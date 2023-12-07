package calculatorExceptions;

public class RedundantSymbols extends Exception {
    public RedundantSymbols(String str) {
        super("Symbols left after calculation: " + str);
    }
}
