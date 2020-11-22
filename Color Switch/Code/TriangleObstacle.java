package Code;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class TriangleObstacle extends Obstacle{
    @FXML private Group triangle;
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
    //TODO: Implement Rotation
    @Override
    void doMovement() {
        Thread animationThread = new Thread(() ->
        {
            int updateInTime = 20; // Millisecond
            while (true) {
                try {
                    triangle.setRotate(triangle.getRotate()+rotateSpeed*updateInTime);
                    Thread.sleep(updateInTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
}
