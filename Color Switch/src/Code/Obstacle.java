package Code;

import javafx.scene.paint.Color;

//TODO: Check Error [Difficulty Must Be 0-1]
public abstract class Obstacle implements Collidable {
    private double difficulty;
    private Color[] colors;
    private Color passingColor;

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