package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

public class Ball {
    @FXML private Circle ball;
    private final double radius = 10;
    private static final double upVel = 0.3;
    private double velocity;
    private ScheduledFuture<?> mover;

    public void initialize() {
        Platform.runLater(() ->
        {
            ball.setFill(Main.GAME_COLORS[Main.RANDOM.nextInt(Main.GAME_COLORS.length)]);
            ball.setTranslateX(ball.getTranslateX() + xOffset());
        });
    }
    public double getCenterY() {
        return ball.getTranslateY() + radius;
    }
    public double getCenterX() {
        return ball.getTranslateX() + radius;
    }
    public double getRadius() {
        return radius;
    }
    public void setColor(Color color) {
        boolean validColor = false;
        for (int index = 0; index < Main.GAME_COLORS.length ; index++) {
            validColor = validColor || (Main.GAME_COLORS[index] == color);
        }
        if (validColor) {
            Platform.runLater(() -> ball.setFill(color));
        } else {
            // TODO Throw Error Here
        }
    }
    public Color getColor() {
        return (Color) ball.getFill();
    }
    public void goUp() {
        velocity = upVel;
    }
    public void initializeMover(HashMap<Node, Collidable> gameObjectsNodeAndController, Pane gameSpace) {
        mover = Main.scheduleForExecution(() -> {
            if (ball.getTranslateY() <= 550) {
                Platform.runLater(() -> ball.setTranslateY(ball.getTranslateY() - velocity * Main.UPDATE_IN));
                Platform.runLater(() -> velocity = Math.max(velocity - 0.001 * Main.UPDATE_IN, -0.5));
            } else {
                ball.setTranslateY(545);
                velocity = 0;
            }
            AtomicReference<Double> upped = new AtomicReference<>((double) 0); // SYNCHRONISE
            double half = Main.STAGE_HEIGHT / 2;
            double curPos = ball.getTranslateY();
            if (curPos < half) {
                Platform.runLater(() -> ball.setTranslateY(ball.getTranslateY() - 1 * Main.UPDATE_IN * (curPos - half) / half));
                gameObjectsNodeAndController.forEach((K, V) ->
                {
                    Platform.runLater(() -> K.setTranslateY(K.getTranslateY() - 1 * Main.UPDATE_IN * (curPos - half) / half));
                    if (K.getTranslateY() > Main.STAGE_HEIGHT) {
                        if (V instanceof Obstacle) {
                            ((Obstacle) V).stopAllSubTasks();
                        } else {
                            V.stopCollisionDetector();
                        }
                        Platform.runLater(() ->
                        {
                            gameSpace.getChildren().remove(K);
                            gameObjectsNodeAndController.remove(K);
                        });
                    }
                    upped.updateAndGet(v -> (v + (-1 * Main.UPDATE_IN * (curPos - half) / half)));
                });
            };
        }, 0, 1);
    }
    private double xOffset() {
        return Main.STAGE_WIDTH / 2 - radius;
    }
    public void stop() {
        mover.cancel(false);
    }
}