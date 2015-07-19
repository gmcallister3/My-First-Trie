import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Test cases to ensure proper trie functioning
 * @author Graham McAllister
 * @version 1.0
 */
public class TrieTest {

    //Instance stuff
    SimpleTrie trie;
    PatriciaTrie trie2;
    String[] stringSet = {"all", "all over", "great", "Greatness", "Hall", "Help"};
    String[] stringSet1 = {"Baby", "Babble", "Acid", "Junior", "jam", "jasmine"};
    String[] stringSet2 = {"gap", "god", "gods", "gold", "gone", "gun"};

    @Before
    public void setup() {
        trie = new SimpleTrie();
        trie2 = new PatriciaTrie();
    }

    //Testing SimpleTrie

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

    //Testing PatriciaTrie
    @Test
    public void testAdding1() {
        assertEquals(0, trie2.size());
        assertFalse(trie2.contains("hello"));
        assertTrue(trie2.add("Hello"));
        assertTrue(trie2.contains("hello"));
        assertTrue(trie2.contains("Hello"));
        assertEquals(1, trie2.size());
    }

    @Test
    public void testAddMany() {
        for (String exp : stringSet) {
            assertTrue(trie2.add(exp));
        }
        assertEquals(stringSet.length, trie2.size());
        assertTrue(trie2.contains("all"));
        assertTrue(trie2.contains("all over"));
        assertTrue(trie2.contains("hall"));
        assertTrue(trie2.contains("help"));
        assertFalse(trie2.contains("h"));
        assertTrue(trie2.isPrefix("all"));
    }

    @Test
    public void testCount2() {
        for (String exp : stringSet) {
            assertTrue(trie2.add(exp));
        }
        assertEquals(2, trie2.count("h"));
        assertEquals(2, trie2.count("g"));
        assertEquals(2, trie2.count("great"));
        assertEquals(1, trie2.count("all ov"));
    }

    @Test
    public void testIsPrefix() {
        for (String exp : stringSet) {
            assertTrue(trie2.add(exp));
        }
        assertFalse(trie2.isPrefix("x"));
        assertFalse(trie2.isPrefix("halls"));
        assertTrue(trie2.isPrefix("grea"));
        assertTrue(trie2.isPrefix("all "));
    }

    @Test
    public void testExpressions() {
        for (String exp : stringSet) {
            assertTrue(trie2.add(exp));
        }
        ArrayList<String> aExp = trie2.expressions("all");
        System.out.println("all- expressions: ");
        for (String exp : aExp) {
            System.out.println(exp);
        }
        ArrayList<String> hExp = trie2.expressions("ha");
        System.out.println("ha- expressions: ");
        for (String exp : hExp) {
            System.out.println(exp);
        }
        ArrayList<String> gExp = trie2.expressions("great");
        System.out.println("great- expressions: ");
        for (String exp : gExp) {
            System.out.println(exp);
        }
    }
}
