package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class SquareObstacle extends Obstacle {
    @FXML private GridPane mainPane;
    @FXML private Group square;
    @FXML private ArrayList<Rectangle> sides;
    private double thickness = 20;
    private double sideSize = 150;
    private double rotateSpeed = 0.05;

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
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

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
        Platform.runLater(() -> square.setRotate(square.getRotate() + rotateSpeed * Main.UPDATE_IN));
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
    double xOffset() {
        return Main.STAGE_WIDTH/2 - getWidth() / 2;
    }
}
