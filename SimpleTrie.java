/**
 * @author Graham McAllister
 * @version 1.0
 *
 * My simple implementation of a trie.
 * This trie is used for search autocomplete.
 * Memory usage will be minimal since it will only store the Entry title to search for
 * Look-ups are just as quick as a normal trie
 */
public class SimpleTrie implements Trie {

    private class Node {

        //Instance data
        private Character symbol;
        private Node[] children;

        public Node(Character symbol) {
            this.symbol = symbol;
            children = new Node[25];
        }
    }

    //Instance data
    private Node root;

    public SimpleTrie() {
        root = new Node('-');
    }

    /**
     * helper method that takes a character and returns the index it should be at
     * For example, a -> 0   b -> 1  c -> 2  ... z -> 25
     * Uses unicode method Unicode bounds:
     * a = 0010
     * z = 0035
     */
    private int getInd(Character c) {
        int i = Character.getNumericValue(c);
        return i - 10;
    }

    public Boolean add(String word) {
        //Search for the word to be added until the child node is null
        String normWord = word.toLowerCase();
        Node curNode = root;
        for (int i = 0; i < normWord.length(); i++) {
            int ind = getInd(normWord.charAt(i));
            if (curNode.children[ind] != null) {
                //Found another symbol, keep searching
                curNode = curNode.children[i];
            } else if (curNode.children[i] == null && i < normWord.length() - 1){
                //If the next node is null and more letters to add, add the symbol in and change pointers
                curNode.children[i] = new Node(normWord.charAt(i));
            } else {
                curNode.children[i] = new Node(normWord.charAt(i));
                //Now return true since the last character has been added
                return true;
            }
        }
        //Otherwise nothing has been added
        return false;
    }

    public Boolean isPrefix(String seq) {
        return null;
    }

    public Boolean contains(String word) {
        return null;
    }

    public int count(String word) {
        return 0;
    }

    public int countPrefixes(String word) {
        return 0;
    }
}
