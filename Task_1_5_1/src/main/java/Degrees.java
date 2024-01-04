import calculatorexceptions.NotComputable;
import java.text.DecimalFormat;

public class Degrees implements CalcValue {

    double value;

    public Degrees(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double getRadians() {
        return Math.toRadians(value);
    }

    @Override
    public CalcValue sin() {
        return new RealNumber(Math.sin(getRadians()));
    }

    @Override
    public CalcValue cos() {
        return new RealNumber(Math.cos(getRadians()));
    }

    @Override
    public CalcValue tan() {
        return new RealNumber(Math.tan(getRadians()));
    }

    @Override
    public CalcValue sqrt() throws NotComputable {
        throw new NotComputable("Square root of degrees is not defined");
    }

    @Override
    public CalcValue log() throws NotComputable {
        throw new NotComputable("logarithm of degrees is not defined");
    }

    @Override
    public CalcValue plus(CalcValue other) throws NotComputable {
        if (other.getClass() == Degrees.class) {
            return new Degrees(value + ((Degrees) other).getValue());
        } else {
            throw new NotComputable("sum of degrees and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue minus() throws NotComputable {
        return new Degrees(-getValue());
    }

    @Override
    public CalcValue minus(CalcValue other) throws NotComputable {
        return this.plus(other.minus());
    }

    @Override
    public CalcValue multiply(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            return new Degrees(value * ((RealNumber) other).getValue());
        } else {
            throw new NotComputable("multiplication of degrees and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue divide(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            double value2 = ((RealNumber) other).getValue();
            if (value2 == 0) {
                throw new NotComputable("cant divide by zero");
            }
            return new Degrees(value / value2);
        } else if (other.getClass() == Degrees.class) {
            double value2 = ((Degrees) other).getValue();
            if (value2 == 0) {
                throw new NotComputable("cant divide by zero");
            }
            return new RealNumber(value / value2);
        } else {
            throw new NotComputable("division of degrees and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue power(CalcValue other) throws NotComputable {
        throw new NotComputable("Raising degrees to any power is not defined");
    }

    public String toString() {
        DecimalFormat format = new DecimalFormat("#.#");
        return format.format(value) + "d";
    }
}
