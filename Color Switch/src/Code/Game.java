package Code;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Game implements Serializable, Cloneable {
    private Player player;
    private int curScore;
    private Ball ball;
    private double currentDifficultyMedian = 0.5;
    private static final double difficultySD = 0.05;
    transient private Stage mainStage;
    private static final double heightOffset = -100;
    private ArrayList<SerializableNode> gameNodes = new ArrayList<>();
    transient private Color passableColor;
    private int passableColorInt;
    private static final String objectDir = "";
    private static final String ballFile = "ball.fxml";
    private static final String starFile = "Star.fxml";
    private static final String switchFile = "Switch.fxml";
    private static final String[] obstacleFiles =
            {"AdjacentDoubleCircleObstacle.fxml", "ConcentricTripleCircleObstacle.fxml",
                    "CrossObstacle.fxml", "LinesObstacle.fxml",
                    "SingleCircleObstacle.fxml", "SquareObstacle.fxml",
                    "TriangleObstacle.fxml"};


    public void setPlayer(Player player) {
        this.player = player;
    }
    public Player getPlayer() {
        return this.player;
    }
    public void setMainStage(Stage primaryStage) {
        this.mainStage = primaryStage;
    }
    public void onExit(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            for (SerializableNode node : getGameNodes()) {
                if (node.getController() instanceof Obstacle) {
                    ((Obstacle) node.getController()).stopAllSubTasks();
                } else {
                    node.getController().stopCollisionDetector();
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Code/MainMenu.fxml"));
            Parent mainMenu;
            try {
                mainMenu = loader.load();
                MainMenu controller = loader.getController();
                controller.setMainStage(mainStage);
                mainStage.setScene(new Scene(mainMenu, Main.STAGE_WIDTH, Main.STAGE_HEIGHT));
                controller.setPlayer(player);
            } catch (Exception e) {
                System.out.println("Resource Deleted!");
                e.printStackTrace();
            }
        }
    }
    public Stage getMainStage() {
        return mainStage;
    }
    public Ball getBall() {
        return ball;
    }
    public SerializableNode getLatestInserted() {
        return gameNodes.size() == 0 ? (null) : (gameNodes.get(gameNodes.size() - 1));
    }
    void spawnBall(GridPane gameSpace, GridPane clickSpace) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(objectDir + ballFile));
                Circle ball = fxmlLoader.load();
                this.ball = fxmlLoader.getController();
                gameSpace.add(ball, 0, 0);
                GridPane.setHalignment(ball, HPos.LEFT);
                GridPane.setValignment(ball, VPos.TOP);
                ball.setTranslateY(500);
                clickSpace.setOnMousePressed((event) ->
                {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        this.ball.goUp();
                    }
                });
                this.ball.initializeMover(gameNodes, gameSpace, this::onCollisionDetected);
                passableColor = this.ball.getColor();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    void spawnRandomObstacle(GridPane gameSpace) {
        currentDifficultyMedian += 0.02;
        double currentDifficulty = Main.RANDOM.nextGaussian() * difficultySD + currentDifficultyMedian;
        currentDifficulty = Math.min(1, Math.max(0, currentDifficulty));
        int randIndex = Main.RANDOM.nextInt(obstacleFiles.length);
        SerializableNode obstacle = new SerializableNode(obstacleFiles[randIndex], gameSpace);
        Obstacle obstacleController = (Obstacle) obstacle.getController();
        obstacle.setTranslateY(heightOffset - obstacleController.getHeight());
        if (obstacleController instanceof TriangleObstacle) {
            passableColorInt = -1;
            for (int index = 0; index < Main.GAME_COLORS.length; index++) {
                if (passableColor.equals(Main.GAME_COLORS[index])) {
                    passableColorInt = index;
                }
            }
            if (passableColorInt == -1) {
                passableColor = ball.getColor();
                passableColorInt = ball.getColIndex();
            }
            ((TriangleObstacle) obstacleController).setPassableColor(passableColor);
        }
        ((Obstacle) obstacle.getController()).setDifficulty(currentDifficulty);
        obstacle.startCollisionDetector(ball, this::onCollisionDetected);
        gameNodes.add(obstacle);
    }
    void spawnSwitch(GridPane gameSpace) {
            SerializableNode colorSwitcher = new SerializableNode(switchFile, gameSpace);
            Switch switchController = (Switch) colorSwitcher.getController();
            colorSwitcher.setTranslateY(heightOffset - switchController.getHeight());
            passableColor = getRandomColorExcept();
            switchController.setNewColor(passableColor);
            colorSwitcher.startCollisionDetector(ball, () -> onSwitchCollected(colorSwitcher));
            gameNodes.add(colorSwitcher);
    }
    void spawnStar(GridPane gameSpace) {
            SerializableNode star = new SerializableNode(starFile, gameSpace);
            Star starController = (Star) star.getController();
            star.setTranslateY(heightOffset - starController.getHeight());
            star.startCollisionDetector(ball, () -> onStarCollected(star));
            gameNodes.add(star);
    }
    ArrayList<SerializableNode> getGameNodes() {
        return gameNodes;
    }
    public abstract void onSaveScore(MouseEvent e);
    public abstract void onCollisionDetected();
    public abstract void onStarCollected(SerializableNode star);
    public abstract void onSwitchCollected(SerializableNode colorSwitch);
    private Color getRandomColorExcept() {
        ArrayList<Color> newCols = new ArrayList<>();
        for (Color c : Main.GAME_COLORS) {
            if (!c.equals(passableColor)) {
                newCols.add(c);
            }
        }
        return newCols.get(Main.RANDOM.nextInt(newCols.size()));
    }
    public void load(GridPane gameSpace, GridPane clickSpace, Game game) {
        gameSpace.getChildren().clear();
        ball = null;
        spawnBall(gameSpace, clickSpace);
        ball.load(game.ball);
        ball.stop();
        curScore = game.curScore;
        passableColorInt = game.passableColorInt;
        currentDifficultyMedian = game.currentDifficultyMedian;
        passableColor = Main.GAME_COLORS[passableColorInt];

        for (SerializableNode node : game.gameNodes) {
            gameNodes.add(new SerializableNode(node, gameSpace));
        }
    }
    public void setCurScore(int curScore) {
        this.curScore = curScore;
    }
    public int getCurScore() {
        return curScore;
    }
    @Override
    protected Game clone() throws CloneNotSupportedException {
        Game clone = (Game) super.clone();
        clone.ball = ball.clone();
        clone.gameNodes = new ArrayList<>();
        for (SerializableNode node : gameNodes) {
            clone.gameNodes.add(node.clone());
        }
        return clone;
    }
}
class SerializableNode implements Serializable, Cloneable {
    private final String fxmlFile;
    transient private Node node;
    private double translateX;
    private double translateY;
    private Collidable controller;
    private static final String objectDir = "";

