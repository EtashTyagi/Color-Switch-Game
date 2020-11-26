package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class TriangleObstacle extends Obstacle{
    @FXML private Group triangle;
    @FXML private Rectangle first;
    @FXML private Rectangle second;
    @FXML private Rectangle third;
    private double thickness = 20;
    private double sideSize = 200;
    private double rotateSpeed = 0.1;

    @FXML private void initialize() {
        Platform.runLater(() ->
        {
            first.setFill(Main.GAME_COLORS[0]);
            second.setFill(Main.GAME_COLORS[1]);
            third.setFill(Main.GAME_COLORS[2]);
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
    //TODO: Implement Rotation
    @Override
    void doMovement() {
        Thread animationThread = new Thread(() ->
        {
            while (true) {
                Platform.runLater(() -> triangle.setRotate(triangle.getRotate()+rotateSpeed*Main.UPDATE_IN));
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
