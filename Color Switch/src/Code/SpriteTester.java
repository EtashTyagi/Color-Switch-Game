package Code;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class SpriteTester {
    private Stage mainStage;
    private Scene mainMenu;
    private Thread animationThread;
    @FXML private Label mainLabel;
    @FXML private ComboBox<String> selection;
    @FXML private GridPane spriteTesterGP;
    @FXML private Node curLoaded;
    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("ConcentricTripleCircleObstacle",
                "AdjacentDoubleCircleObstacle", "SingleCircleObstacle", "TriangleObstacle", "SquareObstacle", "LinesObstacle"
                , "CrossObstacle", "Switch", "Star", "Ball");
        selection.setItems(options);
        selection.getSelectionModel().selectFirst();
        animationThread = new Thread(() ->
        {   int changeTitleInTime = 1000; // Millisecond
            double[] randomRGB = new double[3];
            double[] curRGB = new double[]{255, 255, 255};
            Random random = new Random();
            AtomicInteger timeTillChangeTitle = new AtomicInteger();
            while (true) {
                Platform.runLater(() ->
                {
                    if (timeTillChangeTitle.get() <= 0) {
                        timeTillChangeTitle.set(changeTitleInTime);
                        randomRGB[0] = random.nextInt(225)+30;
                        randomRGB[1] = random.nextInt(225)+30;
                        randomRGB[2] = random.nextInt(225)+30;
                    }
                    curRGB[0] += ((randomRGB[0]-curRGB[0])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
                    curRGB[1] += ((randomRGB[1]-curRGB[1])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
                    curRGB[2] += ((randomRGB[2]-curRGB[2])/ timeTillChangeTitle.get())*Main.UPDATE_IN;
                    mainLabel.setTextFill(Color.rgb((int)curRGB[0], (int)curRGB[1], (int) curRGB[2]));
                    timeTillChangeTitle.addAndGet(-Main.UPDATE_IN);
                });
                try {
                    Thread.sleep(Main.UPDATE_IN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        animationThread.start();
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public void setMainMenuScene(Scene mainMenu) {
        this.mainMenu = mainMenu;
    }
    public void onBack() {
        animationThread.stop();
        mainStage.setScene(mainMenu);
    }
    public void onShow() throws IOException {
        if (curLoaded != null) {
            spriteTesterGP.getChildren().remove(curLoaded);
        }
        curLoaded = FXMLLoader.load(getClass().getResource(selection.getSelectionModel().getSelectedItem()+".fxml"));
        spriteTesterGP.add(curLoaded, 0, 5);
        // Align All
        double xRad; double yRad;
        switch (selection.getSelectionModel().getSelectedItem()) {
            case "ConcentricTripleCircleObstacle":
                xRad = 141.5; yRad = 141.5; break;
            case "AdjacentDoubleCircleObstacle":
                xRad = 190; yRad = 95; break;
            case "SingleCircleObstacle":
            case "CrossObstacle":
            case "TriangleObstacle":
                xRad = 100; yRad = 100; break;
            case "SquareObstacle":
                xRad = 75; yRad = 75; break;
            case "LinesObstacle":
                xRad = 250; yRad = 10; break;
            case "Switch":
            case "Ball":
                xRad = 12.5; yRad = 12.5; break;
            case "Star":
                xRad = 20; yRad = 20; break;
            default:
                xRad=0; yRad=0; break;
        }
        curLoaded.setTranslateX(250 - xRad);
        curLoaded.setTranslateY(200 - yRad);
    }
}
