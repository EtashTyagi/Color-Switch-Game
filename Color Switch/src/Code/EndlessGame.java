package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.io.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

public class EndlessGame extends Game {
    @FXML transient private Circle pauseButton;
    @FXML transient private Label scoreLabel;
    @FXML transient private GridPane gamePane;
    @FXML transient private GridPane mainPane;
    @FXML transient private Label hint;
    private static final int continueCost = 10;
    transient private final ImagePattern pauseUnEntered = new ImagePattern(new Image("file:Resources\\Images\\StopUE.png"));
    transient private final ImagePattern pauseEntered = new ImagePattern(new Image("file:Resources\\Images\\StopE.png"));
    transient private final AudioClip buttonESound = new AudioClip(new File("Resources\\Sound Effects\\button.wav").toURI().toString());
    transient private final AudioClip clickSound = new AudioClip(new File("Resources\\Sound Effects\\achat.wav").toURI().toString());
    transient private final AudioClip errorSound = new AudioClip(new File("Resources\\Sound Effects\\error.wav").toURI().toString());
    transient private final AudioClip deadSound = new AudioClip(new File("Resources\\Sound Effects\\dead.wav").toURI().toString());
    transient private final AudioClip starSound = new AudioClip(new File("Resources\\Sound Effects\\star.wav").toURI().toString());
    transient private final AudioClip switchSound = new AudioClip(new File("Resources\\Sound Effects\\colorswitch.wav").toURI().toString());
    transient private Scene pauseMenu;
    transient private Scene gameOver;
    transient private ScheduledFuture<?> futureObjectMaker;
    transient private final Runnable gameObjectMaker = () -> {
        if (getLatestInserted() == null || getLatestInserted().getTranslateY() > -50) {
            if (getLatestInserted() != null) {
                if (getLatestInserted().getController() instanceof Obstacle) {
                    spawnStar(gamePane);
                } else if (!(getLatestInserted().getController() instanceof Switch) && (Main.RANDOM.nextBoolean() || Main.RANDOM.nextBoolean())) {
                    spawnSwitch(gamePane);
                } else {
                    spawnRandomObstacle(gamePane);
                }
            } else {
                spawnRandomObstacle(gamePane);
                Platform.runLater(() -> getLatestInserted().setTranslateY(0));
            }
        }
    };

    @FXML void initialize() throws IOException {
        pauseMenu = new Scene(FXMLLoader.load(getClass().getResource("PauseMenu.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        gameOver = new Scene(FXMLLoader.load(getClass().getResource("EndGameMenu.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        scoreLabel.toFront();
        pauseButton.toFront();
        pauseButton.setFill(pauseUnEntered);
        pauseButton.setOnMouseEntered((event) ->
        {
            pauseButton.setFill(pauseEntered);
            buttonESound.play();
        });
        pauseButton.setOnMouseExited((event) ->
                pauseButton.setFill(pauseUnEntered));
    }
    @FXML void onPausePressed() {
        stopAllCollisionAndSpawn();
        Scene gameScene = getMainStage().getScene();
        Button backButton = (Button) pauseMenu.lookup("#resumeButton");
        hint.setVisible(true);
        Button saveButton = (Button) pauseMenu.lookup("#saveButton");
        saveButton.setOnMouseClicked((e) -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                onSaveGame();
                ((Button) e.getSource()).setDisable(true);
            }
        });

        backButton.setOnMouseClicked((e) ->
        {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                getMainStage().setScene(gameScene);
                gamePane.setOnMouseClicked((event) ->
                {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        saveButton.setDisable(false);
                        hint.setVisible(false);
                        startAllCollisionAndSpawn();
                        gamePane.setOnMouseClicked((event1 -> {}));
                    }
                });
            }
        });

        Label curScoreLabel = (Label) pauseMenu.lookup("#curScore");
        curScoreLabel.setText(scoreLabel.getText());
        Label highScore = (Label) pauseMenu.lookup("#maxScoreLabel");
        highScore.setText(getPlayer().getEndlessHighScore() + "");
        Button exitButton = (Button)  pauseMenu.lookup("#exitButton");
        exitButton.setOnMouseClicked(this::onExit);

