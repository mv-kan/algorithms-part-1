import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private final List<Item> l;

    // construct an empty deque
    public Deque() {
        l = new List<Item>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return l.size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return l.size();
    }

    // add the item to the front
    public void addFirst(Item item) {
        l.addBegin(item);
    }

    // add the item to the back
    public void addLast(Item item) {
        l.addEnd(item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        return l.removeFirst();
    }

    // remove and return the item from the back
    public Item removeLast() {
        return l.removeLast();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(l.head);
    }
    private void printStatus() {
        System.out.println("IsEmpty: " + isEmpty());
        System.out.println("Size: " + size());
    }
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        System.out.println(deque.removeLast());
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addFirst(7);
        System.out.println(deque.removeLast());
////        Deque<Integer> deque = new Deque<>();
////        deque.addFirst(1);
////        deque.removeLast() ;
////        deque.isEmpty()   ;
////        deque.addFirst(4);
////        deque.addFirst(5);
////        deque.removeLast();
//        Deque<Integer> deck = new Deque<>();
//
//        deck.printStatus();
//
//        deck.addFirst(1);
//        deck.addLast(2);
//        deck.addFirst(0);
//        deck.addLast(3);
//
//        deck.printStatus();
//
//        for (Integer i : deck) {
//            System.out.print(i + "  ");
//        }
//        System.out.println();
//        deck.printStatus();
//        System.out.println();
//
//        deck.removeFirst();
//        deck.removeLast();
//
//        for (Integer i : deck) {
//            System.out.print(i + "  ");
//        }
//        System.out.println();
//        deck.printStatus();
//        System.out.println();
//
//        deck.removeFirst();
//        deck.removeLast();
//
//        for (Integer i : deck) {
//            System.out.print(i + "  ");
//        }
//        System.out.println();
//        deck.printStatus();
//        System.out.println();
    }

    private static class ListIterator<Item> implements Iterator<Item> {
        Node<Item> head;
        public ListIterator(Node<Item> head) {
            this.head = head;
        }

        public boolean hasNext() {
            return head != null;
        }

        public Item next() {
            if (head == null) {
                throw new NoSuchElementException();
            }
            Item item = head.item;
            head = head.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private static class List<Item> {
        public Node<Item> head;
        private int n;
        public int size() {
            return n;
        }

        public void addBegin(Item item) {
            if (item == null){
                throw new IllegalArgumentException();
            }
            Node<Item> newHead = new Node<>(item);
            newHead.next = head;
            head = newHead;
            n++;
        }

        public void addEnd(Item item) {
            if (item == null){
                throw new IllegalArgumentException();
            }
            n++;
            if (head == null) {
                head = new Node<>(item);
                return;
            }
            Node<Item> lastElem = head;
            while (lastElem.next != null) {
                lastElem = lastElem.next;
            }
            Node<Item> newItem = new Node<>(item);
            lastElem.next = newItem;
        }

        public Item removeFirst() {
            if (n == 0) {
                throw new NoSuchElementException();
            }
            Item item = head.item;
            head = head.next;
            n--;
            return item;
        }

        public Item removeLast() {
            if (n == 0) {
                throw new NoSuchElementException();
            }
            if (n == 1) {
                Item it = head.item;
                head = null;
                n--;
                return it;
            }
            n--;
            Node<Item> lastElem = head;
            while (lastElem.next != null) {
                Node<Item> tmp = lastElem.next;
                if (lastElem.next.next == null) {
                    lastElem.next = null;
                }
                lastElem = tmp;
            }

            return lastElem.item;
        }
    }
    private static class Node<Item> {
        public Item item;
        public Node<Item> next;
        public Node(Item item) {
            this.item = item;
        }
    }
}
