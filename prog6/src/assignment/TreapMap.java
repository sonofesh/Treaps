package assignment;

import java.util.ArrayList;
import java.util.Iterator;

public class TreapMap<K extends Comparable<K>, V> implements Treap {
    TreapNode head;
    ArrayList<K> nodes;
    boolean maxPriority = false;

    public TreapMap(TreapNode head) { this.head = head; }

    public TreapMap() { }

    public TreapNode getHead() {
        return head;
    }

    public void setHead(TreapNode head) {
        this.head = head;
    }

    /**
     * Retrieves the value associated with a key in this dictionary.
     * If the key is null or the key is not present in this
     * dictionary, this method returns null.
     *
     * @param key
     * @return      the value associated with the key
     */
    @Override
    public Object lookup(Comparable key) {
        TreapNode current = head;
        while (true) {
            if (current == null || current.getKey() == key)
                return current.getValue();
            else if (current.getKey().compareTo(key) > 0)
                current = current.getLeft();
            else if (current.getKey().compareTo(key) < 0)
                current = current.getRight();
        }
    }

    /**
     * Helper method that returns the TreapNode associated with the key
     *
     * @param key
     * @return      the node associated with the key
     */
    public Object lookupNode(Comparable key) {
        TreapNode current = head;
        while (true) {
            if (current == null || current.getKey() == key)
                return current;
            else if (current.getKey().compareTo(key) > 0)
                current = current.getLeft();
            else if (current.getKey().compareTo(key) < 0)
                current = current.getRight();
        }
    }

    @Override
    public void insert (Comparable key, Object value) {
        if (head == null)
            head = new TreapNode(key, value);
        if (key == null)
            return;
        else
            insertAndRotate(head, key, value);
    }

    /**
     * Helper method that builds the tree using BST and rotates whenever tree violates heap property
     * @param current
     * @param key
     * @return
     */
    private TreapNode insertAndRotate (TreapNode current, Comparable key, Object value) {
        //create a new node once a null node is reacher
        if (current == null) {
            if (maxPriority)
                return new TreapNode (key, value, MAX_PRIORITY);
            else
                return new TreapNode  (key, value);
        }

        if (key.compareTo(current.getKey()) < 0) {
            //insert in left subtree
            current.left = insertAndRotate(current.left, key, value);
            //checks and fixes heap property
            if ((current.getLeft().getPriority()).compareTo(current.getPriority()) > 0)
                current = rotateRight(current);
        }

        //if keys are equal replace current's value
        else if (key.compareTo(current.getKey()) == 0) {
            current.setValue(value);
            if (maxPriority)
                current.setPriority(MAX_PRIORITY);
        }

        else {
            current.right = insertAndRotate(current.getRight(), key, value);
            if ((current.getRight().getPriority()).compareTo(current.getPriority()) > 0)
                current = rotateLeft(current);
        }

        return current;
    }

    /**
     * Helper method that right rotates a node around a given head
     * @param root
     * @return
     */
    private TreapNode rotateRight (TreapNode root) {
        TreapNode newRoot = root.getLeft();
        if (newRoot == null) return null;
        TreapNode transfer = newRoot.getRight();
        //moves over the head
        if (root == head)
            head = newRoot;
        newRoot.setRight(root);
        root.setLeft(transfer);

        return newRoot;
    }

    /**
     * Helper method that left rotates a node around a given head
     * @param root
     * @return
     */
    private TreapNode rotateLeft (TreapNode root) {
        TreapNode newRoot = root.getRight();
        if (newRoot == null) return null;
        TreapNode transfer = newRoot.getLeft();
        //moves over the head
        if (root == head)
            head = newRoot;
        newRoot.setLeft(root);
        root.setRight(transfer);

        return newRoot;
    }

    /**
     * Removes a key from this dictionary.  If the key is not present
     * in this dictionary, this method does nothing.  Returns the
     * value associated with the removed key, or null if the key
     * is not present.
     *
     * @param key      the key to remove
     * @return         the associated with the removed key, or null
     */
    @Override
    public Object remove(Comparable key) {
        TreapNode removed = deleteNode(head, key);
        if (deleteNode(head, key) == null)
            return null;
        return removed;
    }

    TreapNode deleteNode(TreapNode head, Comparable key) {
        if (head == null)
            return null;

        if (key == null) {
            head.setRight(null);
            head.setLeft(null);
        }

        if (key.compareTo(head.getKey()) < 0)
            head.left = deleteNode(head.getLeft(), key);
        else if (key.compareTo(head.getKey()) > 0)
            head.right = deleteNode(head.getRight(), key);

        //Key is at head check if left or right is null first. If none are null compare values, and rotate the larger
        //of the two up to the head

        else if (head.getLeft() == null) {
            TreapNode current = head.getRight();
            head.setRight(null);
            head = current;
        }
        else if (head.getRight() == null) {
            TreapNode current = head.getLeft();
            head.setLeft(null);
            head = current;
        }

        //If left and right are not null
        else if (head.getLeft().getPriority().compareTo(head.getRight().getPriority()) < 0) {
            head = rotateLeft(head);
            head.setLeft(deleteNode(head.getLeft(), key));
        }
        else {
            head = rotateRight(head);
            head.setRight(deleteNode(head.getRight(), key));
        }

        return head;
    }

