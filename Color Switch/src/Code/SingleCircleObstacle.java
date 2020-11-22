package Code;

import javafx.fxml.FXML;
import javafx.scene.Group;

public class SingleCircleObstacle extends Obstacle{
    @FXML private Group circle;
    private double radius;
    private double rotateSpeed = 0.1;

    @FXML public void initialize() {
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
                try {
                    circle.setRotate(circle.getRotate()+rotateSpeed*updateInTime);
                    Thread.sleep(updateInTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
}
