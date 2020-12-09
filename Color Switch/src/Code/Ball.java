package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;
import java.util.concurrent.ScheduledFuture;

public class Ball {
    @FXML private Circle ball;
    private final double radius = 10;
    private static final double upVel = 0.3;
    private double velocity;
    private ScheduledFuture<?> mover;

    public void initialize() {
        Platform.runLater(() ->
        {
            ball.setFill(Main.GAME_COLORS[Main.RANDOM.nextInt(Main.GAME_COLORS.length)]);
            ball.setTranslateX(ball.getTranslateX() + xOffset());
        });
        initializeMover();
    }
    public double getCenterY() {
        return ball.getTranslateY() + radius;
    }
    public double getCenterX() {
        return ball.getTranslateX() + radius;
    }
    public double getRadius() {
        return radius;
    }
    public void setColor(Color color) {
        boolean validColor = false;
        for (int index = 0; index < Main.GAME_COLORS.length ; index++) {
            validColor = validColor || (Main.GAME_COLORS[index] == color);
        }
        if (validColor) {
            ball.setFill(color);
        } else {
            // Throw Error Here
        }
    }
    public Color getColor() {
        return (Color) ball.getFill();
    }
    public void goUp() {
        velocity = upVel;
    }
    private void initializeMover() {
        mover = Main.scheduleForExecution(() -> {
            Platform.runLater(() ->
            {
                if (ball.getTranslateY() <= 550) {
                    ball.setTranslateY(ball.getTranslateY() - velocity * Main.UPDATE_IN);
                    velocity = Math.max(velocity - 0.001 * Main.UPDATE_IN, -0.5);
                } else {
                    ball.setTranslateY(545);
                    velocity = 0;
                }
            });
        }, 0, 1);
    }
    private double xOffset() {
        return Main.STAGE_WIDTH / 2 - radius / 2;
    }
    public void stop() {
        mover.cancel(false);
    }
}