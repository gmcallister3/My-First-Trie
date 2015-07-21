import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Reads in the english dictionary txt file and loads the words into the trie
 * @author Graham McAllister
 * @version 1.0
 */
public class DictionaryLoader {

    public static void loadDict(Trie trie) throws FileNotFoundException {
        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/wordsEn.txt"));
            in.lines().forEach(trie::add);
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found.");
        }
    }

}
