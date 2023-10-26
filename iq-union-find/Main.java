public class Main {
    public static void main(String[] args) {
        SocialNetwork SN = new SocialNetwork(10);

        SN.union(1, 4);
        System.out.println(SN.find(1, 4));
        SN.union(4, 5);
        System.out.println(SN.find(5, 1));

        SN.union(2, 3);
        SN.union(3, 6);
        SN.union(2, 4);
        SN.union(3, 7);
        System.out.println(SN.find(7, 5));
        System.out.println(SN.find(0, 1));
        System.out.println(SN.find(0, 9));
        System.out.println(SN.find(1, 9));
    }
}
