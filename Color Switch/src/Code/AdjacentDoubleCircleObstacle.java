package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class AdjacentDoubleCircleObstacle extends Obstacle{
    @FXML private Group firstCircle;
    @FXML private Group secondCircle;
    private double radius1; //TODO: Set
    private double radius2; //TODO: Set
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
                Platform.runLater(() ->
                {
                    firstCircle.setRotate(firstCircle.getRotate()+rotateSpeed*updateInTime);
                    secondCircle.setRotate(secondCircle.getRotate()-rotateSpeed*updateInTime);
                });
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