        getMainStage().setScene(pauseMenu);
    }
    private void startAllCollisionAndSpawn() {
        futureObjectMaker = Main.scheduleForExecution(gameObjectMaker, 0, 2);
        for (SerializableNode node : getGameNodes()) {
            if (node.getController() instanceof Obstacle) {
                node.getController().startCollisionDetector(getBall(), this::onCollisionDetected);
            } else if (node.getController() instanceof Star) {
                node.getController().startCollisionDetector(getBall(), () -> onStarCollected(node));
            } else if (node.getController() instanceof Switch) {
                node.getController().startCollisionDetector(getBall(), () -> onSwitchCollected(node));
            } else {
                System.out.println("This should not happen");
            }
        }
        getBall().initializeMover(getGameNodes(), gamePane, this::onCollisionDetected);
    }
    public void onSaveGame() {
        try {
            getPlayer().saveGame(this.clone());
        } catch (Exception i) {
            i.printStackTrace();
        }
    }
    public void load(Game toLoad) {
        spawnBall(gamePane, mainPane);
        scoreLabel.setTextFill(getBall().getColor());
        stopAllCollisionAndSpawn();
        if (toLoad != null) {
            load(gamePane, mainPane, toLoad);
        }
        scoreLabel.setTextFill(getBall().getColor());
        gamePane.setOnMouseClicked((event) ->
        {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                hint.setVisible(false);
                startAllCollisionAndSpawn();
            }
            gamePane.setOnMouseClicked(e -> {});
        });
        scoreLabel.setText(Integer.toString(getCurScore()));
    }
    private void stopAllCollisionAndSpawn() {
        try {
            getBall().stop();
            futureObjectMaker.cancel(true);
        } catch (NullPointerException ignore) {
        }
        for (SerializableNode node : getGameNodes()) {
            node.getController().stopCollisionDetector();
        }
    }
    @Override
    public void onCollisionDetected() {
        Scene gameScene = getMainStage().getScene();
        stopAllCollisionAndSpawn();
        deadSound.play();
        onSaveScore();
        Button restartButton = (Button) gameOver.lookup("#restartButton");
        restartButton.setOnMouseClicked((e) -> {
            onExit(e);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EndlessGame.fxml"));
            try {
                Scene endlessGameScene = new Scene(loader.load(), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
                ((EndlessGame) loader.getController()).setMainStage(getMainStage());
                ((EndlessGame) loader.getController()).setPlayer(getPlayer());
                getMainStage().setScene(endlessGameScene);
                ((EndlessGame) loader.getController()).load(null);
            } catch (Exception err) {
                System.out.println("Resource Deleted!");
                err.printStackTrace();
            }
        });

        Label curScoreLabel = (Label) gameOver.lookup("#score");
        curScoreLabel.setText(getCurScore()+"");

        Label totalScore = (Label) gameOver.lookup("#totalStars");
        totalScore.setText(getPlayer().getBalance()+"");

        Label continueCost = (Label) gameOver.lookup("#continueCost");
        continueCost.setText(Integer.toString(EndlessGame.continueCost));
        Button continueButton = (Button) gameOver.lookup("#continueButton");
        continueButton.setDisable(!getPlayer().canDoTransaction(EndlessGame.continueCost) || isContinued());
        continueButton.setVisible(!isContinued());
        continueButton.setOnMouseClicked(event ->
        {
            setContinued(true);
            getPlayer().doTransaction(EndlessGame.continueCost);
            getPlayer().save();
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                getMainStage().setScene(gameScene);
                gamePane.setOnMouseClicked((mouseEvent) ->
                {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        hint.setVisible(false);
                        startAllCollisionAndSpawn();
                        gamePane.setOnMouseClicked(event1 -> {});
                    }
                });
            }
        });

        Button exitButton = (Button)  gameOver.lookup("#exitButton");
        exitButton.setOnMouseClicked((this::onExit));
        getMainStage().setScene(gameOver);
        try {
            Thread.sleep(Main.UPDATE_IN*45);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hint.setVisible(true);
    }
    @Override
    public void onStarCollected(SerializableNode star) {
        setCurScore(getCurScore()+1);
        star.getController().stopCollisionDetector();
        getGameNodes().remove(star);
        gamePane.getChildren().remove(star.getNode());
        scoreLabel.setText(Integer.toString(getCurScore()));
        starSound.play();
    }
    @Override
    public void onSwitchCollected(SerializableNode colorSwitch) {
        colorSwitch.getController().stopCollisionDetector();
        getBall().setColor(((Switch) colorSwitch.getController()).getNewColor());
        scoreLabel.setTextFill(((Switch) colorSwitch.getController()).getNewColor());
        getGameNodes().remove(colorSwitch);
        gamePane.getChildren().remove(colorSwitch.getNode());
        switchSound.play();
    }
    @Override
    public void onSaveScore() {
        getPlayer().saveScore(getCurScore());
    }
    @Override
    public void onExit(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            super.onExit(event);
            for (SerializableNode node : getGameNodes()) {
                gamePane.getChildren().remove(node.getNode());
            }
            getGameNodes().clear();
        }
    }
}
