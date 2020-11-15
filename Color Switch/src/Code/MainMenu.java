package Code;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//TODO: Design GUI
public class MainMenu extends GridPane {
    final private Label headingLabel = new Label("COLOR SWITCH");
    final private Font headingFont = new Font("Cambria", 32);

    private Button startEndlessButton = new Button("Hello");
    private Label endlessHighScoreLabel = new Label("10"); //TODO: Load Value AND Add somewhere
    final private Stage mainStage;

    //TODO: Main Menu Constructor
    public MainMenu(Stage primaryStage) {
        this.mainStage = primaryStage;

        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.CENTER);
        this.getColumnConstraints().add(col);

        this.setAlignment(Pos.CENTER);
        this.headingLabel.setFont(headingFont);
        this.add(headingLabel, 0, 0);
        this.add(startEndlessButton, 0, 1);
        startEndlessButton.setAlignment(Pos.CENTER);
        startEndlessButton.setOnMouseClicked(event -> startEndlessMode());
        setGridLinesVisible(true);
    }
    public void startEndlessMode() {
        Parent endlessGame = new EndlessGame(Integer.parseInt(endlessHighScoreLabel.getText()));
        mainStage.setScene(new Scene(endlessGame, Main.STAGE_WIDTH, Main.STAGE_HEIGHT));
    }
    //TODO: Load High Score Function [On Starting to display]
    private void loadHighScoreData() {
    }
    //TODO: Select Save File Function [On Click of Button]
    private void selectSaveFile() {
    }

}