    @Override
    public Treap[] split(Comparable key) {
        maxPriority = true;
        TreapNode max = insertAndRotate(head, key, null);
        maxPriority = false;
        Treap[] treaps = {new TreapMap(max.getLeft()), new TreapMap(max.getRight())};
        return treaps;
    }

    @Override
    public void join(Treap t) {
        if (head == null || t == null)
            return;

        TreapNode x = new TreapNode(null, null, Integer.MIN_VALUE);
        Iterator<TreapNode> iterator = t.iterator();
        if (!iterator.hasNext())
            return;
        //get the first element in Treap t
        Comparable k = (Comparable) iterator.next();
        if (k.compareTo(head) > 0) {
            x.setRight((TreapNode) lookupNode(k));
            x.setLeft(head);
        }
        else {
            x.setLeft((TreapNode) lookupNode(k));
            x.setRight(head);
        }

        remove(null);
    }

    @Override
    public void meld(Treap t) throws UnsupportedOperationException {

    }

    @Override
    public void difference(Treap t) throws UnsupportedOperationException {

    }

    /**
     * @return a fresh iterator that points to the first element in
     * this Treap and iterates in sorted order.
     */
    @Override
    public Iterator iterator() {
        nodes = new ArrayList<>();
        inOrderKeys(head);
        return nodes.iterator();
    }

    /**
     * Build a human-readable version of the treap.
     * Each node in the treap will be represented as
     *
     *     [priority] <key, value>\n
     *
     * Subtreaps are indented one tab over from their parent for
     * printing.  This method prints out the string representations
     * of key and value using the object's toString(). Treaps should
     * be printed in pre-order traversal fashion.
     */
    @Override
    public String toString() {
        String output = "";
        return toString(head, 0);
    }

    private String toString(TreapNode n, int indents) {
        StringBuilder output = new StringBuilder();
        if (n == null) {
            return output.toString();
        }

        for (int i = 0; i < indents; i++) {
            output.append("\t");
        }

        output.append("[").append(n.getPriority().toString()).append("]").append("<").append(n.getKey().toString()).append(", ").append(n.getValue().toString()).append(">").append("\n");
        indents += 1;
        output.append(toString(n.left, indents));
        output.append(toString(n.right, indents));
        
        return output.toString();
    }

    public void inOrderKeys(TreapNode n) {
        if (n == null)
            return;
        inOrderKeys(n.left);
        System.out.print(nodes.add((K) n.getKey()));
        inOrderKeys(n.right);
    }

    /* Print nodes at a given level */
    void printGivenLevel(TreapNode head, int level)
    {
        if (head == null)
            return;
        if (level == 1)
            System.out.print(head.getKey() + " ");
        else if (level > 1)
        {
            printGivenLevel(head.left, level-1);
            printGivenLevel(head.right, level-1);
        }
    }

    private int getHeight(TreapNode head) {
        if (head == null)
            return 0;
        else {
            int left = getHeight(head.getLeft());
            int right = getHeight(head.getRight());

            if (left > right)
                return left + 1;
            else
                return right + 1;
        }
    }

    private int countNodes (TreapNode n) {
        if (n == null)
            return 0;
        return countNodes(n.left) + 1 + countNodes(n.right);
    }

    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        double minimum = 0;
        double count = countNodes(head);
        while (Math.pow(minimum, 2) < count)
            minimum++;
        return getHeight(head)/minimum;
    }

    protected class TreapNode <K extends Comparable<K>, V> {
        K key;
        V value;
        Integer priority;
        TreapNode left;
        TreapNode right;

        public TreapNode (K key, V value) {
            this.key = key;
            this.value = value;
            priority = (int) (Math.random() * 60000); //all ints from 0-65534
        }

        public TreapNode (K key, V value, TreapNode right, TreapNode left) {
            this.key = key;
            this.value = value;
            priority = (int) (Math.random() * 60000);
            this.left = left;
            this.right = right;
        }

        public TreapNode (K key, V value, Integer priority) {
            this.key = key;
            this.value = value;
            this.priority = priority;
        }

        //GETTERS AND SETTERS
        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public TreapNode getLeft() {
            return left;
        }

        public void setLeft(TreapNode left) {
            this.left = left;
        }

        public TreapNode getRight() {
            return right;
        }

        public void setRight(TreapNode right) {
            this.right = right;
        }
    }

    public static void main (String[] args) {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");

        System.out.print(tree.countNodes(tree.head));
        System.out.print(tree.getHeight(tree.head));
        System.out.print("Balance" + "\n");
        System.out.print(tree.balanceFactor());

        System.out.print("\n");
        System.out.print(tree.toString());
        System.out.print("\n");

        tree.remove(6);
        System.out.print("Remove" + "\n");
        System.out.print(tree.toString());

        System.out.print("Split" + "\n");
        Treap[] treaps = tree.split(3);
        System.out.print("1" + "\n");
        System.out.print(treaps[0].toString());
        System.out.print("2" + "\n");
        System.out.print(treaps[1].toString());
    }
}
