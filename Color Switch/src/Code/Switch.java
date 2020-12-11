package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

public class Switch implements Collidable {
    @FXML private GridPane mainPane;
    @FXML private ArrayList<Arc> arcs;
    private ScheduledFuture<?> collisionDetector;
    private Color newColor;

    @FXML private void initialize() {
        Platform.runLater(() -> {
            mainPane.setTranslateX(xOffset() - 1);
        });
    }
    public Color getNewColor() {
        return newColor;
    }
    public void setNewColor(Color color) {
        this.newColor = color;
    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return CollisionDetector.ballAndArcedCircle(arcs, ball, arcs.get(0).getRadiusX(),
                mainPane.getTranslateX() + arcs.get(0).getRadiusX(), mainPane.getTranslateY() + arcs.get(0).getRadiusY(),
                0);
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
    public double getHeight() {
        return 25;
    }
    public double getWidth() {
        return 25;
    }
    public double xOffset() {
        return Main.STAGE_WIDTH/2 - getWidth() / 2;
    }
}
