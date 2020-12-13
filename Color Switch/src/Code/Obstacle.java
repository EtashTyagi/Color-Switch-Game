package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.util.concurrent.ScheduledFuture;

//TODO: Check Error [Difficulty Must Be 0-1]
public abstract class Obstacle implements Collidable {
    private ScheduledFuture<?> mover;
    private ScheduledFuture<?> collisionDetector;

    @FXML void initialize() {
        Platform.runLater(() -> mover = Main.scheduleForExecution(this::doMovement, 0, 1));
    }
    abstract void doMovement();
    abstract public double getHeight();
    abstract public double getWidth();
    abstract double xOffset();
    public final void stopAllSubTasks() {
        mover.cancel(false);
        stopCollisionDetector();
    }
    @Override
    public void startCollisionDetector(Ball ball, Runnable taskOnCollision) {
        collisionDetector = Main.scheduleForExecution(() ->
                {
                    if (hasCollidedWithBall(ball)) {
                        Platform.runLater(taskOnCollision);
                    }
                }
        , 0, 1);
    }
    @Override
    public void stopCollisionDetector() {
        try {
            collisionDetector.cancel(false);
        } catch (Exception ignore) {

        }
    }
}