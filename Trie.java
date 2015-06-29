/**
 * @author Graham McAllister
 * @version 1.0
 *
 * Trie interface
 */
public interface Trie<T> {

    Boolean add(T word);

    Boolean isPrefix(T seq);

    Boolean contains(T word);

    int count(T word);

    int countPrefixes(T word);

}
