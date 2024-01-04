import calculatorexceptions.NotComputable;
import java.text.DecimalFormat;

public class RealNumber implements CalcValue {

    double value;

    public RealNumber(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public CalcValue sin() {
        return new RealNumber(Math.sin(value));
    }

    @Override
    public CalcValue cos() {
        return new RealNumber(Math.cos(value));
    }

    @Override
    public CalcValue tan() {
        return new RealNumber(Math.tan(value));
    }

    @Override
    public CalcValue sqrt() throws NotComputable {
        if (value < 0) {
            throw new NotComputable("cant get sqrt of negative number");
        }
        return new RealNumber(Math.sqrt(value));
    }

    @Override
    public CalcValue log() throws NotComputable {
        if (value < 0) {
            throw new NotComputable("cant get log of negative number");
        }
        return new RealNumber(Math.log(value));
    }

    @Override
    public CalcValue plus(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            return new RealNumber(value + ((RealNumber) other).getValue());
        } else if (other.getClass() == ComplexNumber.class) {
            return other.plus(this);
        } else {
            throw new NotComputable("sum of real number and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue minus() throws NotComputable {
        return new RealNumber(-getValue());
    }

    @Override
    public CalcValue minus(CalcValue other) throws NotComputable {
        return this.plus(other.minus());
    }

    @Override
    public CalcValue multiply(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            return new RealNumber(value * ((RealNumber) other).getValue());
        } else if (other.getClass() == ComplexNumber.class) {
            return other.multiply(this);
        } else {
            throw new NotComputable("multiplication of real number and "
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
            return new RealNumber(value / value2);
        } else if (other.getClass() == ComplexNumber.class) {
            return other.divide(this);
        } else {
            throw new NotComputable("division of real number and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue power(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            return new RealNumber(Math.pow(value, ((RealNumber) other).getValue()));
        } else if (other.getClass() == ComplexNumber.class) {
            return other.power(this);
        } else {
            throw new NotComputable("real number to the power of "
                    + other.getClass().toString() + " is not defined");
        }
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
    }
}
