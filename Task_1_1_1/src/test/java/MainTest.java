import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void test() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, Main.heapsort(new int[] {5, 4, 3, 2, 1}));
        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, Main.heapsort(new int[] {5, 6, 7, 9, 8, 4, 10, 3, 2, 1}));
    }
}