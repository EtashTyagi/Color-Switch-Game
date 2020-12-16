package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class ConcentricTripleCircleObstacle extends Obstacle {
    @FXML transient private GridPane mainPane;
    @FXML transient private Group outerCircle;
    @FXML transient private Group middleCircle;
    @FXML transient private Group innerCircle;
    @FXML transient private ArrayList<Arc> outerArcs;
    @FXML transient private ArrayList<Arc> middleArcs;
    @FXML transient private ArrayList<Arc> innerArcs;
    private static final double thickness = 17;
    private static final double radius = 82;
    private double rotated = 0;
    private double rotateSpeed = 0.125;

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
    public void setDifficulty(double difficulty) {
        if (difficulty <= 1 && difficulty >= 0) {
            boolean positive = Main.RANDOM.nextBoolean();
            if (positive) {
                rotateSpeed = Math.max(0.125 * difficulty, 0.125*0.5);
            } else {
                rotateSpeed = Math.min(-0.125 * difficulty, -0.125*0.5);
            }
        }
    }
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
            rotated += rotateSpeed * Main.UPDATE_IN;
            outerCircle.setRotate(rotated);
            middleCircle.setRotate(-rotated);
            innerCircle.setRotate(rotated);
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
    public void load(Obstacle obstacle) {
        assert obstacle instanceof ConcentricTripleCircleObstacle;
        ConcentricTripleCircleObstacle proper = (ConcentricTripleCircleObstacle) obstacle;
        rotateSpeed = proper.rotateSpeed;
        rotated = proper.rotated;
    }
    @Override
    double xOffset() {
        return  Main.STAGE_WIDTH / 2 - getWidth() / 2;
    }
}
