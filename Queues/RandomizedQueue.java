import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;
    private Node first;

    private class Node {
        Item item;
        Node next;
    }

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        count = 0;
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return (count == 0);
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return count;
    }

    public void enqueue(Item item)           // add the item
    {
        if(item == null) throw new java.lang.IllegalArgumentException();

        //Basically inserting an element on the front
        if (count == 0) {
            Node newNode = new Node();

            newNode.item = item;
            newNode.next = null;
            first = newNode;
        }
        else {
            Node newNext = first;

            first = new Node();
            first.item = item;
            first.next = newNext;
        }

        count++;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if(count == 0) throw new java.util.NoSuchElementException();

        //Would normally just pop the front element but here we need to randomize things
        int indexToRemove = StdRandom.uniform(0, count - 1);

        int i = 0;
        Node curr = first;
        Node prev = curr;
        while (i < indexToRemove) {
            prev = curr;
            curr = curr.next;
            i++;
        }

        prev.next = curr.next;
        count--;

        return curr.item;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if(count == 0) throw new java.util.NoSuchElementException();

        int indexToGet = StdRandom.uniform(0, count - 1);

        int i = 0;
        Node curr = first;
        while (i < indexToGet) {
            curr = curr.next;
            i++;
        }

        return curr.item;
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new ListRandomIterator(count);
    }

    private class ListRandomIterator implements Iterator<Item> {
        private int currIndex;
        private int order[];

        public ListRandomIterator(int count)
        {
            order = new int[count];

            for(int i = 0; i < count; i++)
            {
                order[i] = i;
            }
            StdRandom.shuffle(order);
            currIndex = 0;
        }

        public boolean hasNext()
        {
            return (currIndex < count);
        }

        public void remove() { throw new java.lang.UnsupportedOperationException(); }

        public Item next() {
            Node node = first;
            int i = 0;
            int index = order[currIndex];
            while(i != index)
            {
                node = node.next;
                i++;
            }

            currIndex++;

            return node.item;
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        final int testCount = 10;

        RandomizedQueue<Integer> deck = new RandomizedQueue<Integer>();

        System.out.println("The deck is empty: " + deck.isEmpty());
        System.out.println("Count is: " + deck.size());

        for(int i = 0; i < testCount; i++){
            deck.enqueue(i);
        }

        System.out.println("Count is: " + deck.size() + " should be " + testCount);
        System.out.println("The deck is NOT empty: " + !deck.isEmpty());

        deck.dequeue();
        deck.dequeue();

        System.out.println("Count after removing two: " + deck.size() + " should be " + (testCount - 2));

        Iterator<Integer> i  = deck.iterator();
        while(i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
