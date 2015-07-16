import java.util.ArrayList;

/**
 * @author Graham McAllister
 * @version 1.0
 *
 * My implementation of a trie specialized for use to hold entry Titles.
 * Titles may be more than one expression, so the compression that Patricia Tries offers will minimize memory usage.
 * The trie compresses any nodes with only one child.
 * <p>
 *     Valid texts include [a-zA-Z0-9]+
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
            //Make an ordered array of 62 children possibilities (initially empty)
                //[a-z] = 26
                //[A-Z] = 26
                //[0-9] = 10
            children = new Node[62];
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

    public Boolean add(String expression) {
        //TODO
        return true;
    }

    public Boolean isPrefix(String seq) {
        //TODO
        return false;
    }

    public Boolean contains(String expression) {
        //TODO
        return false;
    }

    public int count(String expression) {
        //TODO
        return 0;
    }

    public int size() {
        //TODO
        return 0;
    }


}
