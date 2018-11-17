package Testing;

import assignment.Treap;
import assignment.TreapMap;
import org.junit.Test;

import java.util.Iterator;

public class TreapTesting {

    @Test
    public void testInsert() {

    }

    @Test
    public void testSplit() {
        TreapMap<Integer, String> tree = new TreapMap();
        tree.insert(6, "A");
        tree.insert(5, "B");
        tree.insert(7, "A");
        tree.insert(3, "E");
        tree.insert(8, "F");
        Treap[] treaps = tree.split(6);
        Iterator i = treaps[0].iterator();
        while (i.hasNext()) {
            System.out.print(i.next() + " ");
        }
        System.out.print("\nRight Tree \n");
        Iterator in = treaps[0].iterator();
        while (in.hasNext()) {
            System.out.print(in.next() + " ");
        }
    }

}
