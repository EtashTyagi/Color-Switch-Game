package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class CrossObstacle extends Obstacle{
    @FXML private Node crossObstacle;
    private double radius;
    private double rotateSpeed = 0.1;

    @FXML private void initialize() {
        doMovement();
    }
    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return false;
    }
    //TODO: Implement Rotation
    @Override
    void doMovement() {
        Thread animationThread = new Thread(() ->
        {
            int updateInTime = 20; // Millisecond
            while (true) {
                Platform.runLater(() -> crossObstacle.setRotate(crossObstacle.getRotate()+rotateSpeed*updateInTime));
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
