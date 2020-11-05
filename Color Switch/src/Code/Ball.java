package Code;

import javafx.scene.paint.Color;

//TODO: Design Sprite
public class Ball {
    private double velocity;
    private Color color;
    private double radius;
    private double xCoord;
    private double yCoord;
    //TODO: Ball Constructor
    public Ball(double radius, double[] startCoordinates, Color color) {
    }
    public Color getColor(){
        return color;
    }
    public double getVelocity() {
        return velocity;
    }
    public double[] getCoordinates() {
        return new double[]{xCoord, yCoord};
    }
    public double getRadius() {
        return radius;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    //TODO: Update Coordinate Function
    public void updateCoordinates() {
    }
}