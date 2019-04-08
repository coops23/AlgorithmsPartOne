import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first, last;
    private int count;

    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        count = 0;
        first = null;
        last = null;
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
        Node prevNode = last;

        last = new Node();
        last.item = item;
        last.prev = prevNode;
        last.next = null;

        prevNode.next = last;
    }

    public Item dequeue()                    // remove and return a random item
    {
        int pos = StdRandom.uniform(0, count - 1);

        Iterator<Item> iterator  = this.iterator();
        for(int i = 0; i < pos; i++)
        {
            iterator.next();
        }

        Item item = iterator.next();
    }

    public Item sample()                     // return a random item (but do not remove it)
    {

    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        public Item next()
        {
            if(!hasNext()) throw new java.util.NoSuchElementException();
            else
            {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {

    }
}
