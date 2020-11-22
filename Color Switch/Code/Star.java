package Code;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Star implements Collidable {
    @FXML private Rectangle starRect;
    @FXML private final ImagePattern starImg = new ImagePattern(new Image("file:Resources\\Images\\Star.png"));
    @FXML private void initialize() {
        starRect.setFill(starImg);
    }
    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return false;
    }
}
