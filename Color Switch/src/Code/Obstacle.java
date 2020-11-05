package Code;

import javafx.scene.paint.Color;

//TODO: Check Error [Difficulty Must Be 0-1]
public abstract class Obstacle implements Collidable {
    private double difficulty;
    private Color[] colors;
    private Color passingColor;

    Obstacle(double difficulty, Color[] colors, Color passingColor) {
        this.difficulty = difficulty;
        this.colors = colors.clone();
        this.passingColor = passingColor;
    }
    abstract void doMovement();
    public Color[] getColors() {
        return colors;
    }
    public Color getPassingColor() {
        return passingColor;
    }
    public double getDifficulty() {
        return difficulty;
    }
}