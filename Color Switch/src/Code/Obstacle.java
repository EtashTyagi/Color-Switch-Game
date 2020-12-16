package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

public abstract class Obstacle implements Collidable {
    transient volatile private ScheduledFuture<?> mover;
    transient volatile private ScheduledFuture<?> collisionDetector;

    @FXML void initialize() {
        Platform.runLater(() -> mover = Main.scheduleForExecution(this::doMovement, 0, 1));
    }
    abstract void setDifficulty(double value);
    abstract void doMovement();
    abstract public double getHeight();
    abstract public double getWidth();
    @Override
    public void load(Collidable collidable) {
        assert collidable instanceof Obstacle;
        load((Obstacle) collidable);
    }
    abstract public void load(Obstacle obstacle);
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