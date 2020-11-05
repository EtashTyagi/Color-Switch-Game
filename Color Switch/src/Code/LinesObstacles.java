package Code;

import javafx.scene.paint.Color;

public class LinesObstacles extends Obstacle{
    private double speed;

    //TODO: Triangle Obstacle Constructor [assign speed based on difficulty (both 0-1)]
    LinesObstacles(double difficulty, Color[] colors, Color passingColor) {
        super(difficulty, colors, passingColor);
    }
    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return false;
    }
    //TODO: Implement translation
    @Override
    void doMovement() {
    }
}
