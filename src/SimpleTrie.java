import java.util.LinkedList;
import java.util.Stack;
import java.util.ArrayList;

/**
 * @author Graham McAllister
 * @version 1.0
 *
 * My implementation of a simple trie:
 *      Each node corresponds to a letter
 *      Children represented as a hashTable (array for 26 characters)
 * This trie is used for storing a dictionary.
 * Memory usage will be minimal since many prefixes are words in the English dictionary.
 * Look-ups are just as quick as a normal trie.
 *<p>
 *  DFS performed in two ways:
 *      with a stack - count method
 *      recursion    - words method
 *</p>
 */
public class SimpleTrie implements Trie {

    private class Node {

        //Instance data
        private Character symbol;
        private Node[] children;
        private boolean word;

        public Node(Character symbol) {
            //Each node contains a symbol
            this.symbol = symbol;
            //Make an ordered array of 26 children (initially empty)
            children = new Node[26];
            //Default is that the node doesn't make a word
            word = false;
        }

        /**
         * Helper method to return a list of the existing children
         * @return          the node's children
         */
        private ArrayList<Node> getChildren() {
            ArrayList<Node> out = new ArrayList<>();
            for (Node child : children) {
                if (child != null) {
                    out.add(child);
                }
            }
            return out;
        }
    }

    //Instance data
    private Node root;
    private int size;
    private ArrayList<String> words;
    private LinkedList<Character> traversed;

    public SimpleTrie() {
        //Instantiates root with a non-character value
        root = new Node('-');
        size = 0;
    }

    /**
     * helper method that takes a character and returns the index it should be at.
     * For example, a -> 0   b -> 1  c -> 2  ... z -> 25
     * <P>
     * Uses unicode method Unicode bounds:
     * a = 0010
     * z = 0035
     * </P>
     */
    private int getInd(Character c) {
        int i = Character.getNumericValue(c);
        return i - 10;
    }

    /**
     * Adds the specified word into the trie.  Doesn't add duplicates.
     *
     * @param word          the word to add
     * @return              <tt>true</tt> if the word has been added, <tt>false</tt> otherwise
     */
    public Boolean add(String word) {
        //Search for the word to be added until the child node is null
        String normWord = word.toLowerCase();
        Node curNode = root;
        //Iterate over the word to be added
        for (int i = 0; i < normWord.length(); i++) {
            int ind = getInd(normWord.charAt(i));
            //If the word passed in is not in regular alphabet, terminate
            if (ind < 0 || ind > 25) {
                return false;
            }
            if (curNode.children[ind] != null) {
                //Found another symbol, keep searching
                curNode = curNode.children[ind];
            } else if (curNode.children[ind] == null && i < normWord.length() - 1){
                //If the next node is null and more letters to add, add the char in and change pointers
                curNode.children[ind] = new Node(normWord.charAt(i));
                curNode = curNode.children[ind];
            } else {
                //Else the next node is null and no more letters to add
                curNode.children[ind] = new Node(normWord.charAt(i));
                //Make that node word marker true
                curNode.children[ind].word = true;
                //Increment size
                size++;
                //Now return true since the last character has been added
                return true;
            }
        }
        //Otherwise nothing has been added or adding duplicate
        return false;
    }

    /**
     * Checks if the sequence of characters is a prefix of one or more words.
     * @param seq               the character sequence to check if prefix
     * @return                  <tt>true</tt> if the sequence is a prefix, <tt>false</tt> otherwise
     */
    public Boolean isPrefix(String seq) {
        String normSeq = seq.toLowerCase();
        Node curNode = root;
        //Iterate over sequence of chars
        for (int i = 0; i < normSeq.length(); i++) {
            //Traverse trie, if null reference the seq is not a prefix
            int ind = getInd(normSeq.charAt(i));
            //If the word passed in is not in regular alphabet, terminate
            if (ind < 0 || ind > 25) {
                return false;
            }
            if (curNode.children[ind] == null) {
                return false;
            } else {
                curNode = curNode.children[ind];
            }
        }
        //True if trie is traversed without finding an end
        return true;
    }

