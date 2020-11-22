package Code;

import javafx.fxml.FXML;

public class Switch implements Collidable {
    @FXML private void initialize() {
    }
    //TODO: Collision Detection
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return false;
    }
}
