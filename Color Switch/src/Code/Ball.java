package Code;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {
    @FXML private Circle ball;
    private double radius = 10;
    private double velocity;
    public void initialize() {
        Random random = new Random();
        ball.setFill(Main.GAME_COLORS[random.nextInt(Main.GAME_COLORS.length)]);
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
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    //TODO: Update Coordinate Function
    public void updateCoordinates() {
    }
}