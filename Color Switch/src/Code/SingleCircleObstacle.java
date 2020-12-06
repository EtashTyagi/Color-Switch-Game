package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class SingleCircleObstacle extends Obstacle {
    @FXML private Group circle;
    @FXML private ArrayList<Arc> arcs;
    @FXML private GridPane mainPane;
    private double thickness = 15;
    private double radius = 83; //INNER
    private double rotateSpeed = 0.05;

    @FXML public void initialize() {
        Platform.runLater(() ->
        {
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
        Platform.runLater(() -> circle.setRotate(circle.getRotate()+rotateSpeed*Main.UPDATE_IN));
    }
}
