package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class LinesObstacle extends Obstacle {
    @FXML private HBox mainPane;
    @FXML private ArrayList<Rectangle> parts;
    private double thickness = 20;
    private final double defaultLength = 125.61;
    private double speed = 0.05;

    @FXML void initialize() {
        super.initialize();
        mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
        Platform.runLater(() ->
        {
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            for (int index = 0; index < parts.size() - 1; index++) {
                parts.get(index).setFill(Main.GAME_COLORS[index]);
            }
            parts.get(parts.size()-1).setFill(Main.GAME_COLORS[0]);
        });
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        double xOffset = 0;
        for (Rectangle part : parts) {
            if (CollisionDetector.ballAndRectangle(part, ball, 0, 0,
                    xOffset + mainPane.getTranslateX(), mainPane.getTranslateY(), 0)) {
                return true;
            }
            xOffset += part.getWidth();
        }
        return false;
    }
    @Override
    void doMovement() {
        Platform.runLater(() ->
        {
            parts.get(0).setWidth(parts.get(0).getWidth() - speed* Main.UPDATE_IN);
            parts.get(parts.size() - 1).setWidth(
                    parts.get(parts.size() - 1).getWidth() + speed* Main.UPDATE_IN);
            if (parts.get(0).getWidth() <= 0) {
                parts.get(parts.size() - 1).setWidth(0);
                parts.get(0).setFill(parts.get(1).getFill());
                parts.get(0).setWidth(defaultLength);
                Paint lastColor = parts.get(parts.size() - 1).getFill();
                parts.get(parts.size() - 1).setFill(parts.get(0).getFill());
                for (int index = 0; index < parts.size() - 2; index++) {
                    parts.get(index).setFill(parts.get(index + 1).getFill());
                }
                parts.get(parts.size() - 2).setFill(lastColor);
            }
        });
    }
    @Override
    public double getHeight() {
        return thickness;
    }
    @Override
    public double getWidth() {
        return (defaultLength-0.61)*parts.size();
    }
    @Override
    double xOffset() {
        return 0;
    }
}
