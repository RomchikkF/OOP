import calculatorexceptions.NotComputable;

public interface CalcValue {
    public CalcValue sin() throws NotComputable;
    public CalcValue cos() throws NotComputable;
    public CalcValue tan() throws NotComputable;
    public CalcValue sqrt() throws NotComputable;
    public CalcValue log() throws NotComputable;
    public CalcValue plus(CalcValue other) throws NotComputable;
    public CalcValue minus(CalcValue other) throws NotComputable;
    public CalcValue minus() throws NotComputable;
    public CalcValue multiply(CalcValue other) throws NotComputable;
    public CalcValue divide(CalcValue other) throws NotComputable;
    public CalcValue power(CalcValue other) throws NotComputable;
}
