package Code;

import javafx.scene.paint.Color;

//TODO: Design Sprite
public class TriangleObstacle extends Obstacle{
    private double sideSize;
    private double rotateSpeed;

    //TODO: Triangle Obstacle Constructor [assign side and speed based on difficulty (both 0-1)]
    TriangleObstacle(double difficulty, Color[] colors, Color passingColor) {
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
