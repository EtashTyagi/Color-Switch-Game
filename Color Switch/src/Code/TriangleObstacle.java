package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class TriangleObstacle extends Obstacle {
    @FXML transient private GridPane mainPane;
    @FXML transient private Group triangle;
    @FXML transient private ArrayList<Rectangle> sides;
    final static private double thickness = 15;
    private static final double sideSize = 200;
    private static final double diam = 231;
    private int unPassable = 3;
    private double rotated = 0;
    private double rotateSpeed = 0.175;


    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            for (int index = 0; index < sides.size(); index++) {
                sides.get(index).setFill(Main.GAME_COLORS[index]);
            }
            doMovement();
        });
    }
    public void setPassableColor(Color passableColor) {
        Platform.runLater(() ->
        {
            for (Rectangle side : sides) {
                if (side.getFill().equals(passableColor)) {
                    return;
                }
            }
            sides.get(0).setFill(passableColor);
            unPassable = 0;
        });
    }
    public void setDifficulty(double difficulty) {
        if (difficulty <= 1 && difficulty >= 0) {
            boolean positive = Main.RANDOM.nextBoolean();
            if (positive) {
                rotateSpeed = Math.max(0.175 * difficulty, 0.175*0.5);
            } else {
                rotateSpeed = Math.min(-0.175 * difficulty, -0.175*0.5);
            }
        }
    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        double cX = mainPane.getTranslateX() + (sideSize)/2;
        double cY = mainPane.getTranslateY() + diam/2;
        for (int index = 0; index < sides.size(); index++) {
            if (CollisionDetector.ballAndRectangle(sides.get(index), ball, cX, cY,
                    mainPane.getTranslateX(),
                    mainPane.getTranslateY() + 50, triangle.getRotate() + index * 120)) {
                return true;
            };
        }
        return false;
    }
    @Override
    void doMovement() {
        Platform.runLater(() ->
        {
            rotated += rotateSpeed * Main.UPDATE_IN;
            triangle.setRotate(rotated);
        });
    }
    @Override
    public double getHeight() {
        return diam;
    }
    @Override
    public double getWidth() {
        return sideSize;
    }
    @Override
    public void load(Obstacle obstacle) {
        assert obstacle instanceof TriangleObstacle;
        TriangleObstacle proper = (TriangleObstacle) obstacle;
        rotateSpeed = proper.rotateSpeed;
        rotated = proper.rotated;
        unPassable = proper.unPassable;
        if (unPassable == 0) {
            sides.get(0).setFill(Main.GAME_COLORS[3]);
        }
    }
    @Override
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - getWidth() / 2;
    }
}
