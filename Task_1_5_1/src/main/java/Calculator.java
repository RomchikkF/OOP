import CalculatorExceptions.*;

import java.util.Arrays;
import java.util.LinkedList;

public class Calculator {

    private LinkedList<String> currentExpression;

    public Double Evaluate(String s)
            throws MissingArguments, BadArgument, EmptyExpression, RedundantSymbols {
        var list = Arrays.asList(s.isEmpty() ? new String[0] : s.split(" "));
        currentExpression = new LinkedList<>(list);
        double result = EvaluateNext();
        if (!currentExpression.isEmpty()) {
            throw new RedundantSymbols(currentExpression.toString());
        }
        return result;
    }

    Double EvaluateNext()
            throws BadArgument, MissingArguments, EmptyExpression
    {
        if (currentExpression.isEmpty()) {
            throw new EmptyExpression("");
        }
        String atom = currentExpression.removeFirst();
        if (isConstant(atom)) {
            return Double.parseDouble(atom);
        }
        try {
            return switch (atom) {
                case "+" -> EvaluateNext() + EvaluateNext();
                case "-" -> EvaluateNext() - EvaluateNext();
                case "*" -> EvaluateNext() * EvaluateNext();
                case "/" -> EvaluateNext() / EvaluateNext();
                case "sin" -> Math.sin(EvaluateNext());
                case "cos" -> Math.cos(EvaluateNext());
                case "log" -> Math.log(EvaluateNext());
                case "sqrt" -> Math.sqrt(EvaluateNext());
                case "pow" -> Math.pow(EvaluateNext(), EvaluateNext());
                default -> throw new BadArgument(atom);
            };
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