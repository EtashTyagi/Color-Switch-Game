package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareObstacle extends Obstacle{
    @FXML private Group square;
    @FXML private Rectangle first;
    @FXML private Rectangle second;
    @FXML private Rectangle third;
    @FXML private Rectangle fourth;
    @FXML private Rectangle firstShort;
    @FXML private Rectangle secondShort;
    @FXML private Rectangle thirdShort;
    @FXML private Rectangle fourthShort;
    private double thickness = 20;
    private double sideSize = 125;
    private double rotateSpeed = 0.1;

    @FXML private void initialize() {
        Platform.runLater(() ->
        {
            first.setFill(Main.GAME_COLORS[0]);
            second.setFill(Main.GAME_COLORS[1]);
            third.setFill(Main.GAME_COLORS[2]);
            fourth.setFill(Main.GAME_COLORS[3]);
            firstShort.setFill(Main.GAME_COLORS[0]);
            secondShort.setFill(Main.GAME_COLORS[1]);
            thirdShort.setFill(Main.GAME_COLORS[2]);
            fourthShort.setFill(Main.GAME_COLORS[3]);
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
                Platform.runLater(() -> square.setRotate(square.getRotate()+rotateSpeed*Main.UPDATE_IN));
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
