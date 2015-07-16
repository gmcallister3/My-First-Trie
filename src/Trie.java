/**
 * @author Graham McAllister
 * @version 1.0
 *
 * Trie interface
 */
public interface Trie {

    Boolean add(String word);

    Boolean isPrefix(String seq);

    Boolean contains(String word);

    int count(String prefix);

    int size();
}
