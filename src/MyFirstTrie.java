import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;


/**
 * @author Graham McAllister
 * @version 1.0
 *
 * Controller class for javaFX GUI
 */
public class MyFirstTrie extends Application {

    //Instance variables

    /** tableView data for entries */
    private ObservableList<Entry> entryData = FXCollections.observableArrayList();
    /** tableView for entries */
    private TableView entryList;

    /** Buttons */
    private Button sortDate;
    private Button search;

    /** Labels and other control elements */
    private Label title;
    private TextField searchBar;
    private TextArea body;

    /** Panels and other scene elements */
    private HBox root;
    private VBox lower;
    private HBox navigationBar;

    /**
     * Application viewer controller
     * @param primaryStage            GUI application window
     */
    public void start(Stage primaryStage) {

        //Instantiating scene elements
        entryList = new TableView(entryData);
        sortDate = new Button("Sort by Date");
        search = new Button("Search");
        title = new Label("Title: ");
        searchBar = new TextField();
        body = new TextArea();
        lower = new VBox();
        navigationBar = new HBox();
        root = new HBox();
        lower.getChildren().addAll(entryList, body);
        navigationBar.getChildren().addAll(sortDate, searchBar);
        root.getChildren().addAll(lower, navigationBar);


        //Formatting scene elements
        searchBar.setPromptText("Search");



        Scene rootScene = new Scene(root, 800, 500);
        primaryStage.setTitle("MyFirstTrie - A diary application by Graham McAllister");
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
