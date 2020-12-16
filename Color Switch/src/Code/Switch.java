package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

public class Switch implements Collidable {
    @FXML transient private GridPane mainPane;
    @FXML transient private ArrayList<Arc> arcs;
    transient private ScheduledFuture<?> collisionDetector;
    transient private Color newColor;
    private int nextColIndex;

    @FXML private void initialize() {
        Platform.runLater(() -> {
            mainPane.setTranslateX(xOffset() - 1);
            for (int index = 0; index < arcs.size(); index++) {
                arcs.get(index).setFill(Main.GAME_COLORS[index]);
            }
        });
    }
    public Color getNewColor() {
        return newColor;
    }
    public void setNewColor(Color color) {
        for (int index = 0; index < Main.GAME_COLORS.length; index++) {
            if (Main.GAME_COLORS[index].equals(color)) {
                nextColIndex = index;
            }
        }
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
    @Override
    public void load(Collidable collidable) {
        assert collidable instanceof Switch;
        nextColIndex = ((Switch) collidable).nextColIndex;
        setNewColor(Main.GAME_COLORS[nextColIndex]);
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
