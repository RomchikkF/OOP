import calculatorexceptions.BadArgument;
import calculatorexceptions.EmptyExpression;
import calculatorexceptions.MissingArguments;
import calculatorexceptions.NotComputable;
import calculatorexceptions.RedundantSymbols;

import java.util.Arrays;
import java.util.LinkedList;

public class Calculator {

    private LinkedList<String> currentExpression;

    public CalcValue evaluate(String s)
            throws MissingArguments, BadArgument, EmptyExpression, RedundantSymbols, NotComputable {
        var list = Arrays.asList(s.isEmpty() ? new String[0] : s.split(" "));
        currentExpression = new LinkedList<>(list);
        CalcValue result = evaluateNext();
        if (!currentExpression.isEmpty()) {
            throw new RedundantSymbols(currentExpression.toString());
        }
        return result;
    }

    CalcValue evaluateNext()
            throws BadArgument, MissingArguments, EmptyExpression, NotComputable {
        if (currentExpression.isEmpty()) {
            throw new EmptyExpression("");
        }
        String atom = currentExpression.removeFirst();
        if (isConstant(atom)) {
            return parseAtom(atom);
        }
        try {
            switch (atom) {
                case "+":
                    return evaluateNext().plus(evaluateNext());
                case "-":
                    return evaluateNext().minus(evaluateNext());
                case "*":
                    return evaluateNext().multiply(evaluateNext());
                case "/":
                    return evaluateNext().divide(evaluateNext());
                case "sin":
                    return evaluateNext().sin();
                case "cos":
                    return evaluateNext().cos();
                case "log":
                    return evaluateNext().log();
                case "sqrt":
                    return evaluateNext().sqrt();
                case "pow":
                    return evaluateNext().power(evaluateNext());
                default:
                    throw new BadArgument(atom);
            }
        } catch (EmptyExpression e) {
            throw new MissingArguments("\"" + atom + "\"");
        }
    }

    boolean isConstant(String atom) {
        try {
            if (atom.charAt(atom.length() - 1) == 'i') {
                if (atom.length() == 1) {  // just i
                    return true;
                }
                Double.parseDouble(atom.substring(0, atom.length() - 1));
            } else if (atom.charAt(atom.length() - 1) == 'd') {
                Double.parseDouble(atom.substring(0, atom.length() - 1));
            } else {
                Double.parseDouble(atom);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    CalcValue parseAtom(String atom){
        if (atom.charAt(atom.length() - 1) == 'i') {
            if (atom.length() == 1) {  // just i
                return ComplexNumber.i;
            }
            double value = Double.parseDouble(atom.substring(0, atom.length() - 1));
            System.out.println(new ComplexNumber(0, value));
            return new ComplexNumber(0, value);
        } else if (atom.charAt(atom.length() - 1) == 'd') {  // '°' symbol or '\u00B0' does not work
            double value = Double.parseDouble(atom.substring(0, atom.length() - 1));
            return new Degrees(value);
        } else {
            return new RealNumber(Double.parseDouble(atom));
        }
    }
}