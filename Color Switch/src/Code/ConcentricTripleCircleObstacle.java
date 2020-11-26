package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Arc;

public class ConcentricTripleCircleObstacle extends Obstacle {
    @FXML private Group outerCircle;
    @FXML private Group middleCircle;
    @FXML private Group innerCircle;
    @FXML private Arc outerCircleFirst;
    @FXML private Arc outerCircleSecond;
    @FXML private Arc outerCircleThird;
    @FXML private Arc outerCircleFourth;
    @FXML private Arc middleCircleFirst;
    @FXML private Arc middleCircleSecond;
    @FXML private Arc middleCircleThird;
    @FXML private Arc middleCircleFourth;
    @FXML private Arc innerCircleFirst;
    @FXML private Arc innerCircleSecond;
    @FXML private Arc innerCircleThird;
    @FXML private Arc innerCircleFourth;
    private double thickness = 17;
    private double radius = 82;
    private double rotateSpeed = 0.1;

    @FXML private void initialize() {
        Platform.runLater(() ->
        {
            outerCircleFirst.setFill(Main.GAME_COLORS[0]);
            outerCircleSecond.setFill(Main.GAME_COLORS[1]);
            outerCircleThird.setFill(Main.GAME_COLORS[2]);
            outerCircleFourth.setFill(Main.GAME_COLORS[3]);
            middleCircleFirst.setFill(Main.GAME_COLORS[0]);
            middleCircleSecond.setFill(Main.GAME_COLORS[1]);
            middleCircleThird.setFill(Main.GAME_COLORS[2]);
            middleCircleFourth.setFill(Main.GAME_COLORS[3]);
            innerCircleFirst.setFill(Main.GAME_COLORS[0]);
            innerCircleSecond.setFill(Main.GAME_COLORS[1]);
            innerCircleThird.setFill(Main.GAME_COLORS[2]);
            innerCircleFourth.setFill(Main.GAME_COLORS[3]);
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
                    outerCircle.setRotate(outerCircle.getRotate()+rotateSpeed*Main.UPDATE_IN);
                    middleCircle.setRotate(middleCircle.getRotate()-rotateSpeed*Main.UPDATE_IN);
                    innerCircle.setRotate(innerCircle.getRotate()+rotateSpeed*Main.UPDATE_IN);
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
