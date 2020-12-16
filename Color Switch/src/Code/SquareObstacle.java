package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class SquareObstacle extends Obstacle {
    @FXML transient private GridPane mainPane;
    @FXML transient private Group square;
    @FXML transient private ArrayList<Rectangle> sides;
    final static private double thickness = 20;
    private static final double sideSize = 150;
    private double rotate = 0;
    private double rotateSpeed = 0.175;

    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            for (int index = 0; index < sides.size(); index++) {
                sides.get(index).setFill(Main.GAME_COLORS[index]);
            }
        });
    }
    public void setDifficulty(double difficulty) {
        if (difficulty <= 1 && difficulty >= 0) {
            boolean positive = Main.RANDOM.nextBoolean();
            if (positive) {
                rotateSpeed = Math.max(0.175 * difficulty, 0.5*0.175);
            } else {
                rotateSpeed = Math.min(-0.175 * difficulty, -0.5*0.175);
            }
        }
    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        double cX = mainPane.getTranslateX() + (sideSize + thickness)/2;
        double cY = mainPane.getTranslateY() + (sideSize + thickness)/2;
        for (Rectangle side : sides) {
            if (CollisionDetector.ballAndRectangle(side, ball, cX, cY,
                    mainPane.getTranslateX() + side.getTranslateX() + sideSize,
                    mainPane.getTranslateY() + side.getTranslateY(), square.getRotate())) {
                return true;
            }
        }
        return false;
    }
    @Override
    void doMovement() {
        rotate += rotateSpeed * Main.UPDATE_IN;
        Platform.runLater(() -> square.setRotate(rotate));
    }
    @Override
    public double getHeight() {
        return sideSize + thickness;
    }
    @Override
    public double getWidth() {
        return getHeight();
    }
    @Override
    public void load(Obstacle obstacle) {
        assert obstacle instanceof SquareObstacle;
        SquareObstacle proper = (SquareObstacle) obstacle;
        rotateSpeed = proper.rotateSpeed;
        rotate = proper.rotate;
    }
    @Override
    double xOffset() {
        return Main.STAGE_WIDTH/2 - getWidth() / 2;
    }
}
