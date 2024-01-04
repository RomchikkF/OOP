import calculatorexceptions.NotComputable;
import java.text.DecimalFormat;

public class ComplexNumber implements CalcValue {

    public static final ComplexNumber i = new ComplexNumber(0, 1);

    private final double imaginary;
    private final double real;

    public ComplexNumber(double value) {
        this.real = value;
        this.imaginary = 0;
    }

    public ComplexNumber(RealNumber number) {
        this.real = number.getValue();
        this.imaginary = 0;
    }

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public RealNumber getMod() {
        return new RealNumber(Math.sqrt(real * real + imaginary * imaginary));
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(real, -imaginary);
    }

    public double getArg() {
        return Math.atan2(imaginary, real);
    }

    @Override
    public ComplexNumber sin() {
        return new ComplexNumber(
                Math.sin(real) * Math.cosh(imaginary),
                Math.cos(real) * Math.sinh(imaginary));
    }

    @Override
    public ComplexNumber cos() {
        return new ComplexNumber(
                Math.cos(real) * Math.cosh(imaginary),
                Math.sin(real) * Math.sinh(imaginary));
    }

    @Override
    public ComplexNumber tan() throws NotComputable {
        return this.sin().divide(this.cos());
    }

    @Override
    public ComplexNumber sqrt() throws NotComputable {
        if (imaginary == 0 && real <= 0) {
            return new ComplexNumber(0, Math.sqrt(-real));
        }
        RealNumber mod = getMod();
        ComplexNumber sum = this.plus(mod);
        return sum.divide(sum.getMod()).multiply(mod.sqrt());
    }

    @Override
    public ComplexNumber log() throws NotComputable {
        return new ComplexNumber((getMod().log()).getValue(), getArg());
    }

    @Override
    public ComplexNumber plus(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            other = new ComplexNumber((RealNumber) other);
        }
        if (other.getClass() == ComplexNumber.class) {
            return new ComplexNumber(
                    real + ((ComplexNumber) other).getReal(),
                    imaginary + ((ComplexNumber) other).getImaginary());
        } else {
            throw new NotComputable("sum of complex number and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public ComplexNumber minus() throws NotComputable {
        return new ComplexNumber(-real, -imaginary);
    }

    @Override
    public ComplexNumber minus(CalcValue other) throws NotComputable {
        return this.plus(other.minus());
    }

    @Override
    public ComplexNumber multiply(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            other = new ComplexNumber((RealNumber) other);
        }
        if (other.getClass() == ComplexNumber.class) {
            ComplexNumber complexOther = (ComplexNumber) other;
            return new ComplexNumber(
                    real * complexOther.getReal() - imaginary * complexOther.getImaginary(),
                    real * complexOther.getImaginary() + imaginary * complexOther.getReal()
            );
        } else {
            throw new NotComputable("multiplication of complex number and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public ComplexNumber divide(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            double value = ((RealNumber) other).value;
            if (value == 0) {
                throw new NotComputable("Cant divide by zero.");
            }
            return new ComplexNumber(real / value, imaginary / value);
        } else if (other.getClass() == ComplexNumber.class) {
            ComplexNumber complexOther = (ComplexNumber) other;
            return this.multiply(complexOther.conjugate())
                    .divide(complexOther.getMod().power(new RealNumber(2)));
        } else {
            throw new NotComputable("division of complex number and "
                    + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public ComplexNumber power(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            other = new ComplexNumber((RealNumber) other);
        }
        if (other.getClass() == ComplexNumber.class) {
            ComplexNumber complexOther = (ComplexNumber) other;
            ComplexNumber pow = new ComplexNumber(getArg()).multiply(complexOther).multiply(i)
                    .plus(getMod().log().multiply(complexOther));
            return new ComplexNumber(Math.exp(pow.getReal())).multiply(
                    new ComplexNumber(Math.cos(pow.getImaginary()), Math.sin(pow.getImaginary()))
            );
        } else {
            throw new NotComputable("complex number to the power of "
                    + other.getClass().toString() + " is not defined");
        }
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#");
        StringBuilder res = new StringBuilder();
        double eps = 1e-5;
        if (real > eps || real < -eps) {
            res.append(df.format(real));
        }
        if (imaginary > eps) {
            if (real > eps || real < -eps) {
                res.append(" + ");
            }
            res.append(df.format(imaginary));
            res.append("i");
        }
        if (imaginary < -eps) {
            if (real > eps || real < -eps) {
                res.append(" - ");
                res.append(df.format(-imaginary));
            } else {
                res.append(df.format(imaginary));
            }
            res.append("i");
        }
        return res.toString();
    }
}