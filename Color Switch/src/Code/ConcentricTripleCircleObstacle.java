package Code;

import javafx.fxml.FXML;
import javafx.scene.Group;

public class ConcentricTripleCircleObstacle extends Obstacle {
    @FXML private Group outerCircle;
    @FXML private Group middleCircle;
    @FXML private Group innerCircle;
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
                try {
                    outerCircle.setRotate(outerCircle.getRotate()+rotateSpeed*updateInTime);
                    middleCircle.setRotate(middleCircle.getRotate()-rotateSpeed*updateInTime);
                    innerCircle.setRotate(innerCircle.getRotate()+rotateSpeed*updateInTime);
                    Thread.sleep(updateInTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
}
