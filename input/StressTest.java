public class StressTest {

    static int square(int x) {
        return x * x;
    }

    static int compute(int a) {
        int b = a + 0; // Algebraic simplification
        int c = b; // Copy propagation
        int d = 3 + 5; // Constant folding
        int e = 3 + 5; // CSE candidate
        int f = d + e; // Constant propagation

        // Algrbraic simplification
        int t1 = a + 0;
        int t2 = 0 + a;
        int t3 = a * 1;
        int t4 = 1 * a;
        int t5 = a * 0;
        int t6 = a - 0;
        int t7 = a / 1;

        if (false) { // Unreachable code
            System.out.println("dead");
        }

        for (int i = 0; i < 4; i++) { // Loop optimizations
            f = f + i; // Induction variable
            int g = i * 2; // Strength reduction
            int h = square(i); // Inlining candidate
            System.out.println(g);
        }

        return f;
    }

    public static void main(String[] args) {
        int x = compute(10);
        System.out.println(x);
    }
}