package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class TriangleObstacle extends Obstacle {
    @FXML private GridPane mainPane;
    @FXML private Group triangle;
    @FXML private ArrayList<Rectangle> sides;
    private double thickness = 15;
    private double sideSize = 200;
    private double rotateSpeed = 0.05;
    private double diam = 231;

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
        });
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

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
        Platform.runLater(() -> triangle.setRotate(triangle.getRotate() + rotateSpeed * Main.UPDATE_IN));
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
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - getWidth() / 2;
    }
}
