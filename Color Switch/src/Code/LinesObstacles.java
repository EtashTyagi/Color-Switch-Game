package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class LinesObstacles extends Obstacle{
    @FXML private Rectangle first;
    @FXML private Rectangle second;
    @FXML private Rectangle third;
    @FXML private Rectangle fourth;
    @FXML private Rectangle fifth;
    private double thickness = 20;
    private final double defaultLength = 125;
    private double speed = 2.5;

    @FXML private void initialize() {
        Platform.runLater(() ->
        {
            first.setFill(Main.GAME_COLORS[0]);
            second.setFill(Main.GAME_COLORS[1]);
            third.setFill(Main.GAME_COLORS[2]);
            fourth.setFill(Main.GAME_COLORS[3]);
            fifth.setFill(Main.GAME_COLORS[0]);
        });
        doMovement();
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

    }
    //TODO: Check For Collision
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return false;
    }
    @Override
    void doMovement() {
        Thread animationThread = new Thread(() ->
        {
            while (true) {
                Platform.runLater(() ->
                {
                    if (first.getWidth() <= 0) {
                        fifth.setWidth(0);
                        first.setFill(second.getFill());
                        first.setWidth(defaultLength);
                        Paint fifthColor = fifth.getFill();
                        fifth.setFill(first.getFill());
                        first.setFill(second.getFill());
                        second.setFill(third.getFill());
                        third.setFill(fourth.getFill());
                        fourth.setFill(fifthColor);
                    }

                    first.setWidth(first.getWidth() - speed);
                    fifth.setWidth(fifth.getWidth() + speed);
                });
                try {
                    Thread.sleep(Main.UPDATE_IN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
}
