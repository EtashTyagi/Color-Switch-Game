package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Arc;

public class AdjacentDoubleCircleObstacle extends Obstacle{
    @FXML private Group firstCircle;
    @FXML private Group secondCircle;
    @FXML private Arc firstCircleFirstPart;
    @FXML private Arc firstCircleSecondPart;
    @FXML private Arc firstCircleThirdPart;
    @FXML private Arc firstCircleFourthPart;
    @FXML private Arc secondCircleFirstPart;
    @FXML private Arc secondCircleSecondPart;
    @FXML private Arc secondCircleThirdPart;
    @FXML private Arc secondCircleFourthPart;
    private final double thickness = 10;
    private double radius1 = 83; //TODO: Set This Is Inner Radius
    private double radius2 = 63; //TODO: Set
    private double rotateSpeed = 0.1;

    @FXML private void initialize() {
        Platform.runLater(() ->
        {
            firstCircleFirstPart.setFill(Main.GAME_COLORS[0]);
            firstCircleSecondPart.setFill(Main.GAME_COLORS[1]);
            firstCircleThirdPart.setFill(Main.GAME_COLORS[2]);
            firstCircleFourthPart.setFill(Main.GAME_COLORS[3]);
            secondCircleFirstPart.setFill(Main.GAME_COLORS[0]);
            secondCircleSecondPart.setFill(Main.GAME_COLORS[1]);
            secondCircleThirdPart.setFill(Main.GAME_COLORS[2]);
            secondCircleFourthPart.setFill(Main.GAME_COLORS[3]);
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
                Platform.runLater(() ->
                {
                    firstCircle.setRotate(firstCircle.getRotate()+rotateSpeed*Main.UPDATE_IN);
                    secondCircle.setRotate(secondCircle.getRotate()-rotateSpeed*Main.UPDATE_IN);
                });
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
