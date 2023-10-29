import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        do {
            String str = StdIn.readString();
            rq.enqueue(str);
        } while (!StdIn.isEmpty());
        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}
