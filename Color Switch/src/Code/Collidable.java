package Code;

public interface Collidable {
    boolean hasCollidedWithBall(Ball ball);
    void startCollisionDetector(Ball ball, Runnable taskOnCollision);
    void stopCollisionDetector();
}
