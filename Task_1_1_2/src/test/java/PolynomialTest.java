import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PolynomialTest {

    @Test
    void test_string0() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        assertEquals("7x^3 + 6x^2 + 3x + 4", p1.toString());
    }

    @Test
    void test_string1() {
        Polynomial p1 = new Polynomial(new int[] {0, 1, 0, 3});
        assertEquals("3x^3 + x", p1.toString());
    }

    @Test
    void test_string2() {
        Polynomial p1 = new Polynomial(new int[] {-1, -2, 0, -1});
        assertEquals("-x^3 - 2x - 1", p1.toString());
    }

    @Test
    void test_plus() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});
        assertEquals("7x^3 + 14x^2 + 5x + 7", p1.plus(p2).toString());
    }

    @Test
    void test_minus() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});
        assertEquals("7x^3 - 2x^2 + x + 1", p1.minus(p2).toString());
    }

    @Test
    void test_diff0() {
        Polynomial p1 = new Polynomial(new int[] {1, 2, 3, 4});
        assertEquals("4x^3 + 3x^2 + 2x + 1", p1.differentiate(0).toString());
    }

    @Test
    void test_diff1() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        assertEquals("21x^2 + 12x + 3", p1.differentiate(1).toString());
    }

    @Test
    void test_diff2() {
        Polynomial p1 = new Polynomial(new int[] {1, 1, 1, 1, 1, 1});
        assertEquals("60x^2 + 24x + 6", p1.differentiate(3).toString());
    }

    @Test
    void test_eval() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        assertEquals(90, p1.evaluate(2));
    }

    @Test
    void test_times() {
        Polynomial p1 = new Polynomial(new int[] {3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});
        assertEquals("56x^4 + 62x^3 + 57x^2 + 24x + 9", p1.times(p2).toString());
    }
}