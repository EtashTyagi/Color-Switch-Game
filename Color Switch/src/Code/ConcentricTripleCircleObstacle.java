package Code;

import javafx.scene.paint.Color;

//TODO: Design Sprite
public class ConcentricTripleCircleObstacle extends Obstacle{
    private double radius;
    private double rotateSpeed;

    //TODO: Triangle Obstacle Constructor [assign radius and speed based on difficulty (both 0-1)]
    ConcentricTripleCircleObstacle(double difficulty, Color[] colors, Color passingColor) {
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
