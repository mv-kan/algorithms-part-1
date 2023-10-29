import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int start;
    private int end;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }
    private void allocDoubleSize() {
        Item[] newItems = (Item[])new Object[items.length * 2];
        System.arraycopy(items, start, newItems, 0, n);
        start = 0;
        end = n;
        items = newItems;
    }
    private void allocHalfSize() {
        Item[] newItems = (Item[])new Object[items.length / 2];
        System.arraycopy(items, start, newItems, 0, n);
        start = 0;
        end = n;
        items = newItems;
    }
    private void fragmentate() {
        System.arraycopy(items, start, items, 0, n);
        start = 0;
        end = n;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (items.length <= end && n < items.length / 4) {
            fragmentate();
        }
        if (items.length <= end) {
            allocDoubleSize();
        }
        items[end] = item;
        end++;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniformInt(0, n);
        // shuffle
        Item item = items[start + randIndex];
        items[start + randIndex] = items[start];
        items[start] = null;
        n--;
        start++;

        if (start > end) {
            start = 0;
            end = 0;
        }
        if (size() < items.length / 4) {
            allocHalfSize();
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        int rand = StdRandom.uniformInt(0, n);
        return items[start + rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        Item[] randArr = (Item[]) new Object[n];
        System.arraycopy(items, start, randArr, 0, n);
        // shuffle
        for (int i = 0; i < n; i++) {
            int rand1 = StdRandom.uniformInt(0, n);
            int rand2 = StdRandom.uniformInt(0, n);
            Item tmp = randArr[rand1];
            randArr[rand1] = randArr[rand2];
            randArr[rand2] = tmp;
        }
        return new RandomizedQueueIterator(randArr);
    }

    private void printStatus() {
        System.out.println("IsEmpty: " + isEmpty());
        System.out.println("Size: " + size());
    }
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.printStatus();
        rq.enqueue(0);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.printStatus();

        for (int i : rq) {
            System.out.println(i + "  ");
        }
        System.out.println();
        rq.printStatus();
        System.out.println();

        rq.dequeue();
        rq.dequeue();

        for (int i : rq) {
            System.out.println(i + "  ");
        }
        System.out.println();
        rq.printStatus();
        System.out.println();

        rq.enqueue(4);
        rq.enqueue(5);

        for (int i : rq) {
            System.out.println(i + "  ");
        }
        System.out.println();
        rq.printStatus();
        System.out.println();
        System.out.println("sample: " + rq.sample());

        System.out.println("deq: " + rq.dequeue());
        System.out.println("deq: " + rq.dequeue());
        System.out.println("deq: " + rq.dequeue());
        System.out.println("deq: " + rq.dequeue());
        rq.printStatus();

        for (int i : rq) {
            System.out.println(i + "  ");
        }
        System.out.println();
        rq.printStatus();
        System.out.println();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] items;
        int p;
        public RandomizedQueueIterator(Item[] items) {
            this.items = items;
        }

        public boolean hasNext() {
            return p < items.length;
        }

        public Item next() {
            return items[p++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
