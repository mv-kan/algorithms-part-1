/*
* Interview Question no. 1
* Social network connectivity. Given a social network containing � n members and a log file containing � m timestamps at
* which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all
* members are connected (i.e., every member is a friend of a friend of a friend ... of a friend).
*
* Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
* The running time of your algorithm should be � log ⁡ � mlogn or better and use extra space proportional to � n.
* */

// I am gonna be using weighted quick-union
public class SocialNetwork {
    final private int[] friends;
    final private int[] friendsTreeSize;
    public SocialNetwork(int n) {
        friends = new int[n];
        friendsTreeSize = new int[n];
        for (int i = 0; i < n; i++) {
            friends[i] = i;
        }
    }

    public void union(int index1, int index2) {
        int size = 1;
        while (friends[index2] != index2) {
            index2 = friends[index2];
            size++;
        }
        friendsTreeSize[index2] = size;
        size = 1;
        while (friends[index1] != index1) {
            index1 = friends[index1];
            size++;
        }
        friendsTreeSize[index1] = size;
        
        // set index2 parent to index1
        friends[index2] = index1;
    }

    public boolean find(int index1, int index2) {
        int root1 = friends[index1]; // parent
        int tmp = index1; // value
        while (root1 != tmp) {
            tmp = friends[root1];
            root1 = friends[tmp];
        }
        int root2 = friends[index2];
        tmp = index2;
        while (root2 != tmp) {
            tmp = friends[root2];
            root2 = friends[tmp];
        }
        return root1 == root2;
    }

    public boolean checkIfAllFriends() {

        return false;
    }
}
