package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CrossObstacle extends Obstacle {
    @FXML private GridPane mainPane;
    @FXML private Group crossObstacle;
    @FXML private ArrayList<Rectangle> arms;
    @FXML private Rectangle firstOffset;
    private double thickness = 20;
    private double radius = 125;
    private double combined = 10;
    private double rotateSpeed = 0.15;

    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            for (int index = 0; index < arms.size(); index++) {
                arms.get(index).setFill(Main.GAME_COLORS[index]);
            }
            firstOffset.setFill(Main.GAME_COLORS[0]);
        });
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

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
        Platform.runLater(() -> crossObstacle.setRotate(crossObstacle.getRotate() + rotateSpeed * Main.UPDATE_IN));
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
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - getWidth() / 2 + radius / 2;
    }
}
