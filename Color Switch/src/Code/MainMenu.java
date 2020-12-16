package Code;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
    private Player player;
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
    private final AudioClip buttonESound = new AudioClip(new File("Resources\\Sound Effects\\button.wav").toURI().toString());
    private final AudioClip clickSound = new AudioClip(new File("Resources\\Sound Effects\\achat.wav").toURI().toString());
    private final AudioClip errorSound = new AudioClip(new File("Resources\\Sound Effects\\error.wav").toURI().toString());
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
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    @FXML public void initialize() throws IOException {
        startButton.setFill(startUnEnteredSprite);
        highScoreButton.setFill(highScoreUnEnteredSprite);
        settingsButton.setFill(settingsUnEnteredSprite);
        exitButton.setFill(exitUnEnteredSprite);
        highScoreScene = new Scene(FXMLLoader.load(getClass().getResource("HighScore.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        settingsScene = new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml")), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        mainMenuFuture = Main.scheduleForExecution(mainMenuAnimationTask, 10, 1);
        player = (PlayerFactory.createPlayer("Guest", "") == null) ? (PlayerFactory.validate("Guest", ""))
                : (PlayerFactory.validate("Guest", ""));
    }
    @FXML private void onClick(MouseEvent e) throws IOException {
        clickSound.play();
        if (e.getButton()== MouseButton.PRIMARY) {
            Arc source = ((Arc) e.getSource());
            if (source == startButton) {
                preGameOptions();
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
        buttonESound.play();
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
        Circle backButton = ((Circle) (settingsScene.lookup("#backButton")));
        backButton.setFill(backUnEnteredSprite);
        settingsFuture = Main.scheduleForExecution(settingsMenuAnimationTask, 0, 1);
        backButton.setOnMouseEntered((e) ->
        {
            buttonESound.play();
            backButton.setFill(backEnteredSprite);

        });
        backButton.setOnMouseExited((e) -> backButton.setFill(backUnEnteredSprite));
        backButton.setOnMouseClicked((e) ->
        {
            mainStage.setScene(mainScene);
            settingsFuture.cancel(false);
            mainMenuFuture = Main.scheduleForExecution(mainMenuAnimationTask, 0, 1);
        });
        Button loginButton = ((Button) (settingsScene.lookup("#loginButton")));
        TextField username = ((TextField) (settingsScene.lookup("#username")));
        TextField password = ((TextField) (settingsScene.lookup("#password")));
        Label loginLabel = ((Label) settingsScene.lookup("#loginLabel"));
        loginLabel.setText("Welcome "+ ((this.player == null) ? ("Guest") : (this.player.getUserName())));
        loginButton.setOnMouseClicked((e) -> {
            Player player = PlayerFactory.validate(username.getText(), password.getText());
            if (player == null) {
                errorSound.play();
                loginLabel.setText("Invalid Login!\nWelcome "+ ((this.player == null) ? ("Guest") : (this.player.getUserName())));
            } else {
                clickSound.play();
                this.player = player;
                loginLabel.setText("Welcome " + this.player.getUserName());
            }
        });
        Button signupButton = ((Button) (settingsScene.lookup("#signupButton")));
        signupButton.setOnMouseClicked((e) -> {
            if (!username.getText().isBlank()) {
                Player player = PlayerFactory.createPlayer(username.getText(), password.getText());
                if (player == null) {
                    errorSound.play();
                    loginLabel.setText("Player Exists!\nWelcome "+ ((this.player == null) ? ("Guest") : (this.player.getUserName())));
                } else {
                    clickSound.play();
                    this.player = player;
                    loginLabel.setText("Welcome " + this.player.getUserName());
                }
            } else {
                errorSound.play();
                loginLabel.setText("Blank Name!\nWelcome "+ ((this.player == null) ? ("Guest") : (this.player.getUserName())));
            }
        });
        Button applyButton = ((Button) (settingsScene.lookup("#applyButton")));
        ColorPicker cp1 = (ColorPicker) (settingsScene.lookup("#cp1"));
        ColorPicker cp2 = (ColorPicker) (settingsScene.lookup("#cp2"));
        ColorPicker cp3 = (ColorPicker) (settingsScene.lookup("#cp3"));
        ColorPicker cp4 = (ColorPicker) (settingsScene.lookup("#cp4"));
        cp1.setValue(Main.GAME_COLORS[0]);
        cp2.setValue(Main.GAME_COLORS[1]);
        cp3.setValue(Main.GAME_COLORS[2]);
        cp4.setValue(Main.GAME_COLORS[3]);
        applyButton.setOnMouseClicked((e) -> {
                Set<Color> selectedColors = new HashSet<>();
                selectedColors.add(cp1.getValue());
                selectedColors.add(cp2.getValue());
                selectedColors.add(cp3.getValue());
                selectedColors.add(cp4.getValue());
                if (selectedColors.size() == 4) {
                    clickSound.play();
                    Object[] colors = selectedColors.toArray();
                    Main.GAME_COLORS[0] = (Color) colors[0];
                    Main.GAME_COLORS[1] = (Color) colors[1];
                    Main.GAME_COLORS[2] = (Color) colors[2];
                    Main.GAME_COLORS[3] = (Color) colors[3];
                } else {
                    errorSound.play();
                    cp1.setValue(Main.GAME_COLORS[0]);
                    cp2.setValue(Main.GAME_COLORS[1]);
                    cp3.setValue(Main.GAME_COLORS[2]);
                    cp4.setValue(Main.GAME_COLORS[3]);
                }
            }
        );
        mainStage.setScene(settingsScene);
    }
    private void highScoreMenu() {
        mainMenuFuture.cancel(false);
        Scene mainScene = mainStage.getScene();
        mainStage.setScene(highScoreScene);
        highScoreFuture = Main.scheduleForExecution(highScoreMenuAnimationTask, 0, 1);
        Circle backButton = ((Circle) (highScoreScene.lookup("#backButton")));
        backButton.setFill(backUnEnteredSprite);
        backButton.setOnMouseEntered((e) ->
        {
            backButton.setFill(backEnteredSprite);
            buttonESound.play();
        });
        backButton.setOnMouseExited((e) -> backButton.setFill(backUnEnteredSprite));

        TableView<TableData> tableView = ((TableView<TableData>) (highScoreScene.lookup("#table")));
        tableView.getItems().addAll(PlayerFactory.getHighScoreData());

        backButton.setOnMouseClicked((e) -> {
            tableView.getItems().removeAll(tableView.getItems());
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
    private void preGameOptions() throws IOException {
        mainMenuFuture.cancel(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PreGameOptions.fxml"));
        Scene preScene = new Scene(loader.load(), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        Button newGame = (Button) preScene.lookup("#newGameButton");
        newGame.setOnMouseClicked((event -> {
            clickSound.play();
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    startGame(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        Button loadGame = (Button) preScene.lookup("#loadButton");
        ChoiceBox<String> gameChoiceBox = (ChoiceBox<String>) preScene.lookup("#options");
        ArrayList<String> savedGames = player.getSavedEndlessGames();
        for (String savedGame : savedGames) {
            gameChoiceBox.getItems().add(savedGame);
        }
        loadGame.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    String selected = gameChoiceBox.getValue();
                    if (selected != null) {
                        clickSound.play();
                        int saveNo = Integer.parseInt(selected.substring(selected.indexOf("(")+1, selected.indexOf(")")));
                        startGame(player.getSaveGameNo(saveNo));
                    } else {
                        errorSound.play();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mainStage.setScene(preScene);
    }
    private void startGame(Game load) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EndlessGame.fxml"));
        Scene endlessGameScene = new Scene(loader.load(), Main.STAGE_WIDTH, Main.STAGE_HEIGHT);
        EndlessGame controller = (loader.getController());
        controller.setMainStage(mainStage);
        controller.setPlayer(player);
        controller.load(load);
        mainStage.setScene(endlessGameScene);
    }
}