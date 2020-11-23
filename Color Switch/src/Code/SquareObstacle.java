package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class SquareObstacle extends Obstacle{
    @FXML private Group square;
    private double sideSize;
    private double rotateSpeed = 0.1;

    @FXML private void initialize() {
        doMovement();
    }

    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return false;
    }
    @Override
    void doMovement() {
        Thread animationThread = new Thread(() ->
        {
            int updateInTime = 20; // Millisecond
            while (true) {
                Platform.runLater(() -> square.setRotate(square.getRotate()+rotateSpeed*updateInTime));
                try {
                    Thread.sleep(updateInTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
}