    public SerializableNode(String fxmlFile, GridPane gamePane) {
        this.fxmlFile = fxmlFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(objectDir + fxmlFile));
            node = loader.load();
            controller = loader.getController();
            Platform.runLater(() ->
            {
                gamePane.add(node, 0, 0);
                GridPane.setHalignment(node, HPos.LEFT);
                GridPane.setValignment(node, VPos.TOP);

                translateY = node.getTranslateY();
                translateX = node.getTranslateX();
                node.toBack();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SerializableNode(SerializableNode saved, GridPane gamePane) {
        fxmlFile = saved.fxmlFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(objectDir + fxmlFile));
            node = loader.load();
            gamePane.add(node, 0, 0);
            GridPane.setHalignment(node, HPos.LEFT);
            GridPane.setValignment(node, VPos.TOP);
            controller = loader.getController();
            Platform.runLater(() ->
            {
                controller.load(saved.controller);
                setTranslateY(saved.translateY);
                setTranslateX(saved.translateX);
                node.toBack();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startCollisionDetector(Ball ball, Runnable runnable) {
        controller.startCollisionDetector(ball, runnable);
    }
    public void setTranslateX(double translateX) {
        this.node.setTranslateX(translateX);
        this.translateX = translateX;
    }
    public void setTranslateY(double translateY) {
        this.node.setTranslateY(translateY);
        this.translateY = translateY;
    }
    public double getTranslateX() {
        return translateX;
    }
    public double getTranslateY() {
        return translateY;
    }
    public Collidable getController() {
        return controller;
    }
    public Node getNode() {
        return node;
    }
    @Override
    protected SerializableNode clone() throws CloneNotSupportedException {
        return (SerializableNode) super.clone();
    }
}

