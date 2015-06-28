import java.time.LocalDateTime;

/**
 * @author Graham McAllister
 * @version 1.0
 *
 * Custom object for diary entries
 */
public class Entry {

    //Instance data
    private LocalDateTime created;
    private String title;
    private String body;

    /**
     * Zero-args constructor
     */
    public Entry() {
        created = LocalDateTime.now();
    }

    /**
     * Replaces the text of Entry with the newText parameter
     * @param newText           new body text for Entry
     */
    public void edit(String newText) {
        body = newText;
    }

    /**
     * Replaces the title of Entry with the newTitle parameter
     * @param newTitle          new title for Entry
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }



}
