import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int count;

    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }

    public Deque()                           // construct an empty deque
    {
        count = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return (count == 0);
    }

    public int size()                        // return the number of items on the deque
    {
        return count;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        if (count == 0)
        {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            last = first;
        }
        else
        {
            Node nextNode = first;

            first = new Node();
            first.item = item;
            first.next = nextNode;
            first.prev = null;

            nextNode.prev = first;
        }

        count++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        if (count == 0)
        {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            last = first;
        }
        else
        {
            Node prevNode = last;

            last = new Node();
            last.item = item;
            last.prev = prevNode;
            last.next = null;

            prevNode.next = last;
        }

        count++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (count == 0) throw new java.util.NoSuchElementException();

        Item item = first.item;
        if (count > 1) first = first.next;
        first.prev = null;
        if (count == 1)
        {
            last = null;
            first = null;
        }
        count--;

        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (count == 0) throw new java.util.NoSuchElementException();

        Item item = last.item;
        if (count > 1) last = last.prev;
        last.next = null;
        if (count == 1)
        {
            last = null;
            first = null;
        }
        count--;

        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
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
            if (!hasNext()) throw new java.util.NoSuchElementException();
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
        // final int testCount = 10;
        //
        // Deque<Integer> deck = new Deque<Integer>();
        //
        // System.out.println("The deck is empty: " + deck.isEmpty());
        // System.out.println("Count is: " + deck.size());
        //
        // for (int i = 0; i < testCount; i++)
        // {
        //     deck.addLast(i);
        // }
        //
        // System.out.println("Count is: " + deck.size() + " should be " + testCount);
        // System.out.println("The deck is NOT empty: " + !deck.isEmpty());
        //
        // deck.removeLast();
        // deck.removeFirst();
        //
        // System.out.println("Count after removing the first and last: " + deck.size() + " should be " + (testCount - 2));
        //
        // Iterator<Integer> i  = deck.iterator();
        // while (i.hasNext()) {
        //     System.out.println(i.next());
        // }
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.removeFirst();
        deque.removeFirst();

         Iterator<Integer> i  = deque.iterator();
         while (i.hasNext()) {
             System.out.println(i.next());
         }
    }
}