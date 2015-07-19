import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Graham McAllister
 * @version 1.0
 *
 * My implementation of a trie specialized for use to hold entry Titles.
 * Titles may be more than one expression, so the compression that Patricia Tries offers will minimize memory usage.
 * The trie compresses any nodes with only one child.
 * <p>
 *     Valid texts include [a-zA-Z0-9\s]+
 * </p>
 *
 */
public class PatriciaTrie implements Trie {

    private class Node {

        //Instance data
        private String text;
        private Node[] children;
        private boolean expression;

        public Node(String text) {
            //Each node contains a string of one or more characters (numeric or alphabetic)
            this.text = text;
            //Make an ordered array of 37 children possibilities (initially empty)
                //Indexes:
                //[a-zA-Z] = 0-25
                //[0-9] = 26-35
                //whitespace = 36
            children = new Node[37];
            //Default is that the node doesn't make a expression
            expression = false;
        }

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
    private ArrayList<String> expressions;
    private LinkedList<String> traversed;

    public PatriciaTrie() {
        //Instantiates root with a non-character value
        root = new Node("-");
        size = 0;
    }

    /**
     * Adds the specified expression into the trie.  Doesn't add duplicates.
     *
     * @param expression          the expression to add
     * @return              <tt>true</tt> if the expression has been added, <tt>false</tt> otherwise
     */
    public Boolean add(String expression) {
        //Search for the word to be added until the child node is null
        char[] normExp = expression.toLowerCase().toCharArray();
        Node curNode = root;
        //Iterate over the word to be added
        for (int i = 0; i < normExp.length; i++) {
            int ind = getInd(normExp[i]);
            Node n = curNode.children[ind];
            //If the word passed in is not in bounded alphabet, terminate
            if (ind < 0 || ind > 36) {
                return false;
            }
            //If the child node is null, add the rest of the expression
            if (n == null) {
                //Build a string with the rest of normExp characters
                String compressed = getCompressed(normExp, i);
                //Create new node
                Node newNode = new Node(compressed);
                newNode.expression = true;
                //Add new node to parent's children
                curNode.children[ind] = newNode;
                size++;
                return true;
            //If letters match and node has only one char, check next node
            } else if (n.text.length() == 1) {
                curNode = n;
            //If the first letter matches but the node has a compressed string
            } else if (n.text.length() > 1) {
                //If the found node is compressed need to iterate over the remaining letters
                String nodeExp = n.text;
                //Start at the second character in node expression and normExp since we know the first matches
                for (int j = i + 1, nodeInd = 1; j < normExp.length; j++, nodeInd++) {
                    //Four cases:
                    if (nodeInd == nodeExp.length()) {
                        //1) If the nodeExp runs out of characters: create a new node as child of n
                        //Get index to put the compressed expression
                        int childInd = getInd(normExp[j]);
                        //Build a string with the rest of normExp characters
                        StringBuilder compressed = new StringBuilder();
                        for (int k = j; k < normExp.length; k++) {
                            compressed.append(normExp[k]);
                        }
                        Node newNode = new Node(compressed.toString());
                        newNode.expression = true;
                        n.children[childInd] = newNode;
                        size++;
                        return true;
                    } else if (normExp[j] != nodeExp.charAt(nodeInd)) {
                    //2) If the characters don't match: split the node
                        //Get index and expression for nodeExp
                        int node1Ind = getInd(nodeExp.charAt(nodeInd));
                        String node1Exp = nodeExp.substring(nodeInd);
                        //Make new node
                        Node newNode1 = new Node(node1Exp);
                        //Do the above for normExp
                        int node2Ind = getInd(normExp[j]);
                        String node2Exp = getCompressed(normExp, j);
                        Node newNode2 = new Node(node2Exp);
                        //Alter the parent node
                        n.expression = false;
                        n.text = nodeExp.substring(0, j);
                        //Change pointers
                        n.children[node1Ind] = newNode1;
                        newNode1.expression = true;
                        n.children[node2Ind] = newNode2;
                        newNode2.expression = true;
                        size++;
                        return true;
                    }
                    //3) If the characters match: keep iterating
                }
                //4) If the normExp runs out of characters: terminate expression, mark true,
                //          add a new node with rest of letters
                //          (normExp is a prefix of nodeExp)
                String nextExp = nodeExp.substring(normExp.length);
                int nextInd = getInd(nextExp.charAt(0));
                Node nextNode = new Node(nextExp);
                nextNode.expression = true;
                //Set the parent node to compressed normExp
                n.text = getCompressed(normExp, 0);
                n.expression = true;
                n.children[nextInd] = nextNode;
                size++;
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
        seq = seq.toLowerCase();
        char lastChar = seq.charAt(seq.length() - 1);
        Node n = getCur(seq);
        //If n is null return false
        if (n == null) {
            return false;
        }
        ArrayList<Node> children = n.getChildren();
        //If the last letter of seq and n.text don't match, check n.expression or children
        if (n.text.charAt(n.text.length() - 1) != lastChar) {
            return n.expression || !(children.isEmpty());
        }
        //Otherwise return true if node has children
        return !children.isEmpty();
    }

    public ArrayList<String> expressions(String prefix) {
        String normedPrefix = prefix.toLowerCase();
        Node curNode = getCur(prefix);
        //If there is
        //Remove the curNode from traversed list because it will be added in recur
        traversed.removeLast();
        //curNode will point to node of last char in prefix
        //Use recursive helper DFS function to get words by passing in curNode
        recur(curNode);
        return expressions;
    }

    /**
     * Checks if the trie contains a specific expression.
     * @param expression                 the target expression to search for
     * @return                      <tt>true</tt> if the trie contains the expression, <tt>false</tt> otherwise
     */
    public Boolean contains(String expression) {
        expression = expression.toLowerCase();
        Node curNode = root;
        int i = 0;
        while (i < expression.length()) {
            char letter = expression.charAt(i);
            int childInd = getInd(letter);
            Node n = curNode.children[childInd];
            //If the text is a single character, go to the next node
            if (n != null && n.text.length() == 1) {
                //If on the last letter of expression
                if (i == expression.length() - 1) {
                    return n.expression;
                }
                //Otherwise keep searching
                curNode = n;
                //If the text is more than a single character, check the end of n.text
            } else if (n != null && n.text.length() > 1) {
                //If the last letter of n.text matches expression
                int skippedInd = i + n.text.length() - 1;
                //If the last letter of expression matches the last letter in node, check if the node makes a word
                if (skippedInd == expression.length() - 1) {
                    return n.expression;
                    //If in expression bounds, can safely index
                } else if (skippedInd < expression.length()) {
                    if (n.text.charAt(n.text.length() - 1) == expression.charAt(skippedInd)) {
                        //keep checking using skippedInd - 1
                        //skippedInd - 1 because i will be incremented at the end of the loop
                        i = skippedInd;
                        //Otherwise keep searching
                        curNode = n;
                    }
                }
            }
            i++;
        }
        return false;
    }

    /**
     * Returns the number of words that can be spelled with the given prefix.
     * @param prefix            the prefix to start with
     * @return                  the number of words spelled
     */
    public int count(String prefix) {
        prefix = prefix.toLowerCase();
        int count = 0;
        //Get curNode to contain last letter in prefix
        Node curNode = getCur(prefix);
        //Perform BFS to search all nodes that are linked to curNode to count them
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.addLast(curNode);
        while (!nodes.isEmpty()) {
            Node cur = nodes.removeFirst();
            //Increment count if the node makes an expression
            if (cur.expression) {
                count++;
            }
            for (Node child: cur.children) {
                if (child != null) {
                    nodes.addLast(child);
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
     * helper method that takes a character and returns the index it should be at.
     * For example, a -> 0   b -> 1  c -> 2  ... z -> 25 A -> 0 Z -> 25
     * <P>
     * Uses unicode method Unicode bounds:
     * a = 0010
     * z = 0035
     * 0 = 0
     * 9 = 9
     * whitespace = -1 -> normalized to 36
     * </P>
     *
     * @param c            character whose index it will retrieve
     * @return             int index value
     */
    private int getInd(Character c) {
        //Turn char into index
        int out = Character.getNumericValue(c);
        //Normalize if character is whitespace
        if (out == -1) {
            out = 36;
        }
        return out;
    }

    /**
     * Helper method to compress a character array into string
     * @param letters           character array
     * @param startInd          the starting index to start compression
     * @return                  the string containing specified letters
     */
    private String getCompressed(char[] letters, int startInd) {
        StringBuilder sb = new StringBuilder();
        for (int i = startInd; i < letters.length; i++) {
            sb.append(letters[i]);
        }
        return sb.toString();
    }

    /**
     * Helper method that navigates to node corresponding to the last letter in prefix
     * @param prefix                prefix to use for searching
     * @return                      node which contains the last letter in prefix
     */
    private Node getCur(String prefix) {
        expressions = new ArrayList<>(10);
        traversed = new LinkedList<>();
        Node curNode = root;
        int i = 0;
        while (i < prefix.length()) {
            char letter = prefix.charAt(i);
            int childInd = getInd(letter);
            Node n = curNode.children[childInd];
            //If the text is a single character
            if (n != null && n.text.length() == 1) {
                //If on the last letter of prefix
                if (i == prefix.length() - 1) {
                    return n;
                }
                //Otherwise keep searching
                traversed.addLast(n.text);
                //If the text is more than a single character, check the end of n.text
            } else if (n != null && n.text.length() > 1) {
                //If the last letter of n.text matches prefix
                int skippedInd = i + n.text.length() - 1;
                //If the last letter of prefix matches the last letter in node, check if the node makes a word
                if (skippedInd == prefix.length() - 1) {
                    traversed.add(n.text);
                    return n;
                //If in prefix bounds, can safely index
                } else if (skippedInd < prefix.length()) {
                    if (n.text.charAt(n.text.length() - 1) == prefix.charAt(skippedInd)) {
                        //keep checking using skippedInd
                        i = skippedInd;
                        traversed.add(n.text);
                    }
                //If prefix lays somewhere in n.text
                } else {
                    traversed.addLast(n.text);
                    return n;
                }
            }
            curNode = n;
            i++;
        }
        return curNode;
    }

    /**
     * Recursive helper function to perform DFS and add words.
     * @param curNode       the starting node
     */
    private void recur(Node curNode) {
        //Add node to traversed list
        traversed.addLast(curNode.text);
        ArrayList<Node> children = curNode.getChildren();
        //Base case: no children
        if (children.isEmpty()) {
            //Check if the letter forms a word
            if (curNode.expression && expressions.size() < 10) {
                expressions.add(compress(traversed));
            }
            //Recursive case: 1 or more children
        } else {
            //Check if the letter forms a word
            if (curNode.expression && expressions.size() < 10) {
                expressions.add(compress(traversed));
            }
            for (Node child : children) {
                recur(child);
                traversed.removeLast();
            }
        }
    }

    /**
     * Helper method that converts the {@link LinkedList} of traversed letters and the prefix into a word.
     * @param pieces             {@link LinkedList} structure containing characters
     * @return                  word string
     */
    private String compress(LinkedList<String> pieces) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < pieces.size(); i++) {
            out.append(pieces.get(i));
        }
        return out.toString();
    }
}
