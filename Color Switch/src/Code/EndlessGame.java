package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.util.concurrent.ScheduledFuture;

public class EndlessGame extends Game {
    //TODO: Endless Game Constructor
    @FXML private Circle pauseButton;
    @FXML private Label scoreLabel;
    @FXML private GridPane gamePane;
    private ScheduledFuture<?> futureSpawn;
    private Runnable obstacleMaker = () -> {
        Platform.runLater(() -> {
            if (getLatestInsertion() == null || getLatestInsertion().getTranslateY() > 100) {
                spawnRandomObstacle(gamePane);
            }
            if (getGameObjects().size() > 3) {
                Pair<Node, Collidable> object = getGameObjects().poll();
                assert object != null;
                if (object.getValue() instanceof Obstacle) {
                    ((Obstacle) object.getValue()).stopMovement();
                }
                getCollisionDetector().poll();
                gamePane.getChildren().remove(object.getKey());
            }
        });
    };

    @FXML void initialize() {
        scoreLabel.toFront();
        pauseButton.toFront();
        spawnBall(gamePane);
        futureSpawn = Main.scheduleForExecution(obstacleMaker, 0, 10);
    }
    //TODO: Update Game State Function
    @Override
    public void updateGameState() {
    }
    //TODO: Save Game State Function
    @Override
    public void saveGameData() {
    }
    @Override
    public void onCollisionDetected() {
        // Go Back TO Main Menu
    }
}
