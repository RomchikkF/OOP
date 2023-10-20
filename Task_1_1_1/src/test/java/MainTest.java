import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void test0() {
        assertArrayEquals(new int[] {}, Main.heapsort(new int[] {}));
    }

    @Test
    void test1() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, Main.heapsort(new int[] {5, 4, 3, 2, 1}));
    }

    @Test
    void test2() {
        assertArrayEquals(
                new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                Main.heapsort(new int[] {10, 6, 7, 9, 1, 4, 8, 3, 2, 5}));
    }
}