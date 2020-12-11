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
    @FXML private GridPane mainPane;
    private ScheduledFuture<?> futureObjectMaker;
    private final Runnable gameObjectMaker = () -> {
        if (getLatestNodeAndController() == null || getLatestNodeAndController().getKey().getTranslateY() > 0) {
            if (getLatestNodeAndController() != null &&
                    getLatestNodeAndController().getValue() instanceof Obstacle) {
                spawnStar(gamePane);
            } else if (getLatestNodeAndController() != null) {
                if (!(getLatestNodeAndController().getValue() instanceof Switch) && Main.RANDOM.nextBoolean()) {
                    spawnSwitch(gamePane);
                } else {
                    spawnRandomObstacle(gamePane);
                }
            } else {
                spawnRandomObstacle(gamePane);
                Platform.runLater(() -> getLatestNodeAndController().getKey().setTranslateY(0));
            }
        }
    };

    @FXML void initialize() {
        scoreLabel.toFront();
        pauseButton.toFront();
        spawnBall(gamePane, mainPane);
        futureObjectMaker = Main.scheduleForExecution(gameObjectMaker, 0, 1);
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
        futureObjectMaker.cancel(false);
        getBall().stop();
    }
    @Override
    public void onStarCollected(Pair<Node, Star> nodeStarPair) {
        nodeStarPair.getValue().stopCollisionDetector();
        getGameObjectsNodeAndController().remove(nodeStarPair.getKey());
        gamePane.getChildren().remove(nodeStarPair.getKey());
        scoreLabel.setText(Integer.toString(Integer.parseInt(scoreLabel.getText()) + 1));
    }
    @Override
    public void onSwitchCollected(Pair<Node, Switch> nodeSwitchPair) {
        nodeSwitchPair.getValue().stopCollisionDetector();
        getBall().setColor(nodeSwitchPair.getValue().getNewColor());
        getGameObjectsNodeAndController().remove(nodeSwitchPair.getKey());
        gamePane.getChildren().remove(nodeSwitchPair.getKey());
    }
}
