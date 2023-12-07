package CalculatorExceptions;

public class MissingArguments extends Exception {
    public MissingArguments(String functionName) {
        super("Missing arguments in function " + functionName);
    }
}
