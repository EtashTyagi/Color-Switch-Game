package Code;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Queue;

//TODO: Design GUI
public abstract class Game extends Pane {
    private final Color[] POSSIBLE_COLOURS = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private final Color BACKGROUND_COLOUR = Color.BLACK;
    private int curScore;
    private Ball ball;
    private int prevHighScore;
    private Queue<Collidable> gameObjectQueue;
    private Button pauseButton;

    //TODO: Game Constructor [BONUS: add color options]
    public Game(int previousHighScore) {
        this.prevHighScore = previousHighScore;
    }
    public abstract void updateGameState();
    public abstract void saveGameData();
    public int getCurScore() {
        return curScore;
    }
    //TODO: Change To Random
    public Color getRandomColor() {
        return POSSIBLE_COLOURS[0];
    }
    public void increaseScore(int by) {
        curScore += by;
    }
    public void changeBallColor(Color to) {
        ball.setColor(to);
    }
    //TODO: Spawn Obstacle Function
    public void spawnObstacle(double[] coordinates) {
    }
    //TODO: Spawn Switch Function [Switches Color of ball]
    public void spawnSwitch(double[] coordinates) {
    }
    //TODO: Spawn Star Function
    public void spawnStar(double[] coordinates) {
    }
    //TODO: On Pause Press Function
    public void onPausePress() {
    }
}
