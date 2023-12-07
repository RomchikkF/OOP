import CalculatorExceptions.*;
import java.util.Arrays;
import java.util.LinkedList;

public class Calculator {

    private LinkedList<String> currentExpression;

    public double evaluate(String s)
            throws MissingArguments, BadArgument, EmptyExpression, RedundantSymbols {
        var list = Arrays.asList(s.isEmpty() ? new String[0] : s.split(" "));
        currentExpression = new LinkedList<>(list);
        double result = evaluateNext();
        if (!currentExpression.isEmpty()) {
            throw new RedundantSymbols(currentExpression.toString());
        }
        return result;
    }

    double evaluateNext()
            throws BadArgument, MissingArguments, EmptyExpression {
        if (currentExpression.isEmpty()) {
            throw new EmptyExpression("");
        }
        String atom = currentExpression.removeFirst();
        if (isConstant(atom)) {
            return Double.parseDouble(atom);
        }
        try {
            switch (atom) {
                case "+":
                    return evaluateNext() + evaluateNext();
                case "-":
                    return evaluateNext() - evaluateNext();
                case "*":
                    return evaluateNext() * evaluateNext();
                case "/":
                    return evaluateNext() / evaluateNext();
                case "sin":
                    return Math.sin(evaluateNext());
                case "cos":
                    return Math.cos(evaluateNext());
                case "log":
                    return Math.log(evaluateNext());
                case "sqrt":
                    return Math.sqrt(evaluateNext());
                case "pow":
                    return Math.pow(evaluateNext(), evaluateNext());
                default:
                    throw new BadArgument(atom);
            }
        }
        catch (EmptyExpression e) {
            throw new MissingArguments("\"" + atom + "\"");
        }
    }

    boolean isConstant(String atom) {
        try {
            Double.parseDouble(atom);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}