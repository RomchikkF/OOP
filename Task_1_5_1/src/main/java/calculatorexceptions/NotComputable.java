package calculatorexceptions;

/**
 * exception for all not defined behaviour for calculator types
 */
public class NotComputable extends Exception {
    public NotComputable(String message) {
        super(message);
    }
}
