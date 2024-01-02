import calculatorexceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calc = new Calculator();

    enum TestType { Normal, MissingArg, BadArg, Empty, IncorrectOperations, RedundantSymbols }

    @Test
    void test0() {
        String expression = "sin + - 1 2 1";
        String expected = "0";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test1() {
        String expression = "/ 15.0 * 10.0 .2";
        String expected = "7,5";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test2() {
        String expression = "sqrt - cos 0 log 1";
        String expected = "1";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test3() {
        String expression = "sqrt sqrt sqrt pow 2 80";
        String expected = "1024";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test4() {
        String expression = "tg 0";
        String expected = "0";
        test(expression, expected, TestType.BadArg);
    }

    @Test
    void test5() {
        String expression = "";
        String expected = "0";
        test(expression, expected, TestType.Empty);
    }

    @Test
    void test6() {
        String expression = "pow 0.1";
        String expected = "0";
        test(expression, expected, TestType.MissingArg);
    }

    @Test
    void test7() {
        String expression = "sqrt 9 16";
        String expected = "0";
        test(expression, expected, TestType.RedundantSymbols);
    }

    @Test
    void test8() {
        String expression = "10i";
        String expected = "10i";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test9() {
        String expression = "- 5 2i";
        String expected = "5 - 2i";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test10() {
        String expression = "/ * + 2 2i - 4 3i -2i";
        String expected = "-1 + 7i";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test11() {
        String expression = "sin 1i";
        String expected = "1,2i";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test12() {
        String expression = "cos + 1 1i";
        String expected = "0,8 + 1i";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test13() {
        String expression = "pow 2i 3";
        String expected = "-8i";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test14() {
        String expression = "15\u00B0";
        String expected = "15\u00B0";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test15() {
        String expression = "+ 20\u00B0 25\u00B0";
        String expected = "45\u00B0";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test16() {
        String expression = "sin + 30\u00B0 60\u00B0";
        String expected = "1";
        test(expression, expected, TestType.Normal);
    }

    @Test
    void test17() {
        String expression = "- cos * 30\u00B0 6 2i";
        String expected = "-1 - 2i";
        test(expression, expected, TestType.Normal);
    }

    void test(String expression, String expected, TestType t) {
        try {
            CalcValue result = calc.evaluate(expression);
            System.out.println(result);
            if (t != TestType.Normal) {
                fail();
            } else {
                assertEquals(expected, result.toString());
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
            if (t != TestType.Empty) {
                fail();
            }
        } catch (RedundantSymbols e) {
            if (t != TestType.RedundantSymbols) {
                fail();
            }
        } catch (NotComputable e) {
            if (t != TestType.IncorrectOperations){
                fail();
            }
        }
    }
}