    /**
     * Checks if the trie contains a specific word.
     * @param word                  the target word to search for
     * @return                      <tt>true</tt> if the trie contains the word, <tt>false</tt> otherwise
     */
    public Boolean contains(String word) {
        String normWord = word.toLowerCase();
        Node curNode = root;
        //Iterate over sequence of chars
        for (int i = 0; i < normWord.length(); i++) {
            //Traverse trie starting at root, check the last node's word marker
            int ind = getInd(normWord.charAt(i));
            //If the word passed in is not in regular alphabet, terminate
            if (ind < 0 || ind > 25) {
                return false;
            }
            //If node is null, trie doesn't contain word
            if (curNode.children[ind] == null) {
                return false;
            //If at the words last char, check marker
            } else if (i == normWord.length() - 1) {
                return curNode.children[ind].word;
            //Otherwise traverse trie
            } else {
                curNode = curNode.children[ind];
            }
        }
        //Trie doesn't contain word if not found in trie or the last marker is false
        return false;
    }

    /**
     * Returns the number of words that can be spelled with the given prefix.
     * @param prefix            the prefix to start with
     * @return                  the number of words spelled
     */
    public int count(String prefix) {
        int count = 0;
        String normed = prefix.toLowerCase();
        Node curNode = root;
        //Traverse trie until end of prefix
        for (int i = 0; i < normed.length(); i++) {
            int ind = getInd(normed.charAt(i));
            if (curNode.children[ind] != null) {
                curNode = curNode.children[ind];
            }
        }
        //curNode will point to node of last char in prefix
        //If the curNode is not the last character, then the prefix doesn't have any words
        if (curNode.symbol != normed.charAt(normed.length() - 1)) {
            return count;
        }
        //Once trie traversed to end of prefix, perform DFS and count any nodes with marker equal to true
        Stack<Node> children = new Stack<Node>();
        children.add(curNode);
        while (children.size() > 0) {
            //Take node off and evaluate
            Node n = children.pop();
            //Iterate over all children
            for (int i = 0; i < 26; i++) {
                Node child = n.children[i];
                //Go through all children that aren't null
                if (child != null) {
                    children.add(child);
                    //Increment count if a word
                    if (child.word) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Returns the number of words contained in the trie.
     * @return       number of words
     */
    public int size() {
        return size;
    }

    /**
     * Returns an {@link ArrayList} of words (maximum of 10) that can be spelled with the given prefix.
     * Returns the words in alphabetical order.
     * @param prefix            the prefix to start with
     * @return                  possible words spelled
     */
    public ArrayList<String> words(String prefix) {
        String normedPrefix = prefix.toLowerCase();
        Node curNode = root;
        //Traverse trie until end of prefix
        for (int i = 0; i < normedPrefix.length(); i++) {
            int ind = getInd(normedPrefix.charAt(i));
            if (curNode.children[ind] != null) {
                curNode = curNode.children[ind];
            }
        }
        //Cut prefix down by 1 character since that last character will be added by helper function
        String pre = normedPrefix.substring(0, normedPrefix.length() - 1);
        //curNode will point to node of last char in prefix
        //Use recursive helper DFS function to get words by passing in curNode
        words = new ArrayList<>(10);
        traversed = new LinkedList<>();
        recur(curNode, pre);
        return words;
    }

    /**
     * Helper method that converts the {@link LinkedList} of traversed letters and the prefix into a word.
     * @param chars             {@link LinkedList} structure containing characters
     * @return                  word string
     */
    private String compress(String prefix, LinkedList<Character> chars) {
        StringBuilder out = new StringBuilder(prefix);
        for (int i = 0; i < chars.size(); i++) {
            out.append(chars.get(i));
        }
        return out.toString();
    }

    /**
     * Recursive helper function to perform DFS and add words.
     * @param curNode       the starting node
     * @param prefix        the prefix to add during compression
     */
    private void recur(Node curNode, String prefix) {
        //Add the letter to the traversed list
        traversed.addLast(curNode.symbol);
        ArrayList<Node> children = curNode.getChildren();
        //Base case: no children
        if (children.isEmpty()) {
            //Check if the letter forms a word
            if (curNode.word && words.size() < 10) {
                words.add(compress(prefix, traversed));
            }
        //Recursive case: 1 or more children
        } else {
            //Check if the letter forms a word
            if (curNode.word && words.size() < 10) {
                words.add(compress(prefix, traversed));
            }
            for (Node child : children) {
                recur(child, prefix);
                traversed.removeLast();
            }
        }
    }
}
