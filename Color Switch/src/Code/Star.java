package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.ScheduledFuture;

public class Star implements Collidable {
    @FXML private Rectangle starRect;
    @FXML private GridPane mainPane;
    @FXML private final ImagePattern starImg = new ImagePattern(new Image("file:Resources\\Images\\Star.png"));
    private ScheduledFuture<?> collisionDetector;

    @FXML private void initialize() {
        Platform.runLater(() -> {
            starRect.setFill(starImg);
            mainPane.setTranslateX(starRect.getTranslateX() + xOffset());
        });
    }
    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return CollisionDetector.ballAndRectangle(starRect, ball, mainPane.getTranslateX() + starRect.getWidth()/2,
                mainPane.getTranslateY() + starRect.getHeight()/2, mainPane.getTranslateX(), mainPane.getTranslateY(), 0);
    }
    @Override
    public void startCollisionDetector(Ball ball, Runnable taskOnCollision) {
        collisionDetector = Main.scheduleForExecution(() ->
                {
                    if (hasCollidedWithBall(ball)) {
                        Platform.runLater(taskOnCollision);
                    }
                }
                , 0, 1);
    }
    @Override
    public void stopCollisionDetector() {
        try {
            collisionDetector.cancel(false);
        } catch (Exception ignore) {

        }
    }
    public double getHeight() {
        return starRect.getHeight();
    }
    public double getWidth() {return starRect.getWidth();}
    double xOffset() {
        return Main.STAGE_WIDTH/2 - getWidth()/2;
    }
}
