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
    public void setBody(String newText) {
        body = newText;
    }

    /**
     * Replaces the title of Entry with the newTitle parameter
     * @param newTitle          new title for Entry
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Public getter to return the title of Entry
     * @return String           the title of Entry
     */
    public String getTitle() {
        return title;
    }

    /**
     * Public getter to return the body of Entry
     * @return String           the body of Entry
     */
    public String getBody() {
        return body;
    }

    /**
     * Public getter to return the date of Entry
     * @return LocalDateTime          the date of Entry
     */
    public LocalDateTime getDate() {
        return created;
    }

    @Override
    public String toString() {
        return title;
    }
}
