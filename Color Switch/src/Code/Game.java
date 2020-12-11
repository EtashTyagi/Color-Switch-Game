package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game {
    private int curScore;
    private Ball ball;
    private static final double heightOffset = -100;
    private final HashMap<Node, Collidable> gameObjectsNodeAndController = new HashMap<>();
    private Pair<Node, Collidable> latestNodeAndController;
    private boolean hasEnded = false; // TODO End game on condition
    private Color passableColor;
    private static final String ballFile = "ball.fxml";
    private static final String starFile = "Star.fxml";
    private static final String switchFile = "Switch.fxml";
    private static final String[] obstacleFiles =
            {"AdjacentDoubleCircleObstacle.fxml", "ConcentricTripleCircleObstacle.fxml",
                    "CrossObstacle.fxml", "LinesObstacle.fxml",
                    "SingleCircleObstacle.fxml", "SquareObstacle.fxml",
                    "TriangleObstacle.fxml"};

    public Ball getBall() {
        return ball;
    }
    public Pair<Node, Collidable> getLatestNodeAndController() {
        return latestNodeAndController;
    }
    void spawnBall(GridPane gameSpace, GridPane clickSpace) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Circle ball = fxmlLoader.load(getClass().getResource(ballFile).openStream());
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
            Platform.runLater(() ->
            {
                this.ball.initializeMover(gameObjectsNodeAndController, gameSpace);
                passableColor = this.ball.getColor();
            });
        } catch (Exception e) {
            System.out.println(ballFile + " Resource not found"); // TODO REPORT PROPERLY
            e.printStackTrace();
        }
    }
    void spawnRandomObstacle(GridPane gameSpace) {
        int randIndex = Main.RANDOM.nextInt(obstacleFiles.length);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane obstacle = null;
        try {
            obstacle = fxmlLoader.load(getClass().getResource(obstacleFiles[randIndex]).openStream());
        } catch (IOException e) {
            System.out.println("Resource Not Found " + obstacleFiles[randIndex]);
            e.printStackTrace();
        }
        Obstacle obstacleController = fxmlLoader.getController();
        double topY = heightOffset - obstacleController.getHeight();
        Pane finalObstacle = obstacle;
        Platform.runLater(() -> {
            gameSpace.add(finalObstacle, 0, 0);
            assert finalObstacle != null;
            finalObstacle.setTranslateY(topY);
            GridPane.setHalignment(finalObstacle, HPos.LEFT);
            GridPane.setValignment(finalObstacle, VPos.TOP);
            obstacleController.startCollisionDetector(this.ball, this::onCollisionDetected);
            gameObjectsNodeAndController.put(finalObstacle, obstacleController);
            if (obstacleController instanceof  TriangleObstacle) {
                ((TriangleObstacle) obstacleController).setPassableColor(passableColor);
            }
            finalObstacle.toBack();
            latestNodeAndController = new Pair<>(finalObstacle, obstacleController);
        });
    }
    HashMap<Node, Collidable> getGameObjectsNodeAndController() {
        return gameObjectsNodeAndController;
    }
    //TODO: Spawn Switch Function [Switches Color of ball]
    void spawnSwitch(GridPane gameSpace) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane switchPane = null;
        try {
            switchPane = fxmlLoader.load(getClass().getResource(switchFile).openStream());
        } catch (IOException e) {
            System.out.println("Resource Not Found " + switchFile);
            e.printStackTrace();
        }
        Switch switchController = fxmlLoader.getController();
        double topY = heightOffset - switchController.getHeight();
        Pane finalSwitch = switchPane;
        Platform.runLater(() -> {
            passableColor = getRandomColorExcept();
            switchController.setNewColor(passableColor);
            gameSpace.add(finalSwitch, 0, 0);
            assert finalSwitch != null;
            finalSwitch.setTranslateY(topY);
            GridPane.setHalignment(finalSwitch, HPos.LEFT);
            GridPane.setValignment(finalSwitch, VPos.TOP);
            switchController.startCollisionDetector(this.ball,
                    () -> onSwitchCollected(new Pair<>(finalSwitch, switchController)));
            gameObjectsNodeAndController.put(finalSwitch, switchController);
            finalSwitch.toBack();
            latestNodeAndController = new Pair<>(finalSwitch, switchController);
        });
    }
    void spawnStar(GridPane gameSpace) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane starPane = null;
        try {
            starPane = fxmlLoader.load(getClass().getResource(starFile).openStream());
        } catch (IOException e) {
            System.out.println("Resource Not Found " + starFile);
            e.printStackTrace();
        }
        Star starController = fxmlLoader.getController();
        double topY = heightOffset - starController.getHeight();
        Pane finalStar = starPane;
        Platform.runLater(() -> {
            gameSpace.add(finalStar, 0, 0);
            assert finalStar != null;
            finalStar.setTranslateY(topY);
            GridPane.setHalignment(finalStar, HPos.LEFT);
            GridPane.setValignment(finalStar, VPos.TOP);
            starController.startCollisionDetector(this.ball, () -> onStarCollected(new Pair<>(finalStar, starController)));
            gameObjectsNodeAndController.put(finalStar, starController);
            finalStar.toBack();
            latestNodeAndController = new Pair<>(finalStar, starController);
        });
    }
    //TODO: On Pause Press Function
    void onPausePress() {
    }
    public abstract void updateGameState();
    public abstract void saveGameData();
    public abstract void onCollisionDetected();
    public abstract void onStarCollected(Pair<Node, Star> nodeStarPair);
    public abstract void onSwitchCollected(Pair<Node, Switch> nodeSwitchPair);
    @FXML abstract void initialize();
    private Color getRandomColorExcept() {
        ArrayList<Color> newCols = new ArrayList<>();
        for (Color c : Main.GAME_COLORS) {
            if (!c.equals(passableColor)) {
                newCols.add(c);
            }
        }
        return newCols.get(Main.RANDOM.nextInt(newCols.size()));
    }
}
