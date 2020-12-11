package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class SingleCircleObstacle extends Obstacle {
    @FXML private GridPane mainPane;
    @FXML private Group circle;
    @FXML private ArrayList<Arc> arcs;
    private double thickness = 15;
    private double radius = 83; //INNER
    private double rotateSpeed = 0.1;

    @FXML public void initialize() {
        super.initialize();
        Platform.runLater(() ->
        {
            mainPane.setMaxHeight(getHeight()); mainPane.setMaxWidth(getWidth());
            mainPane.setTranslateX(mainPane.getTranslateX() + xOffset());
            for (int index = 0; index < arcs.size(); index++) {
                arcs.get(index).setFill(Main.GAME_COLORS[index]);
            }
        });
    }
    //TODO: assign speed and difficulty based on this
    public void setDifficulty(double difficulty) {

    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return CollisionDetector.ballAndArcedCircle(arcs, ball, thickness, mainPane.getTranslateX() + radius + thickness,
                mainPane.getTranslateY() + radius + thickness, circle.getRotate());
    }
    @Override
    void doMovement() {
        Platform.runLater(() -> circle.setRotate(circle.getRotate()+rotateSpeed* Main.UPDATE_IN));
    }
    @Override
    public double getHeight() {
        return 2*(radius + thickness + 2);
    }
    @Override
    public double getWidth() {
        return getHeight();
    }
    @Override
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - getWidth() / 2;
    }
}
