package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class CrossObstacle extends Obstacle{
    @FXML private Node crossObstacle;
    @FXML private Rectangle firstRectangle;
    @FXML private Rectangle secondRectangle;
    @FXML private Rectangle thirdRectangle;
    @FXML private Rectangle fourthRectangle;
    private double thickness = 20;
    private double radius = 125;
    private double rotateSpeed = 0.1;

    @FXML private void initialize() {
        Platform.runLater(() ->
        {
            firstRectangle.setFill(Main.GAME_COLORS[0]);
            secondRectangle.setFill(Main.GAME_COLORS[1]);
            thirdRectangle.setFill(Main.GAME_COLORS[2]);
            fourthRectangle.setFill(Main.GAME_COLORS[3]);
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
                Platform.runLater(() -> crossObstacle.setRotate(crossObstacle.getRotate()+rotateSpeed*Main.UPDATE_IN));
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
