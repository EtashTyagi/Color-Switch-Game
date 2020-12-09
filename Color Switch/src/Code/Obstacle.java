package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

import java.util.concurrent.ScheduledFuture;

//TODO: Check Error [Difficulty Must Be 0-1]
public abstract class Obstacle implements Collidable {
    private double difficulty;
    private Color[] colors;
    private Color passingColor;
    private ScheduledFuture<?> mover;

    @FXML void initialize() {
        Platform.runLater(() -> mover = Main.scheduleForExecution(this::doMovement, 0, 1));
    }
    abstract void doMovement();
    abstract public double getHeight();
    abstract public double getWidth();
    abstract double xOffset();
    final void stopMovement() {
        mover.cancel(false);
    }
}