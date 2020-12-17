package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;

public class Ball implements Serializable, Cloneable {
    @FXML transient private Circle ball;
    private final static double radius = 10;
    private static final double upVel = 0.3;
    private double[] ballPos = new double[2];
    private int colIndex;
    private double velocity;
    transient private ScheduledFuture<?> mover;

    public void initialize() {
        colIndex = Main.RANDOM.nextInt(Main.GAME_COLORS.length);
        ball.setFill(Main.GAME_COLORS[colIndex]);
        ball.setTranslateX(ball.getTranslateX() + xOffset());
        ballPos[0] = ball.getTranslateX();
        ballPos[1] = ball.getTranslateY();
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
    public int getColIndex() {
        return colIndex;
    }
    public void setColor(Color color) {
        boolean validColor = false;
        for (int index = 0; index < Main.GAME_COLORS.length ; index++) {
            if (Main.GAME_COLORS[index] == color) {
                colIndex = index;
                validColor = true;
                break;
            }
        }
        if (validColor) {
            Platform.runLater(() -> ball.setFill(color));
        }
    }
    public Color getColor() {
        return (Color) ball.getFill();
    }
    public void goUp() {
        velocity = upVel;
    }
    public void initializeMover(ArrayList<SerializableNode> gameObjects, Pane gameSpace, Runnable onBallDown) {
        mover = Main.scheduleForExecution(() -> {
            try {
                if (ball.getTranslateY() <= 570) {
                    Platform.runLater(() ->
                    {
                        ball.setTranslateY(ball.getTranslateY() - velocity * Main.UPDATE_IN);
                        double prevVel = velocity;
                        velocity = Math.max(prevVel - 0.001 * Main.UPDATE_IN, -0.5);
                        ballPos[0] = ball.getTranslateX();
                        ballPos[1] = ball.getTranslateY();
                    });
                } else {
                    ball.setTranslateY(500);
                    ballPos[0] = ball.getTranslateX();
                    ballPos[1] = ball.getTranslateY();
                    Platform.runLater(onBallDown);
                }
                double half = Main.STAGE_HEIGHT / 2;
                double curPos = ball.getTranslateY();
                if (curPos < half) {
                    Platform.runLater(() ->
                    {
                        ball.setTranslateY(ball.getTranslateY() - 1 * Main.UPDATE_IN * (curPos - half) / half);
                        ballPos[0] = ball.getTranslateX();
                        ballPos[1] = ball.getTranslateY();
                    });
                    ArrayList<SerializableNode> toRemoves = new ArrayList<>();
                    ArrayList<Node> removalNodes = new ArrayList<>();
                    gameObjects.forEach((gameObject) ->
                    {
                        Platform.runLater(() -> gameObject.setTranslateY(gameObject.getTranslateY() - 1 * Main.UPDATE_IN * (curPos - half) / half));
                        if (gameObject.getTranslateY() > Main.STAGE_HEIGHT) {
                            if (gameObject.getController() instanceof Obstacle) {
                                ((Obstacle) gameObject.getController()).stopAllSubTasks();
                            } else {
                                gameObject.getController().stopCollisionDetector();
                            }
                            toRemoves.add(gameObject);
                            removalNodes.add(gameObject.getNode());
                        }
                    });
                    gameObjects.removeAll(toRemoves);
                    Platform.runLater(() -> gameSpace.getChildren().removeAll(removalNodes));
                }
            } catch (Exception ignore) {

            }
        }, 0, 1);
    }
    private double xOffset() {
        return Main.STAGE_WIDTH / 2 - radius;
    }
    public void load(Ball savedBall) {
        ballPos[0] = savedBall.ballPos[0];
        ballPos[1] = savedBall.ballPos[1];
        ball.setTranslateX(ballPos[0]);
        ball.setTranslateY(ballPos[1]);
        velocity = savedBall.velocity;
        colIndex = savedBall.colIndex;
        ball.setFill(Main.GAME_COLORS[colIndex]);
    }
    public void stop() {
        mover.cancel(false);
    }
    @Override
    protected Ball clone() throws CloneNotSupportedException {
        Ball clone = (Ball) super.clone();
        clone.ballPos = ballPos.clone();
        return clone;
    }
}