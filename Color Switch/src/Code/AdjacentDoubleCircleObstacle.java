package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class AdjacentDoubleCircleObstacle extends Obstacle{
    @FXML private Group firstCircle;
    @FXML private Group secondCircle;
    @FXML private ArrayList<Arc> firstArcs;
    @FXML private ArrayList<Arc> secondArcs;
    @FXML private HBox mainPane;
    private double firstCircleRotOffset;
    private final double thickness = 10;
    private double radius1 = 83; //TODO: Set This Is Inner Radius
    private double radius2 = 63; //TODO: Set
    private double rotateSpeed = 0.05;

    @FXML private void initialize() {
        firstCircleRotOffset = firstCircle.getRotate();
        Platform.runLater(() ->
        {
            for (int index = 0; index < 4; index++) {
                firstArcs.get(index).setFill(Main.GAME_COLORS[index]);
            }
            // SECOND ARCS FLIPPED OVER VERTICAL AXIS
            secondArcs.get(0).setFill(Main.GAME_COLORS[1]);
            secondArcs.get(1).setFill(Main.GAME_COLORS[0]);
            secondArcs.get(2).setFill(Main.GAME_COLORS[3]);
            secondArcs.get(3).setFill(Main.GAME_COLORS[2]);
        });
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {
    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return CollisionDetector.ballAndArcedCircle(firstArcs, ball, thickness,
                mainPane.getTranslateX() + radius1 + thickness, mainPane.getTranslateY() + radius1 + thickness,
                firstCircle.getRotate()) || CollisionDetector.ballAndArcedCircle(secondArcs, ball, thickness,
                mainPane.getTranslateX() + 2*(radius1 + thickness + 2) + radius2 + thickness,
                mainPane.getTranslateY() + radius1 + thickness, secondCircle.getRotate());
    }
    @Override
    void doMovement() {
        Platform.runLater(() ->
        {
            firstCircle.setRotate(firstCircle.getRotate()+rotateSpeed*Main.UPDATE_IN);
            secondCircle.setRotate(secondCircle.getRotate()-rotateSpeed*Main.UPDATE_IN);
        });
    }
}
