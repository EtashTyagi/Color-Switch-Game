package Code;

import javafx.application.Platform;
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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class MainMenu {
    @FXML private Label mainLabel;
    @FXML private Arc startButton;
    @FXML private Arc highScoreButton;
    @FXML private Arc settingsButton;
    @FXML private Arc exitButton;
    @FXML private Group outerCircle;
    @FXML private Group middleCircle;
    @FXML private Group innerCircle;
    private final ImagePattern startUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\StartUE.png"));
    private final ImagePattern startEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\StartE.png"));
    private final ImagePattern highScoreUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\HighScoreUE.png"));
    private final ImagePattern highScoreEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\HighScoreE.png"));
    private final ImagePattern settingsUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\SettingsUE.png"));
    private final ImagePattern settingsEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\SettingsE.png"));
    private final ImagePattern exitUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\ExitUE.png"));
    private final ImagePattern exitEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\ExitE.png"));
    private final ImagePattern backUnEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\backUE.png"));
    private final ImagePattern backEnteredSprite = new ImagePattern(new Image("file:Resources\\Images\\backE.png"));
    private ScheduledFuture<?> mainMenuFuture;
    private Scene highScoreScene;
    private ScheduledFuture<?> highScoreFuture;
    private Scene settingsScene;
    private ScheduledFuture<?> settingsFuture;
    private Stage mainStage;
    private final int changeTitleInTime = 1000;
    private final double[] randomRGB = new double[3];
    private final double[] curRGB = new double[]{255, 255, 255};
    private final AtomicInteger timeTillChangeTitle = new AtomicInteger();
    double rotateSpeed = 0.075;
    private final Runnable mainMenuAnimationTask = () -> {
        Platform.runLater(() -> {
            if (timeTillChangeTitle.get() <= 0) {
                timeTillChangeTitle.set(changeTitleInTime);
                randomRGB[0] = Main.RANDOM.nextInt(225)+30;
                randomRGB[1] = Main.RANDOM.nextInt(225)+30;
                randomRGB[2] = Main.RANDOM.nextInt(225)+30;
            }
            outerCircle.setRotate(outerCircle.getRotate()+rotateSpeed*Main.UPDATE_IN);
            middleCircle.setRotate(middleCircle.getRotate()-rotateSpeed*Main.UPDATE_IN);
            innerCircle.setRotate(innerCircle.getRotate()+rotateSpeed*Main.UPDATE_IN);
            curRGB[0] += ((randomRGB[0]-curRGB[0])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            curRGB[1] += ((randomRGB[1]-curRGB[1])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            curRGB[2] += ((randomRGB[2]-curRGB[2])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            mainLabel.setTextFill(Color.rgb((int)curRGB[0], (int)curRGB[1], (int) curRGB[2]));
            timeTillChangeTitle.addAndGet(-Main.UPDATE_IN);
        });
    };
    private final Runnable settingsMenuAnimationTask = () -> {
        Label settingsLabel = ((Label) (settingsScene.lookup("#settingsLabel")));
        Platform.runLater(() ->
        {
            if (timeTillChangeTitle.get() <= 0) {
                timeTillChangeTitle.set(changeTitleInTime);
                randomRGB[0] = Main.RANDOM.nextInt(225)+30;
                randomRGB[1] = Main.RANDOM.nextInt(225)+30;
                randomRGB[2] = Main.RANDOM.nextInt(225)+30;
            }
            curRGB[0] += ((randomRGB[0]-curRGB[0])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            curRGB[1] += ((randomRGB[1]-curRGB[1])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            curRGB[2] += ((randomRGB[2]-curRGB[2])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            settingsLabel.setTextFill(Color.rgb((int)curRGB[0], (int)curRGB[1], (int) curRGB[2]));
            timeTillChangeTitle.addAndGet(-Main.UPDATE_IN);
        });
    };
    private final Runnable highScoreMenuAnimationTask = () -> {
        Label highScoreLabel = ((Label) (highScoreScene.lookup("#highScoreLabel")));
        Platform.runLater(() ->
        {
            if (timeTillChangeTitle.get() <= 0) {
                timeTillChangeTitle.set(changeTitleInTime);
                randomRGB[0] = Main.RANDOM.nextInt(225)+30;
                randomRGB[1] = Main.RANDOM.nextInt(225)+30;
                randomRGB[2] = Main.RANDOM.nextInt(225)+30;
            }
            curRGB[0] += ((randomRGB[0]-curRGB[0])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            curRGB[1] += ((randomRGB[1]-curRGB[1])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            curRGB[2] += ((randomRGB[2]-curRGB[2])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
            highScoreLabel.setTextFill(Color.rgb((int)curRGB[0], (int)curRGB[1], (int) curRGB[2]));
            timeTillChangeTitle.addAndGet(-Main.UPDATE_IN);
        });
    };

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
        mainMenuFuture = Main.scheduleForExecution(mainMenuAnimationTask, 10, 1);
    }
    @FXML private void onClick(MouseEvent e) throws IOException {
        if (e.getButton()== MouseButton.PRIMARY) {
            Arc source = ((Arc) e.getSource());
            if (source == startButton) {
                preGameOptions();
                startGame();
            } else if (source == highScoreButton) {
                highScoreMenu();
            } else if (source == settingsButton) {
                settingsMenu();
            } else if (source == exitButton) {
                mainStage.close();
                System.exit(0);
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
    private void settingsMenu() {
        mainMenuFuture.cancel(false);
        Scene mainScene = mainStage.getScene();
        mainStage.setScene(settingsScene);
        Circle backButton = ((Circle) (settingsScene.lookup("#backButton")));
        backButton.setFill(backUnEnteredSprite);
        settingsFuture = Main.scheduleForExecution(settingsMenuAnimationTask, 0, 1);
        backButton.setOnMouseEntered((e) -> backButton.setFill(backEnteredSprite));
        backButton.setOnMouseExited((e) -> backButton.setFill(backUnEnteredSprite));
        backButton.setOnMouseClicked((e) ->
        {
            mainStage.setScene(mainScene);
            settingsFuture.cancel(false);
            mainMenuFuture = Main.scheduleForExecution(mainMenuAnimationTask, 0, 1);
        });
    }
    private void highScoreMenu() {
        mainMenuFuture.cancel(false);
        Scene mainScene = mainStage.getScene();
        mainStage.setScene(highScoreScene);
        highScoreFuture = Main.scheduleForExecution(highScoreMenuAnimationTask, 0, 1);
        Circle backButton = ((Circle) (highScoreScene.lookup("#backButton")));
        backButton.setFill(backUnEnteredSprite);
        backButton.setOnMouseEntered((e) -> backButton.setFill(backEnteredSprite));
        backButton.setOnMouseExited((e) -> backButton.setFill(backUnEnteredSprite));
        backButton.setOnMouseClicked((e) -> {
            mainStage.setScene(mainScene) ;
            highScoreFuture.cancel(false);
            mainMenuFuture = Main.scheduleForExecution(mainMenuAnimationTask, 0, 1);
        });
    }
    private void spriteTester() throws IOException {
        // For Debug
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SpriteTester.fxml"));
        Parent guiTest = loader.load();
        SpriteTester spriteTester = loader.getController();
        spriteTester.setMainStage(mainStage);
        spriteTester.setMainMenuScene(mainStage.getScene());
        mainStage.setScene(new Scene(guiTest, Main.STAGE_WIDTH, Main.STAGE_HEIGHT));

    }
    private void preGameOptions() {

    }
    private void startGame() throws IOException {
        mainMenuFuture.cancel(false);
        Scene mainScene = mainStage.getScene();
        Scene endlessGameScene = new Scene(FXMLLoader.load(getClass().getResource("EndlessGame.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        mainStage.setScene(endlessGameScene);
        Circle backButton = ((Circle) (endlessGameScene.lookup("#pauseButton")));
        backButton.setFill(backUnEnteredSprite);
        backButton.setOnMouseEntered((e) -> backButton.setFill(backEnteredSprite));
        backButton.setOnMouseExited((e) -> backButton.setFill(backUnEnteredSprite));
        backButton.setOnMouseClicked((e) ->
        {
            mainStage.setScene(mainScene);
            mainMenuFuture = Main.scheduleForExecution(mainMenuAnimationTask, 0, 1);
        });
    }
    //TODO: Load High Score Function
    private void loadHighScoreData() {
    }
    //TODO: Select Save File Function
    private void selectSaveFile() {
    }
}