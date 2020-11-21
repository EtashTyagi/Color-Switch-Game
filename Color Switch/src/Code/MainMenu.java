package Code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class MainMenu {
    @FXML private Label mainLabel;
    @FXML private Arc startButton;
    @FXML private Arc highScoreButton;
    @FXML private Arc settingsButton;
    @FXML private Arc exitButton;
    @FXML private Group outerCircle;
    @FXML private Group middleCircle;
    @FXML private Group innerCircle;
    @FXML private ImagePattern startUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\StartUE.png"));
    @FXML private ImagePattern startEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\StartE.png"));
    @FXML private ImagePattern highScoreUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\HighScoreUE.png"));
    @FXML private ImagePattern highScoreEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\HighScoreE.png"));
    @FXML private ImagePattern settingsUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\SettingsUE.png"));
    @FXML private ImagePattern settingsEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\SettingsE.png"));
    @FXML private ImagePattern exitUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\ExitUE.png"));
    @FXML private ImagePattern exitEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\ExitE.png"));
    @FXML private ImagePattern backUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\backUE.png"));
    @FXML private ImagePattern backEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\backE.png"));
    @FXML private Scene highScoreScene;
    @FXML private Scene settingsScene;
    Thread animationThread;
    private Stage mainStage;

    public void setMainStage(Stage primaryStage) {
        this.mainStage = primaryStage;
    }
    @FXML public void initialize() throws IOException {
        startButton.setFill(startUnEnteredSprite);
        highScoreButton.setFill(highScoreUnEnteredSprite);
        settingsButton.setFill(settingsUnEnteredSprite);
        exitButton.setFill(exitUnEnteredSprite);
        highScoreScene = new Scene(FXMLLoader.load(getClass().getResource("HighScore.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        settingsScene = new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        animationThread = new Thread(() ->
        {   int changeTitleInTime = 1000; // Millisecond
            int updateInTime = 20; // Millisecond
            double[] randomRGB = new double[3];
            double[] curRGB = new double[3];
            Random random = new Random();
            int timeTillChangeTitle = 0;
            double rotateSpeed = 0.1; // Degree Per Mili Sec
            while (true) {
                try {
                    if (timeTillChangeTitle <= 0) {
                        timeTillChangeTitle = changeTitleInTime;
                        randomRGB[0] = random.nextInt(225)+30;
                        randomRGB[1] = random.nextInt(225)+30;
                        randomRGB[2] = random.nextInt(225)+30;
                    }
                    outerCircle.setRotate(outerCircle.getRotate()+rotateSpeed*updateInTime);
                    middleCircle.setRotate(middleCircle.getRotate()-rotateSpeed*updateInTime);
                    innerCircle.setRotate(innerCircle.getRotate()+rotateSpeed*updateInTime);
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
    @FXML private void onClick(MouseEvent e) {
        if (e.getButton()== MouseButton.PRIMARY) {
            Arc source = ((Arc) e.getSource());
            if (source == startButton) {
                Parent endlessGame = new EndlessGame(10);
                mainStage.setScene(new Scene(endlessGame, Main.STAGE_WIDTH, Main.STAGE_HEIGHT));
            } else if (source == highScoreButton) {
                highScoreMenu();
            } else if (source == settingsButton) {
                settingsMenu();
            } else if (source == exitButton) {
                animationThread.stop();
                mainStage.close();
            }
        }
    }
    @FXML private void mouseEnter(MouseEvent e) {
        Arc source = (Arc) e.getSource();
        if (source == startButton) {
            source.setFill(startEnteredSprite);
        } else if (source == highScoreButton) {
            source.setFill(highScoreEnteredSprite);
        } else if (source == settingsButton) {
            source.setFill(settingsEnteredSprite);
        } else if (source == exitButton) {
            source.setFill(exitEnteredSprite);
        }
    }
    @FXML private void mouseExit(MouseEvent e) {
        Arc source = (Arc) e.getSource();
        if (source == startButton) {
            source.setFill(startUnEnteredSprite);
        } else if (source == highScoreButton) {
            source.setFill(highScoreUnEnteredSprite);
        } else if (source == settingsButton) {
            source.setFill(settingsUnEnteredSprite);
        } else if (source == exitButton) {
            source.setFill(exitUnEnteredSprite);
        }
    }
    @FXML private void settingsMenu() {
        Scene mainScene = mainStage.getScene();
        mainStage.setScene(settingsScene);
        Thread settingsAnimationThread = new Thread(() ->
        {   int changeTitleInTime = 1000; // Millisecond
            int updateInTime = 20; // Millisecond
            double[] randomRGB = new double[3];
            double[] curRGB = new double[3];
            Random random = new Random();
            Label settingsLabel = ((Label) (settingsScene.lookup("#settingsLabel")));
            int timeTillChangeTitle = 0;
            while (true) {
                try {
                    if (timeTillChangeTitle <= 0) {
                        timeTillChangeTitle = changeTitleInTime;
                        randomRGB[0] = random.nextInt(225)+30;
                        randomRGB[1] = random.nextInt(225)+30;
                        randomRGB[2] = random.nextInt(225)+30;
                    }
                    curRGB[0] += ((randomRGB[0]-curRGB[0])/timeTillChangeTitle)*updateInTime;
                    curRGB[1] += ((randomRGB[1]-curRGB[1])/timeTillChangeTitle)*updateInTime;
                    curRGB[2] += ((randomRGB[2]-curRGB[2])/timeTillChangeTitle)*updateInTime;
                    settingsLabel.setTextFill(Color.rgb((int)curRGB[0], (int)curRGB[1], (int) curRGB[2]));
                    timeTillChangeTitle -= updateInTime;
                    Thread.sleep(updateInTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        settingsAnimationThread.start();
        Circle backButton = ((Circle) (settingsScene.lookup("#backButton")));
        backButton.setFill(backUnEnteredSprite);
        backButton.setOnMouseEntered((e) -> backButton.setFill(backEnteredSprite));
        backButton.setOnMouseExited((e) -> backButton.setFill(backUnEnteredSprite));
        backButton.setOnMouseClicked((e) -> {mainStage.setScene(mainScene) ; settingsAnimationThread.stop();});
    }
    @FXML private void highScoreMenu() {
        Scene mainScene = mainStage.getScene();
        mainStage.setScene(highScoreScene);
        Thread highScoreAnimationThread = new Thread(() ->
        {   int changeTitleInTime = 1000; // Millisecond
            int updateInTime = 20; // Millisecond
            double[] randomRGB = new double[3];
            double[] curRGB = new double[3];
            Random random = new Random();
            Label highScoreLabel = ((Label) (highScoreScene.lookup("#highScoreLabel")));
            int timeTillChangeTitle = 0;
            while (true) {
                try {
                    if (timeTillChangeTitle <= 0) {
                        timeTillChangeTitle = changeTitleInTime;
                        randomRGB[0] = random.nextInt(225)+30;
                        randomRGB[1] = random.nextInt(225)+30;
                        randomRGB[2] = random.nextInt(225)+30;
                    }
                    curRGB[0] += ((randomRGB[0]-curRGB[0])/timeTillChangeTitle)*updateInTime;
                    curRGB[1] += ((randomRGB[1]-curRGB[1])/timeTillChangeTitle)*updateInTime;
                    curRGB[2] += ((randomRGB[2]-curRGB[2])/timeTillChangeTitle)*updateInTime;
                    highScoreLabel.setTextFill(Color.rgb((int)curRGB[0], (int)curRGB[1], (int) curRGB[2]));
                    timeTillChangeTitle -= updateInTime;
                    Thread.sleep(updateInTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        highScoreAnimationThread.start();
        Circle backButton = ((Circle) (highScoreScene.lookup("#backButton")));
        backButton.setFill(backUnEnteredSprite);
        backButton.setOnMouseEntered((e) -> backButton.setFill(backEnteredSprite));
        backButton.setOnMouseExited((e) -> backButton.setFill(backUnEnteredSprite));
        backButton.setOnMouseClicked((e) -> {mainStage.setScene(mainScene) ; highScoreAnimationThread.stop();});
    }
    //TODO: Load High Score Function
    private void loadHighScoreData() {
    }
    //TODO: Select Save File Function
    private void selectSaveFile() {
    }
}
