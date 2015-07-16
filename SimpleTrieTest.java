import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Test cases to ensure proper trie functioning
 * @author Graham McAllister
 * @version 1.0
 */
public class SimpleTrieTest {

    //Instance stuff
    SimpleTrie trie;
    String[] stringSet1 = {"Baby", "Babble", "Acid", "Junior", "jam", "jasmine"};
    String[] stringSet2 = {"gap", "god", "gods", "gold", "gone", "gun"};

    @Before
    public void setup() {
        trie = new SimpleTrie();
    }

    @Test
    public void testEmpty() {
        assertEquals(0, trie.size());
        assertFalse(trie.contains("Graham"));
        assertFalse(trie.contains("G"));
        assertFalse(trie.contains("-"));
    }

    @Test
    public void testSet1() {
        for (String s : stringSet1) {
            assertTrue(trie.add(s));
        }
        assertFalse(trie.add("0"));
        assertTrue(trie.contains("acid"));
        assertTrue(trie.contains("jam"));
        assertTrue(trie.contains("Baby"));
        assertFalse(trie.contains("jacob"));
        assertEquals(6, trie.size());
        assertFalse(trie.contains("0"));
    }

    @Test
    public void testWords() {
        for (String s : stringSet2) {
            assertTrue(trie.add(s));
        }
        assertTrue(trie.contains("god"));
        assertTrue(trie.contains("gods"));
        assertEquals(stringSet2.length, trie.size());
        ArrayList<String> words = trie.words("g");
        assertEquals(stringSet2.length, words.size());
        for (String word: words) {
            System.out.println(word);
        }
        int i = 0;
        for (String word: words) {
            assertEquals(stringSet2[i], word);
            i++;
        }
    }

    @Test
    public void testWords2() {
        for (String s : stringSet1) {
            assertTrue(trie.add(s));
        }
        ArrayList<String> words = trie.words("j");
        assertEquals(3, words.size());
        assertTrue(trie.contains("BABY"));
        for (String word: words) {
            System.out.println(word);
        }
    }

    @Test
    public void testWordsSimple() {
        for (String s : stringSet2) {
            assertTrue(trie.add(s));
        }
        assertTrue(trie.contains("god"));
        assertTrue(trie.contains("gods"));
        assertEquals(stringSet2.length, trie.size());
        ArrayList<String> words = trie.words("go");
        assertEquals(4, words.size());
        int i = 1;
        for (String word: words) {
            System.out.println(word);
            assertEquals(stringSet2[i], word);
            i++;
        }
    }

    @Test
    public void testCount() {
        for (String s : stringSet2) {
            assertTrue(trie.add(s));
        }
        assertEquals(6, trie.count("g"));
        assertEquals(4, trie.count("go"));
        assertEquals(0, trie.count("ha"));
    }

    @Test
    public void testisPrefix() {
        for (String s : stringSet2) {
            assertTrue(trie.add(s));
        }
        assertTrue(trie.isPrefix("go"));
        assertTrue(trie.isPrefix("god"));
        assertFalse(trie.isPrefix("pre"));
    }

}
