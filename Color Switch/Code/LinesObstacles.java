package Code;

import javafx.scene.paint.Color;

public class LinesObstacles extends Obstacle{
    private double speed;


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
