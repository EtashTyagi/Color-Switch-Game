package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.io.IOException;
import java.util.concurrent.ScheduledFuture;

public class EndlessGame extends Game {
    @FXML private Circle pauseButton;
    @FXML private Label scoreLabel;
    @FXML private GridPane gamePane;
    @FXML private GridPane mainPane;
    private Scene pauseMenu;
    private Scene gameOver;
    private ScheduledFuture<?> futureObjectMaker;
    private final Runnable gameObjectMaker = () -> {
        if (getLatestNodeAndController() == null || getLatestNodeAndController().getKey().getTranslateY() > -50) {
            if (getLatestNodeAndController() != null &&
                    getLatestNodeAndController().getValue() instanceof Obstacle) {
                spawnStar(gamePane);
            } else if (getLatestNodeAndController() != null) {
                if (!(getLatestNodeAndController().getValue() instanceof Switch) && Main.RANDOM.nextBoolean()) {
                    spawnSwitch(gamePane);
                } else {
                    spawnRandomObstacle(gamePane);
                }
            } else {
                spawnRandomObstacle(gamePane);
                Platform.runLater(() -> getLatestNodeAndController().getKey().setTranslateY(0));
            }
        }
    };

    @FXML void initialize() throws IOException {
        pauseMenu = new Scene(FXMLLoader.load(getClass().getResource("PauseMenu.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        gameOver = new Scene(FXMLLoader.load(getClass().getResource("EndGameMenu.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        scoreLabel.toFront();
        pauseButton.toFront();
        spawnBall(gamePane, mainPane);
        futureObjectMaker = Main.scheduleForExecution(gameObjectMaker, 0, 5);
    }

    @FXML void onPausePressed() {
        Scene gameScene = getMainStage().getScene();
        futureObjectMaker.cancel(false);
        getBall().stop();
        getGameObjectsNodeAndController().forEach((K,V) ->
                V.stopCollisionDetector());

        Button backButton = (Button) pauseMenu.lookup("#resumeButton");
        backButton.setOnMouseClicked((e) -> {
            futureObjectMaker = Main.scheduleForExecution(gameObjectMaker, 0, 1);
            getMainStage().setScene(gameScene) ;
            getGameObjectsNodeAndController().forEach((K,V) -> {
                if (V instanceof Obstacle) {
                    V.startCollisionDetector(getBall(), this::onCollisionDetected);
                } else if (V instanceof Star) {
                    V.startCollisionDetector(getBall(), () -> onStarCollected(new Pair<>(K, (Star) V)));
                } else {
                    V.startCollisionDetector(getBall(), () -> onSwitchCollected(new Pair<>(K, (Switch) V)));
                }
            });
            getBall().initializeMover(getGameObjectsNodeAndController(), gamePane);
        });

        Label curScoreLabel = (Label) pauseMenu.lookup("#curScore");
        curScoreLabel.setText(scoreLabel.getText());
        Label highScore = (Label) pauseMenu.lookup("#maxScoreLabel");
        highScore.setText(getPlayer().getEndlessHighScore() + "");
        Button exitButton = (Button)  pauseMenu.lookup("#exitButton");
        exitButton.setOnMouseClicked(this::onExit);

        getMainStage().setScene(pauseMenu);
    }
    @Override
    public void onSaveScore(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            getPlayer().setEndlessHighScore(Math.max(getPlayer().getEndlessHighScore(), Integer.parseInt(scoreLabel.getText())));
            getPlayer().save();
        }
    }
    @Override
    public void onCollisionDetected() {
        futureObjectMaker.cancel(true);
        getBall().stop();
        getGameObjectsNodeAndController().forEach((K, V) ->
        Platform.runLater(() -> {
            if (V instanceof Obstacle) {
                ((Obstacle) V).stopAllSubTasks();
            } else {
                V.stopCollisionDetector();
            }
            gamePane.getChildren().remove(K);
            getGameObjectsNodeAndController().remove(K);
        }));

        Button restartButton = (Button) gameOver.lookup("#restartButton");
        restartButton.setOnMouseClicked((e) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EndlessGame.fxml"));
            try {
                Scene endlessGameScene = new Scene(loader.load(), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
                ((Game) loader.getController()).setMainStage(getMainStage());
                ((Game) loader.getController()).setPlayer(getPlayer());
                getMainStage().setScene(endlessGameScene);
            } catch (Exception err) {
                System.out.println("Resource Deleted!");
                err.printStackTrace();
            }
        });

        Button saveButton = (Button) gameOver.lookup("#saveScoreButton");
        saveButton.setOnMouseClicked(this::onSaveScore);

        Label curScoreLabel = (Label) gameOver.lookup("#score");
        curScoreLabel.setText(scoreLabel.getText());

        Button exitButton = (Button)  gameOver.lookup("#exitButton");
        exitButton.setOnMouseClicked(this::onExit);

        getMainStage().setScene(gameOver);
    }
    @Override
    public void onStarCollected(Pair<Node, Star> nodeStarPair) {
        nodeStarPair.getValue().stopCollisionDetector();
        getGameObjectsNodeAndController().remove(nodeStarPair.getKey());
        gamePane.getChildren().remove(nodeStarPair.getKey());
        scoreLabel.setText(Integer.toString(Integer.parseInt(scoreLabel.getText()) + 1));
    }
    @Override
    public void onSwitchCollected(Pair<Node, Switch> nodeSwitchPair) {
        nodeSwitchPair.getValue().stopCollisionDetector();
        getBall().setColor(nodeSwitchPair.getValue().getNewColor());
        getGameObjectsNodeAndController().remove(nodeSwitchPair.getKey());
        gamePane.getChildren().remove(nodeSwitchPair.getKey());
    }
}
