package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class LinesObstacle extends Obstacle {
    @FXML transient private HBox mainPane;
    @FXML transient private ArrayList<Rectangle> parts;
    private static final double thickness = 20;
    final static private double length = 125.61;
    private double[] lengths;
    private int zeroThColorIndex;
    private double speed = 0.25;

    @FXML void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            lengths = new double[parts.size()];
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            for (int index = 0; index < parts.size() - 1; index++) {
                parts.get(index).setFill(Main.GAME_COLORS[index]);
                lengths[index] = length;
            }
            zeroThColorIndex = 0;
            parts.get(parts.size()-1).setFill(Main.GAME_COLORS[0]);
        });
    }
    public void setDifficulty(double difficulty) {
        if (difficulty <= 1 && difficulty >= 0) {
            boolean positive = Main.RANDOM.nextBoolean();
            if (positive) {
                speed = Math.max(0.25 * difficulty, 0.25*0.5);
            } else {
                speed = Math.min(-0.25 * difficulty, -0.25*0.5);
            }
        }
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
            if (speed > 0) {
                lengths[0] -= speed * Main.UPDATE_IN;
                lengths[lengths.length - 1] += speed * Main.UPDATE_IN;
                if (lengths[0] <= 0) {
                    lengths[lengths.length - 1] = 0;
                    lengths[0] = length;
                    zeroThColorIndex = (zeroThColorIndex + 1) % Main.GAME_COLORS.length;
                    for (int index = 0; index < parts.size(); index++) {
                        parts.get(index).setFill(Main.GAME_COLORS[(zeroThColorIndex + index) % Main.GAME_COLORS.length]);
                        parts.get(index).setWidth(lengths[index]);
                    }
                } else {
                    for (int index = 0; index < parts.size(); index++) {
                        parts.get(index).setWidth(lengths[index]);
                    }
                }
            } else {
                lengths[0] -= speed * Main.UPDATE_IN;
                lengths[lengths.length - 1] += speed * Main.UPDATE_IN;
                if (lengths[lengths.length - 1] <= 0) {
                    lengths[0] = 0;
                    lengths[lengths.length - 1] = length;
                    zeroThColorIndex = (((zeroThColorIndex - 1) % Main.GAME_COLORS.length) +
                            Main.GAME_COLORS.length) % Main.GAME_COLORS.length;
                    for (int index = 0; index < parts.size(); index++) {
                        parts.get(index).setFill(Main.GAME_COLORS[(zeroThColorIndex + index) % Main.GAME_COLORS.length]);
                        parts.get(index).setWidth(lengths[index]);
                    }
                } else {
                    for (int index = 0; index < parts.size(); index++) {
                        parts.get(index).setWidth(lengths[index]);
                    }
                }
            }
        });
    }
    @Override
    public double getHeight() {
        return thickness;
    }
    @Override
    public double getWidth() {
        return (length -0.61)*parts.size();
    }
    @Override
    public void load(Obstacle obstacle) {
        assert obstacle instanceof LinesObstacle;
        LinesObstacle proper = (LinesObstacle) obstacle;
        speed = proper.speed;
        zeroThColorIndex = proper.zeroThColorIndex;
        for (int index = 0; index < parts.size(); index++) {
            parts.get(index).setFill(Main.GAME_COLORS[(zeroThColorIndex+index) % Main.GAME_COLORS.length]);
            parts.get(index).setWidth(lengths[index]);
        }
    }
    @Override
    double xOffset() {
        return 0;
    }
}
