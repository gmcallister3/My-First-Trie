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

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.geometry.Side;

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

    //Other data
    private PatriciaTrie entryTrie;
    private SimpleTrie dictionary;

    /** Buttons */
    private Button sortDate;
    private Button search;
    private Button submit;
    private Button deleteEntry;
    private Button newEntry;

    /** Labels and other control elements */
    private Label title;
    private Label titleLabel;
    private ContextMenu autocompleteBox;
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
    public void start(Stage primaryStage) throws FileNotFoundException {

        //Instantiating scene elements and other stuff
        sortDate = new Button("Sort by Date");
        search = new Button("Search");
        submit = new Button("Submit");
        deleteEntry = new Button("Delete");
        newEntry = new Button("New");
        title = new Label("Title: ");
        titleLabel = new Label();
        autocompleteBox = new ContextMenu();
        searchBar = new TextField();
        titleName = new TextField();
        body = new TextArea();
        footer = new HBox();
        header = new HBox();
        root = new HBox();
        sidePane = new VBox();
        entryBlock = new VBox();
        searchBar.setContextMenu(autocompleteBox);

        //Instantiating data in tries
        dictionary = new SimpleTrie();
        entryTrie = new PatriciaTrie();
        //Load data
        DictionaryLoader.loadDict(dictionary);
        EntryLoader.loadEntries(masterData, entryTrie);
        FilteredList<Entry> filteredData = new FilteredList<Entry>(masterData);
        SortedList<Entry> sortedData = new SortedList<Entry>(filteredData);
        entryList = new ListView<>(sortedData);
        System.out.println(dictionary.size());

        //Set so you can select multiple entries
//        entryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //TODO - TextFormatter to restrict the Entry title input
        //TextFormatter titleREGEX = new TextFormatter()
//        Pattern titleREGEX = Pattern.compile("[a-zA-Z0-9\s]+");
//        titleName.setTextFormatter(titleREGEX);
        //Regex for checking if the string is totally alphabetic
        Pattern alphabetic = Pattern.compile("[a-zA-Z]*");

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
            //Add the title to the trie for searching purposes
            entryTrie.add(titleName.getText());
        });

        //Add change listener to body text, checks if each word is in the dictionary
        //TODO - Check individual words?
        body.textProperty().addListener(((observable1, oldValue1, newValue1) -> {
            //Split the observed text into individual words
            //Check if the new value is non alphabetic (representing a new word)
//            String word = newValue1;
//            if (!word.isEmpty()) {
//                Matcher m = alphabetic.matcher(word);
//                //If symbol doesn't match, shift index to start new word
//                if (!m.matches()) {
//                    //Find last mismatch index
//                    int beginInd = m.end();
//                    System.out.println(beginInd);
//                    word = word.substring(beginInd);
//                }
//            }
            //Color determinant if the typed word is in dictionary
            if (dictionary.contains(newValue1)) {
                body.setStyle("-fx-text-fill: green");

            } else {
                body.setStyle("-fx-text-fill: firebrick");
            }
        }));

        //Add change listener to searchBar for filtering and autocomplete purposes
        searchBar.textProperty().addListener(((observable, oldValue, newValue) -> {
            autocompleteBox.hide();
            autocompleteBox.getItems().clear();
            //Get strings to display in searchSuggestion
            if (!newValue.equals("") && entryTrie.isPrefix(newValue)) {
                ArrayList<String> searchData = entryTrie.expressions(newValue);
                //Display searchData in popup menu
                for (String entry : searchData) {
                    MenuItem title = new MenuItem(entry);
                    autocompleteBox.getItems().addAll(title);
                }
                autocompleteBox.show(searchBar, Side.BOTTOM, 0, 0);
            }
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

    private void populateSearch(String seq) {

    }

}
