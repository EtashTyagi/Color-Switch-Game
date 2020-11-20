package Code;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;
import java.util.Random;

//TODO: Design GUI [DONE]
public class MainMenu {
    @FXML
    private Label mainLabel;
    @FXML
    private Arc startButton; //TOP LEFT TODO: SPRITE
    @FXML
    private Arc highScoreButton; //TOP RIGHT TODO: SPRITE
    @FXML
    private Arc loadButton; //BOTTOM LEFT TODO: SPRITE
    @FXML
    private Arc exitButton; //BOTTOM RIGHT TODO: SPRITE
    @FXML
    private Group outerCircle;
    @FXML
    private Group middleCircle;
    @FXML
    private Group innerCircle;
    Thread animationThread;
    private Stage mainStage;

    public void setMainStage(Stage primaryStage) {
        this.mainStage = primaryStage;
    }
    @FXML
    public void initialize() {
        animationThread = new Thread(() ->
        {   int changeTitleInTime = 500; // Millisecond
            int updateInTime = 20; // Millisecond
            double[] randomRGB = new double[3];
            double[] curRGB = new double[3];
            Random random = new Random();
            int timeTillChangeTitle = 0;
            while (true) {
                try {
                    if (timeTillChangeTitle <= 0) {
                        timeTillChangeTitle = changeTitleInTime;
                        randomRGB[0] = random.nextInt(225)+30;
                        randomRGB[1] = random.nextInt(225)+30;
                        randomRGB[2] = random.nextInt(225)+30;
                    }
                    outerCircle.setRotate(outerCircle.getRotate()+2);
                    middleCircle.setRotate(middleCircle.getRotate()-2);
                    innerCircle.setRotate(innerCircle.getRotate()+2);
                    curRGB[0] += ((randomRGB[0]-curRGB[0])/timeTillChangeTitle)*updateInTime;
                    curRGB[1] += ((randomRGB[1]-curRGB[1])/timeTillChangeTitle)*updateInTime;
                    curRGB[2] += ((randomRGB[2]-curRGB[2])/timeTillChangeTitle)*updateInTime;
                    mainLabel.setTextFill(Color.rgb((int)curRGB[0], (int)curRGB[1], (int) curRGB[2]));
                    timeTillChangeTitle -= updateInTime;
                    Thread.sleep(updateInTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
    @FXML
    private void onClick(Event e) {
        Arc source = ((Arc) e.getSource());
        animationThread.stop();
        if (source == startButton) {
            Parent endlessGame = new EndlessGame(10);
            mainStage.setScene(new Scene(endlessGame, Main.STAGE_WIDTH, Main.STAGE_HEIGHT));
        } else if (source == highScoreButton){ //TODO

        } else if (source == loadButton) { //TODO

        } else if (source == exitButton) {
            mainStage.close();
        }
    }
    @FXML
    private void mouseEnter(Event e) {
        Arc source = (Arc) e.getSource();
        source.setFill(Color.LIGHTGRAY);
    }
    @FXML
    private void mouseExit(Event e) {
        Arc source = (Arc) e.getSource();
        source.setFill(Color.DARKGRAY);
    }
    //TODO: Load High Score Function [On Starting to display]
    private void loadHighScoreData() {
    }
    //TODO: Select Save File Function [On Click of Button]
    private void selectSaveFile() {
    }

}
