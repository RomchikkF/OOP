import static org.junit.jupiter.api.Assertions.*;

import CalculatorExceptions.*;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    Calculator calc = new Calculator();

    enum TestType { Normal, MissingArg, BadArg, Empty, RedundantSymbols }

    @Test
    void test0() {
        String expression = "sin + - 1 2 1";
        double expected = 0;
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test1() {
        String expression = "/ 15.0 * 10.0 .2";
        double expected = 7.5;
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test2() {
        String expression = "sqrt - cos 0 log 1";
        double expected = 1;
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test3() {
        String expression = "sqrt sqrt sqrt pow 2 80";
        double expected = 1024;
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test4() {
        String expression = "tg 0";
        double expected = 0;
        test(expression, expected, TestType.BadArg);
    }

    @Test
    void test5() {
        String expression = "";
        double expected = 0;
        test(expression, expected, TestType.Empty);
    }

    @Test
    void test6() {
        String expression = "pow 0.1";
        double expected = 0;
        test(expression, expected, TestType.MissingArg);
    }

    @Test
    void test7() {
        String expression = "sqrt 9 16";
        double expected = 0;
        test(expression, expected, TestType.RedundantSymbols);
    }

    void test(String expression, double expected, TestType t) {
        try {
            var result = calc.evaluate(expression);
            System.out.println(result);
            if (t != TestType.Normal) {
                fail();
            } else {
                assertTrue(doubleEqual(expected, result));
            }
        } catch (MissingArguments e) {
            if (t != TestType.MissingArg) {
                fail();
            }
        } catch (BadArgument e) {
            if (t != TestType.BadArg) {
                fail();
            }
        } catch (EmptyExpression e) {
            System.out.println("hey");
            if (t != TestType.Empty) {
                fail();
            }
        } catch (RedundantSymbols e) {
            if (t != TestType.RedundantSymbols) {
                fail();
            }
        }
    }

    boolean doubleEqual(double d1, double d2) {
        double eps = 1e-9;
        return (d1 < d2 + eps && d1 > d2 - eps);
    }
}