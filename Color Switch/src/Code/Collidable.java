package Code;

import java.io.Serializable;

public interface Collidable extends Serializable {
    boolean hasCollidedWithBall(Ball ball);
    void startCollisionDetector(Ball ball, Runnable taskOnCollision);
    void stopCollisionDetector();
    void load(Collidable collidable);
}
