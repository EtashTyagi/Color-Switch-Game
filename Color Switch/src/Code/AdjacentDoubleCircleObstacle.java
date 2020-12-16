package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class AdjacentDoubleCircleObstacle extends Obstacle {
    @FXML transient private HBox mainPane;
    @FXML transient private Group firstCircle;
    @FXML transient private Group secondCircle;
    @FXML transient private ArrayList<Arc> firstArcs;
    @FXML transient private ArrayList<Arc> secondArcs;
    private static final double thickness = 10;
    private static final double radius1 = 83;
    private static final double radius2 = 63;
    private double rotated = 0;
    private double rotateSpeed = 0.035;

    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            for (int index = 0; index < 4; index++) {
                firstArcs.get(index).setFill(Main.GAME_COLORS[index]);
            }
            secondArcs.get(0).setFill(Main.GAME_COLORS[1]);
            secondArcs.get(1).setFill(Main.GAME_COLORS[0]);
            secondArcs.get(2).setFill(Main.GAME_COLORS[3]);
            secondArcs.get(3).setFill(Main.GAME_COLORS[2]);
        });
    }
    public void setDifficulty(double difficulty) {
        if (difficulty <= 1 && difficulty >= 0) {
            if (difficulty > 0.75) {
                rotateSpeed = (difficulty) * 0.035;
            } else {
                rotateSpeed = Math.min(-0.15*0.5, -(difficulty+0.25)*0.15);
            }
        }
    }
    public void setRotateSpeed(double rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }
    public double getRotateSpeed() {
        return rotateSpeed;
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
            rotated += rotateSpeed * Main.UPDATE_IN;
            firstCircle.setRotate(rotated);
            secondCircle.setRotate(-rotated);
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
    public void load(Obstacle obstacle) {
        assert obstacle instanceof AdjacentDoubleCircleObstacle;
        AdjacentDoubleCircleObstacle proper = (AdjacentDoubleCircleObstacle) obstacle;
        rotateSpeed = proper.rotateSpeed;
        rotated = proper.rotated;
    }
    @Override
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - 2*(radius1 + thickness + 2);
    }
}
