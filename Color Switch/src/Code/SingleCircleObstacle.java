package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;

public class SingleCircleObstacle extends Obstacle{
    @FXML private Group circle;
    @FXML private Arc first;
    @FXML private Arc second;
    @FXML private Arc third;
    @FXML private Arc fourth;
    private double thickness = 15;
    private double radius = 83;
    private double rotateSpeed = 0.1;

    @FXML public void initialize() {
        Platform.runLater(() ->
        {
            first.setFill(Main.GAME_COLORS[0]);
            second.setFill(Main.GAME_COLORS[1]);
            third.setFill(Main.GAME_COLORS[2]);
            fourth.setFill(Main.GAME_COLORS[3]);
        });
        doMovement();
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

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
            while (true) {
                Platform.runLater(() -> circle.setRotate(circle.getRotate()+rotateSpeed*Main.UPDATE_IN));
                try {
                    Thread.sleep(Main.UPDATE_IN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
}
