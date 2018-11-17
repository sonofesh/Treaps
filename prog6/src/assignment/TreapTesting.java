package assignment;

import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TreapTesting {

    /**
     * Basic sanity test for insert. Adds five values and makes sure they are returned in sorted order by the iterator
     */
    @Test
    public void testInsert() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");

        String expectedResult = "35678";
        String result = "";
        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            result += iterator.next().toString();
        }
        assertEquals(expectedResult, result);
    }

    /**
     * Tests that the iterator works if the tree is empty
     */
    @Test
    public void testIteratorNull () {
        TreapMap<Integer, String> tree = new TreapMap();
        String expectedResult = null;
        String result = null;
        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            result += iterator.next();
        }
        assertEquals(expectedResult, result);
    }

    /**
     * Basic sanity test for insert. Adds five non-integer values and makes sure they are returned in sorted order by the iterator
     */
    @Test
    public void testInsertBSTDouble() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6.6, "A");
        tree.insert(5.4, "B");
        tree.insert(7.3, "A");
        tree.insert(3.3, "E");
        tree.insert(2.8, "F");

        String expectedResult = "2.83.35.46.67.3";
        String result = "";

        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            result += iterator.next().toString();
        }
        assertEquals(expectedResult, result);
    }

    /**
     * Not an actual test, but just a basic sanity test to see that their toString() returns valid outputs and
     */
    @Test
    public void testToString () {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");
        int expectedLines = 10; //five lines, two objects on each line

        int actualLines = 0;
        String s = tree.toString();
        Scanner scan = new Scanner(s);
        while (scan.hasNext()) {
            actualLines++;
            scan.next();
        }
        assertEquals(expectedLines, actualLines);
    }

    /**
     * Tests to see that the created tree maintains the heap property through parsing the toString()
     * A randonly generated treap of between 1 to 1000 nodes was fed in
     */
    @Test
    public void testRotations() {
        TreapMap<Integer, String> tree = generateRandomTree();

        Scanner scanner = new Scanner(tree.toString());
        boolean isOrdered = true;
        //counts the number of indends to see what level of the tree we're on
        int tabCount = 0;
        String line = scanner.nextLine();
        Integer last = Integer.parseInt(line.substring(line.indexOf('[') + 1, line.indexOf(']')));
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            int i = 0;
            int tempCount = 0;
            while (line.charAt(i) != '[') {
                tempCount++;
                i++;
            }
            String num = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
            int current = Integer.parseInt(num);
            if (tempCount > tabCount) {
                if (current > last)
                    isOrdered = false;
            }
            last = current;
        }
    }

    /**
     * Basic sanity check that determines whether programmer could remove just one value
     */
    @Test
    public void testRemove() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");

        tree.remove(8);
        tree.remove(2);

        String expectedResult = "3567";
        String result = "";
        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            result += iterator.next().toString();
        }
        assertEquals(expectedResult, result);
    }

    /**
     * Tests remove to see if it doesn't crash if we try to remove a value when head is null
     */
    @Test
    public void testRemoveNull() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.remove(5);
        assertTrue(true);
    }

    /**
     * Basic sanity check that determines whether program didn't crash if a non-existing value was passed into remove
     */
    @Test
    public void testRemoveNonExistant() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");

        tree.remove(8);
        tree.remove(2);

        String expectedResult = "3567";
        String result = "";
        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            result += iterator.next().toString();
        }
        assertEquals(expectedResult, result);
    }

    /**
     * Basic sanity check that determines whether program didn't crash if the head was removed
     */
    @Test
    public void testRemoveHead() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");

        tree.remove(6);

        String expectedResult = "3578";
        String result = "";
        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            result += iterator.next().toString();
        }
        assertEquals(expectedResult, result);
    }

    /**
     * Tests whether heap property is maintained after removal
     */
    @Test
    public void testRemoveRotations() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");

        tree.remove(6);

        Scanner scanner = new Scanner(tree.toString());
        boolean isOrdered = true;
        //counts the number of indends to see what level of the tree we're on
        int tabCount = 0;
        String line = scanner.nextLine();
        Integer last = Integer.parseInt(line.substring(line.indexOf('[') + 1, line.indexOf(']')));
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            int i = 0;
            int tempCount = 0;
            while (line.charAt(i) != '[') {
                tempCount++;
                i++;
            }
            String num = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
            int current = Integer.parseInt(num);
            if (tempCount > tabCount) {
                if (current > last) {
                    isOrdered = false;
                    break;
                }
            }
            last = current;

            assertTrue(isOrdered);
        }
    }


    private String generateIterString (TreapMap tree) {
        Iterator iterator = tree.iterator();
        String result = "";
        while (iterator.hasNext()) {
            result += iterator.next().toString();
            result += " ";
        }
        return result;
    }

    /**
     * Tests full capability of remove by inserting a 100 values and randomly removing them one by one
     */
    @Test
    public void testFullRemove() {
        TreapMap<Integer, String> tree = new TreapMap<>();

        for (int i = 0; i < 100; i++)
            tree.insert(i, "A");

        Random rand = new Random();

        boolean valid = true;
        for (int i = 0; i < 100; i++) {
            tree.remove(i);
            if (generateIterString(tree).contains(Integer.toString(i))) {
                valid = false;
                break;
            }

        }

        assertTrue(valid);
    }

    /**
     * Tests the split method by feeding a large Treap and separating it along some randomly generated value
     */
    @Test
    public void testSplit() {
        TreapMap<Integer, String> tree = generateRandomTree();
        int split = (int) (Math.random() * 1000);
        Treap[] treaps = tree.split(split);
        boolean valid = true;
        Iterator iter0 = treaps[0].iterator();
        Iterator iter1 = treaps[1].iterator();
        Comparable last = (Comparable) iter0.next();
        while (iter0.hasNext()) {
            Comparable current = (Comparable) iter0.next();
            if (current.compareTo(last) < 0) {
                valid = false;
                break;
            }
            if (current.compareTo(split) > 0) {
                valid = false;
                break;
            }
        }

        while (iter1.hasNext()) {
            Comparable current = (Comparable) iter1.next();
            if (current.compareTo(last) < 0) {
                valid = false;
                break;
            }
            if (current.compareTo(split) < 0) {
                valid = false;
                break;
            }
        }
        assertTrue(valid);
    }

    public TreapMap generateRandomTree() {
        TreapMap tree = new TreapMap();
        int num = (int) (Math.random() * 1000) + 1;
        for (int i = 0; i < num; i++) {
            tree.insert((int) (Math.random() * 1000) + 1, Integer.toString(i));
        }

        return tree;
    }

    /**
     * Tests that join creates a convergent tree
     */
    @Test
    public void testJoin () {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");

        TreapMap<Integer, String> tree2 = new TreapMap();
        tree.insert(-6, "A");
        tree.insert(-5, "B");
        tree.insert(-7, "A");
        tree.insert(-3, "E");
        tree.insert(-8, "F");

        tree.join(tree2);

        String expectedResult = "-8-7-6-5-335678";
        String result = "";
        Iterator iterator = tree.iterator();
        while (iterator.hasNext()) {
            result += iterator.next().toString();
        }
        assertEquals(expectedResult, result);
    }

    @Test
    public void tests() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");
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
