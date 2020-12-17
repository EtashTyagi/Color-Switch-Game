package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


public class Main extends Application {
    final public static double STAGE_WIDTH = 500;
    final public static double STAGE_HEIGHT = 700;
    final public static Color[] GAME_COLORS = new Color[]{Color.ROYALBLUE, Color.GOLD, Color.DARKKHAKI, Color.FORESTGREEN};
    final private static int FPS = 60;
    final public static int UPDATE_IN = 1000 / FPS; // Milliseconds
    final private static ScheduledExecutorService executorService =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    final public static Random RANDOM = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception{
        openMainMenu(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void openMainMenu(Stage primaryStage) throws IOException {
        primaryStage.setOnCloseRequest((e) -> System.exit(0));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent mainMenu = loader.load();
        MainMenu controller = loader.getController();
        controller.setMainStage(primaryStage);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Colour Switch");
        primaryStage.setScene(new Scene(mainMenu, STAGE_WIDTH, STAGE_HEIGHT));
        makeDraggable(primaryStage.getScene(), primaryStage);
        primaryStage.show();
    }
    public static ScheduledFuture<?> scheduleForExecution(Runnable task, int initialDelay, int updateInFrames) {
        return executorService
                .scheduleWithFixedDelay(task, UPDATE_IN*initialDelay, UPDATE_IN*updateInFrames, TimeUnit.MILLISECONDS);
    }
    public static void makeDraggable(Scene node, Stage primaryStage) {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        node.setOnMousePressed(event -> {
            xOffset.set(event.getSceneX());
            yOffset.set(event.getSceneY());
        });
        node.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset.get());
            primaryStage.setY(event.getScreenY() - yOffset.get());
        });

    }
}
