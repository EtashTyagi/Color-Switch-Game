package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CrossObstacle extends Obstacle {
    @FXML transient private GridPane mainPane;
    @FXML transient private Group crossObstacle;
    @FXML transient private ArrayList<Rectangle> arms;
    @FXML transient private Rectangle firstOffset;
    private final static double thickness = 20;
    private static final double radius = 125;
    private final static double combined = 10;
    private double rotated = 0;
    private double rotateSpeed = 0.2;

    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            boolean left = Main.RANDOM.nextBoolean();
            if (left) {
                mainPane.setTranslateX(mainPane.getTranslateX() + Main.STAGE_WIDTH / 2 - radius/2 - getWidth() / 2);
                rotateSpeed *= -1;
            } else {
                mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            }
            for (int index = 0; index < arms.size(); index++) {
                arms.get(index).setFill(Main.GAME_COLORS[index]);
            }
            firstOffset.setFill(Main.GAME_COLORS[0]);
        });
    }
    public void setDifficulty(double difficulty) {
        if (difficulty <= 1 && difficulty >= 0) {
            double sign = Math.signum(rotateSpeed);
            if (sign > 0) {
                rotateSpeed = Math.max(0.2*0.5, difficulty*0.2);
            } else if (sign < 0) {
                rotateSpeed = Math.min(-0.2*0.5, -difficulty*0.2);
            }

        }
    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        double cX = mainPane.getTranslateX() + radius;
        double cY = mainPane.getTranslateY() + radius;
        for (Rectangle arm: arms) {
            if (CollisionDetector.ballAndRectangle(arm, ball, cX, cY,
                    cX - thickness / 2 + arm.getTranslateX() + 1,
                    cY - thickness / 2 + arm.getTranslateY() + 1, crossObstacle.getRotate())) {
                return true;
            }
        }
        return false;
    }
    @Override
    void doMovement() {
        Platform.runLater(() ->
        {
            rotated += rotateSpeed * Main.UPDATE_IN;
            crossObstacle.setRotate(rotated);
        });
    }
    @Override
    public double getHeight() {
        return 2*radius;
    }
    @Override
    public double getWidth() {
        return 2*radius;
    }
    @Override
    public void load(Obstacle obstacle) {
        assert obstacle instanceof CrossObstacle;
        CrossObstacle proper = (CrossObstacle) obstacle;
        rotated = proper.rotated;
        rotateSpeed = proper.rotateSpeed;
    }
    @Override
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - getWidth() / 2 + radius / 2;
    }
}
