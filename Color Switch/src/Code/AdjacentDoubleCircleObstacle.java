package Code;

import javafx.scene.paint.Color;

public class AdjacentDoubleCircleObstacle extends Obstacle{
    private double radius1;
    private double radius2;
    private double rotateSpeed;

    //TODO: Triangle Obstacle Constructor [assign radius1 & radius2 and speed(same for both) based on difficulty (both 0-1)]
    AdjacentDoubleCircleObstacle(double difficulty, Color[] colors, Color passingColor) {
        super(difficulty, colors, passingColor);
    }
    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return false;
    }
    //TODO: Implement Rotation
    @Override
    void doMovement() {
    }
}
