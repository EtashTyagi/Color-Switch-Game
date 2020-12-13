package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class AdjacentDoubleCircleObstacle extends Obstacle {
    @FXML private HBox mainPane;
    @FXML private Group firstCircle;
    @FXML private Group secondCircle;
    @FXML private ArrayList<Arc> firstArcs;
    @FXML private ArrayList<Arc> secondArcs;
    private final double thickness = 10;
    private double radius1 = 83; //TODO: Set This Is Inner Radius
    private double radius2 = 63; //TODO: Set
    private double rotateSpeed = -0.1;

    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
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
    //TODO: Check For Collisions
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
            firstCircle.setRotate(firstCircle.getRotate()+rotateSpeed* Main.UPDATE_IN);
            secondCircle.setRotate(secondCircle.getRotate()-rotateSpeed* Main.UPDATE_IN);
        });
    }
    @Override
    public double getHeight() {
        return Math.max(2*(radius1 + thickness + 2), 2*(radius2 + thickness + 2));
    }
    @Override
    public double getWidth() {
        return 2*(radius1 + thickness + 2) + 2*(radius2 + thickness + 2);
    }
    @Override
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - 2*(radius1 + thickness + 2);
    }
}
