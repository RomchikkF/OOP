import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

class StringFinderTest {

    int[] test(String path, String string) throws IOException {
        StringFinder stringFinder = new StringFinder();
        return stringFinder.findInFile("resources/" + path, string);
    }

    @Test
    void test0() throws IOException {
        assertArrayEquals(new int[] {134}, test("test0.txt", "abracadabra"));
    }

    @Test
    void test1() throws IOException {
        assertArrayEquals(new int[] {}, test("test1.txt", "some cool string"));
    }

    @Test
    void test2() throws IOException {
        assertArrayEquals(new int[] {0, 1, 2, 3}, test("test2.txt", "aaaaa"));
    }

    @Test
    void test3() throws IOException {
        assertArrayEquals(new int[]{87, 161}, test("test3.txt", "Проверка utf-8"));
    }

    @Test
    void test4() throws IOException {
        assertEquals(40, test("test4.txt", "а").length);
    }
}