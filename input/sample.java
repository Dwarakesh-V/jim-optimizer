public class sample {

    static int helper(int x) {
        return x + 1;
    }

    public static void main(String[] args) {
        int a = 5;
        int b = helper(a);
        System.out.println(b);
    }
}