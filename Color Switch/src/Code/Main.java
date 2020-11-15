package Code;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// DONE MAIN
public class Main extends Application {
    final public static int STAGE_WIDTH = 800;
    final public static int STAGE_HEIGHT = 500;
    @Override
    public void start(Stage primaryStage) throws Exception{
        openMainMenu(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
    private static void openMainMenu(Stage primaryStage) {
        Parent mainMenu = new MainMenu(primaryStage);
        primaryStage.setTitle("Colour Switch");
        primaryStage.setScene(new Scene(mainMenu, STAGE_WIDTH, STAGE_HEIGHT));
        primaryStage.show();
    }
}
