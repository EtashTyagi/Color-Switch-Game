package Code;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {
    @FXML public Circle ball;
    private double velocity;
    private double xCoord;
    private double yCoord;
    public void initialize() {
        Random random = new Random();
        ball.setFill(Main.GAME_COLORS[random.nextInt(Main.GAME_COLORS.length)]);
    }
    public Color getColor(){
        return (Color) ball.getFill();
    }
    public double getVelocity() {
        return velocity;
    }
    public double[] getCoordinates() {
        return new double[]{xCoord, yCoord};
    }
    public double getRadius() {
        return ball.getRadius();
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
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    //TODO: Update Coordinate Function
    public void updateCoordinates() {
    }
}