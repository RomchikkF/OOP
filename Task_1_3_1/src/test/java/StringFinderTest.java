import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

class StringFinderTest {

    int[] test(String path, String string) throws IOException {
        StringFinder stringFinder = new StringFinder();
        return stringFinder.findInFile("resources/" + path, string);
    }

    int[] test(String path, String string, int buffSize) throws IOException {
        StringFinder stringFinder = new StringFinder(buffSize);
        return stringFinder.findInFile("resources/" + path, string);
    }

    int [] testSmallBuff(String path, String string) throws IOException {
        return test(path, string, 32);
    }

    @Test
    void test0() throws IOException {
        assertArrayEquals(new int[] {138}, testSmallBuff("test0.txt", "abracadabra"));
    }

    @Test
    void test1() throws IOException {
        assertArrayEquals(new int[] {}, testSmallBuff("test1.txt", "some cool string"));
    }

    @Test
    void test2() throws IOException {
        assertArrayEquals(new int[] {0, 1, 2, 3}, test("test2.txt", "aaaaa"));
    }

    @Test
    void test3() throws IOException {
        assertArrayEquals(new int[]{91, 170}, testSmallBuff("test3.txt", "Проверка utf-8"));
    }

    @Test
    void test4() throws IOException {
        assertEquals(40, test("test4.txt", "а").length);
    }
}