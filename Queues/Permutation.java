import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> deck = new RandomizedQueue<String>();
        int count = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            deck.enqueue(value);
        }

        Iterator<String> iter  = deck.iterator();
        for (int i = 0; i < count; i++)
        {
            if (iter.hasNext())
            {
                System.out.println(iter.next());
            }
        }
    }
}
