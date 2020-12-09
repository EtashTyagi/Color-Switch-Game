package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Game {
    private int curScore;
    private Ball ball;
    private Queue<Pair<Node, Collidable>> gameObjectQueue = new LinkedList<>();
    private Queue<ScheduledFuture<?>> collisionDetector = new LinkedList<>();
    private static final String ballFXML = "ball.fxml";
    private static final String[] obstacleFiles =
            {"AdjacentDoubleCircleObstacle.fxml", "ConcentricTripleCircleObstacle.fxml",
            "CrossObstacle.fxml", "LinesObstacle.fxml",
            "SingleCircleObstacle.fxml", "SquareObstacle.fxml",
            "TriangleObstacle.fxml"};
    private Node latestInsertion;
    private boolean hasEnded = false; // TODO End game on condition
    // TODO GARBAGE COLLECTOR FOR NODES
    public Ball getBall() {
        return ball;
    }
    public Node getLatestInsertion() {
        return latestInsertion;
    }
    void spawnBall(GridPane gameSpace) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Circle ball = fxmlLoader.load(getClass().getResource(ballFXML).openStream());
            this.ball = fxmlLoader.getController();
            gameSpace.add(ball, 0, 0);
            GridPane.setHalignment(ball, HPos.LEFT);
            GridPane.setValignment(ball, VPos.TOP);
            ball.setTranslateY(500);
            gameSpace.setOnMousePressed((event) -> this.ball.goUp());
            AtomicReference<Double> upped = new AtomicReference<>((double) 0); // SYNCHRONISE
            // EXPERIMENTAL: TODO INCLUDE WITH BALL
            Main.scheduleForExecution(() -> {
                double half = Main.STAGE_HEIGHT / 2;
                double curPos = ball.getTranslateY();
                if (curPos < half) {
                    Platform.runLater(() -> {
                        ball.setTranslateY(ball.getTranslateY() -
                                1 * Main.UPDATE_IN * (curPos - half) / half);
                        gameObjectQueue.forEach((object) -> object.getKey().setTranslateY(object.getKey().getTranslateY() -
                                1* Main.UPDATE_IN * (curPos - half) / half));
                        upped.updateAndGet(v -> (v + (-1 * Main.UPDATE_IN * (curPos - half) / half)));
                    });
                }
            }, 0, 1);
        } catch (Exception e) {
            System.out.println(ballFXML + " Resource not found");
            e.printStackTrace();
        }
    }
    void spawnRandomObstacle(GridPane gameSpace) {
        Platform.runLater(() -> {
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
            double topY = -100 - obstacleController.getHeight();
            gameSpace.add(obstacle, 0, 0);
            assert obstacle != null;
            obstacle.setTranslateY(topY);
            GridPane.setHalignment(obstacle, HPos.LEFT);
            GridPane.setValignment(obstacle, VPos.TOP);

            //TODO SEPERATE
            collisionDetector.add(Main.scheduleForExecution(() -> {
                if(obstacleController.hasCollidedWithBall(ball)) {
                    System.out.println("COLLIDED");
                    onCollisionDetected();
                }
            }, 0, 1));
            gameObjectQueue.add(new Pair<>(obstacle, obstacleController));
            if (obstacleController instanceof  TriangleObstacle) {
                ((TriangleObstacle) obstacleController).setPassableColor(ball.getColor());
            }
            obstacle.toBack();
            latestInsertion = obstacle;
        });
    }
    Queue<Pair<Node, Collidable>> getGameObjects() {
        return gameObjectQueue;
    }
    Queue<ScheduledFuture<?>> getCollisionDetector() {
        return collisionDetector;
    }
    //TODO: Spawn Switch Function [Switches Color of ball]
    void spawnSwitch(GridPane gameSpace, int centerX, int centerY) {
    }
    //TODO: Spawn Star Function
    void spawnStar(GridPane gameSpace, int centerX, int centerY) {
    }
    //TODO: On Pause Press Function
    void onPausePress() {
    }
    public abstract void updateGameState();
    public abstract void saveGameData();
    public abstract void onCollisionDetected();
    @FXML abstract void initialize();
}
