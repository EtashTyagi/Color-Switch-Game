package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

// DONE MAIN
public class Main extends Application {
    final public static int STAGE_WIDTH = 500;
    final public static int STAGE_HEIGHT = 700;
    final public static Color[] GAME_COLORS = new Color[]{Color.ROYALBLUE, Color.GOLD, Color.DARKKHAKI, Color.FORESTGREEN};
    final public static int UPDATE_IN = 20; // Milliseconds
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
        /*
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        mainMenu.setOnMousePressed(event -> {
            xOffset.set(event.getSceneX());
            yOffset.set(event.getSceneY());
        });
        mainMenu.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset.get());
            primaryStage.setY(event.getScreenY() - yOffset.get());
        });
         */ // Remove comment to make dragable, implement everywhere
        primaryStage.setScene(new Scene(mainMenu, STAGE_WIDTH, STAGE_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
