package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class ConcentricTripleCircleObstacle extends Obstacle {
    @FXML private GridPane mainPane;
    @FXML private Group outerCircle;
    @FXML private Group middleCircle;
    @FXML private Group innerCircle;
    @FXML private ArrayList<Arc> outerArcs;
    @FXML private ArrayList<Arc> middleArcs;
    @FXML private ArrayList<Arc> innerArcs;
    private double thickness = 17;
    private double radius = 82;
    private double rotateSpeed = 0.05;

    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            for (int index = 0; index < outerArcs.size(); index++) {
                outerArcs.get(index).setFill(Main.GAME_COLORS[index]);
                innerArcs.get(index).setFill(Main.GAME_COLORS[index]);
            }
            middleArcs.get(0).setFill(Main.GAME_COLORS[1]);
            middleArcs.get(1).setFill(Main.GAME_COLORS[0]);
            middleArcs.get(2).setFill(Main.GAME_COLORS[3]);
            middleArcs.get(3).setFill(Main.GAME_COLORS[2]);
        });
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

    }
    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        boolean outer = CollisionDetector.ballAndArcedCircle(outerArcs, ball, thickness,
                mainPane.getTranslateX() + outerArcs.get(0).getRadiusX() + 1.5,
                mainPane.getTranslateY() + outerArcs.get(0).getRadiusY() + 1.5,
                outerCircle.getRotate());
        boolean middle = CollisionDetector.ballAndArcedCircle(middleArcs, ball, thickness,
                mainPane.getTranslateX() + outerArcs.get(0).getRadiusX() + 1.5,
                mainPane.getTranslateY() + outerArcs.get(0).getRadiusY() + 1.5,
                middleCircle.getRotate());
        boolean inner = CollisionDetector.ballAndArcedCircle(innerArcs, ball, thickness,
                mainPane.getTranslateX() + outerArcs.get(0).getRadiusX() + 1.5,
                mainPane.getTranslateY() + outerArcs.get(0).getRadiusY() + 1.5,
                innerCircle.getRotate());
        return outer || inner || middle;
    }
    @Override
    void doMovement() {
        Platform.runLater(() ->
        {
            outerCircle.setRotate(outerCircle.getRotate()+rotateSpeed* Main.UPDATE_IN);
            middleCircle.setRotate(middleCircle.getRotate()-rotateSpeed* Main.UPDATE_IN);
            innerCircle.setRotate(innerCircle.getRotate()+rotateSpeed* Main.UPDATE_IN);
        });
    }
    @Override
    public double getHeight() {
        return 2*(radius + 3 * thickness + 1.5 * 2);
    }
    @Override
    public double getWidth() {
        return getHeight();
    }
    @Override
    double xOffset() {
        return  Main.STAGE_WIDTH / 2 - getWidth() / 2;
    }
}
