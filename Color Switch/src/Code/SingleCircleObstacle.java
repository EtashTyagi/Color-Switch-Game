package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class SingleCircleObstacle extends Obstacle {
    @FXML transient private GridPane mainPane;
    @FXML transient private Group circle;
    @FXML transient private ArrayList<Arc> arcs;
    final static private double thickness = 15;
    private static final double radius = 83;
    private double rotate = 0;
    private double rotateSpeed = 0.175;

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
    public void setDifficulty(double difficulty) {
        if (difficulty <= 1 && difficulty >= 0) {
            boolean positive = Main.RANDOM.nextBoolean();
            if (positive) {
                rotateSpeed = Math.max(0.175 * difficulty, 0.175*0.5);
            } else {
                rotateSpeed = Math.min(-0.175 * difficulty, -0.175*0.5);
            }
        }
    }
    @Override
    public boolean hasCollidedWithBall(Ball ball) {
        return CollisionDetector.ballAndArcedCircle(arcs, ball, thickness, mainPane.getTranslateX() + radius + thickness,
                mainPane.getTranslateY() + radius + thickness, circle.getRotate());
    }
    @Override
    void doMovement() {
        rotate += rotateSpeed* Main.UPDATE_IN;
        Platform.runLater(() -> circle.setRotate(rotate));
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
    public void load(Obstacle obstacle) {
        assert obstacle instanceof SingleCircleObstacle;
        SingleCircleObstacle proper = (SingleCircleObstacle) obstacle;
        rotateSpeed = proper.rotateSpeed;
        rotate = proper.rotate;
    }
    @Override
    double xOffset() {
        return Main.STAGE_WIDTH / 2 - getWidth() / 2;
    }
}
