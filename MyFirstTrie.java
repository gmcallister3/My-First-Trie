import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * @author Graham McAllister
 * @version 1.0
 *
 * Controller class for javaFX GUI
 */
public class MyFirstTrie extends Application {

    //Instance variables

    /** tableView data for entries */
    private ObservableList<Entry> masterData = FXCollections.observableArrayList();
    /** tableView for entries */
    private ListView entryList;

    /** Buttons */
    private Button sortDate;
    private Button search;
    private Button submit;
    private Button deleteEntry;
    private Button newEntry;

    /** Labels and other control elements */
    private Label title;
    private Label titleLabel;
    private TextField searchBar;
    private TextArea body;
    private TextField titleName;

    /** Panels and other scene elements */
    private HBox root;
    private HBox footer;
    private HBox header;
    private VBox sidePane;
    private VBox entryBlock;

    /** Viewing/Tracking data */
    private Entry selectedEntry;


    /**
     * Application viewer controller
     * @param primaryStage            GUI application window
     */
    public void start(Stage primaryStage) {

        //Instantiating scene elements and other stuff
        sortDate = new Button("Sort by Date");
        search = new Button("Search");
        submit = new Button("Submit");
        deleteEntry = new Button("Delete");
        newEntry = new Button("New");
        title = new Label("Title: ");
        titleLabel = new Label();
        searchBar = new TextField();
        titleName = new TextField();
        body = new TextArea();
        footer = new HBox();
        header = new HBox();
        root = new HBox();
        sidePane = new VBox();
        entryBlock = new VBox();
        FilteredList<Entry> filteredData = new FilteredList<Entry>(masterData);
        SortedList<Entry> sortedData = new SortedList<Entry>(filteredData);
        entryList = new ListView(sortedData);

        //Set so you can select multiple entries
//        entryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Adding action events
        sortDate.setOnMouseClicked(e -> {
            sortedData.setComparator((entry1, entry2) -> {
                //Sort by entry date of creation
                return entry1.getDate().compareTo(entry2.getDate());
            });
        });

        //Deleting an entry
        deleteEntry.setOnMouseClicked(e -> {
            selectedEntry = (Entry) entryList.getSelectionModel().getSelectedItem();
            masterData.remove(selectedEntry);
        });

        //Viewing an entry
        entryList.setOnMouseClicked(e -> {
            selectedEntry = (Entry) entryList.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                body.setText(selectedEntry.getBody());
                titleLabel.setText(selectedEntry.getTitle());
            }
        });


        //Adding an entry
        newEntry.setOnMouseClicked(e -> {
            body.setText("Add a title below, start typing here and, when done press submit (below).");
        });
        submit.setOnMouseClicked(e -> {
            Entry newEntry = new Entry();
            newEntry.setBody(body.getText());
            newEntry.setTitle(titleName.getText());
            masterData.add(newEntry);
        });


        searchBar.textProperty().addListener(((observable, oldValue, newValue) -> {

            //Change data
            filteredData.setPredicate(entry -> {
                //If value in search bar is empty or null, don't filter
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                //Compare case insensitively
                String searched = newValue.toLowerCase();
                if (entry.getTitle().toLowerCase().contains(searched)) {
                    //Filter if title matches the searched word
                    return true;
                } else if (entry.getBody().toLowerCase().contains(searched)) {
                    //Filter if body contains the searched word
                    return true;
                }
                //Otherwise return false
                return false;
            });
        }));




        //Formatting/Resizing scene elements
        searchBar.setPromptText("Search");
        sortDate.setMinSize(sortDate.getPrefWidth(), sortDate.getPrefHeight());
        sortDate.setMaxSize(sortDate.getPrefWidth(), sortDate.getPrefHeight());
        sidePane.setAlignment(Pos.CENTER);

        footer.getChildren().addAll(title, titleName, submit);
        header.getChildren().addAll(titleLabel, deleteEntry, newEntry);
        sidePane.getChildren().addAll(sortDate, searchBar, entryList);
        entryBlock.getChildren().addAll(header, body, footer);
        root.getChildren().addAll(sidePane, entryBlock);

        Scene rootScene = new Scene(root, 800, 500);
        primaryStage.setTitle("MyFirstTrie - A diary application by Graham McAllister");
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
