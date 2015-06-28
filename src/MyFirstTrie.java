import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableView;


/**
 * @author Graham McAllister
 * @version 1.0
 *
 * Controller class for javaFX GUI
 */
public class MyFirstTrie extends Application {

    //Instance variables
    private ObservableList<Entry> entryData = new ObservableList();

    /**
     * Application viewer controller
     * @param primaryStage            GUI application window
     */
    public void start(Stage primaryStage) {

        Scene rootScene = new Scene(root, 800, 500);
        primaryStage.setTitle("MyFirstTrie - A diary application by Graham McAllister");
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
