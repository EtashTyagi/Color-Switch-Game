package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

// DONE MAIN
public class Main extends Application {
    final public static int STAGE_WIDTH = 500;
    final public static int STAGE_HEIGHT = 600;
    @Override
    public void start(Stage primaryStage) throws Exception{
        openMainMenu(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void openMainMenu(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent mainMenu = loader.load();
        MainMenu controller = loader.getController();
        controller.setMainStage(primaryStage);
        primaryStage.initStyle(StageStyle.UNDECORATED); //REMOVE TITLE BAR
        primaryStage.setTitle("Colour Switch");
        primaryStage.setScene(new Scene(mainMenu, STAGE_WIDTH, STAGE_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
