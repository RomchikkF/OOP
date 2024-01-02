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

    public double getArg(){
        return Math.atan2(imaginary, real);
    }

    @Override
    public CalcValue sin() {
        return new ComplexNumber(
                Math.sin(real) * Math.cosh(imaginary),
                Math.cos(real) * Math.sinh(imaginary));
    }

    @Override
    public CalcValue cos() {
        return new ComplexNumber(
                Math.cos(real) * Math.cosh(imaginary),
                Math.sin(real) * Math.sinh(imaginary));
    }

    @Override
    public CalcValue tan() throws NotComputable {
        return this.sin().divide(this.cos());
    }

    @Override
    public CalcValue sqrt() throws NotComputable {
        RealNumber m = getMod();
        RealNumber sumMod = ((ComplexNumber)new ComplexNumber(m).plus(this)).getMod();
        return this.plus(m).divide(sumMod).multiply(m.sqrt());
    }

    @Override
    public CalcValue log() throws NotComputable {
        return new ComplexNumber(((RealNumber) getMod().log()).getValue(), getArg());
    }

    @Override
    public CalcValue plus(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            other = new ComplexNumber((RealNumber) other);
        }
        if (other.getClass() == ComplexNumber.class) {
            return new ComplexNumber(
                    real + ((ComplexNumber) other).getReal(),
                    imaginary + ((ComplexNumber) other).getImaginary());
        } else {
            throw new NotComputable("sum of complex number and " + other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue minus() throws NotComputable {
        return new ComplexNumber(-real, -imaginary);
    }

    @Override
    public CalcValue minus(CalcValue other) throws NotComputable {
        return this.plus(other.minus());
    }

    @Override
    public CalcValue multiply(CalcValue other) throws NotComputable {
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
            throw new NotComputable("multiplication of complex number and " +
                    other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue divide(CalcValue other) throws NotComputable {
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
            throw new NotComputable("division of complex number and " +
                    other.getClass().toString() + " is not defined");
        }
    }

    @Override
    public CalcValue power(CalcValue other) throws NotComputable {
        if (other.getClass() == RealNumber.class) {
            other = new ComplexNumber((RealNumber) other);
        }
        if (other.getClass() == ComplexNumber.class) {
            ComplexNumber complexOther = (ComplexNumber) other;
            ComplexNumber pow = (ComplexNumber)
                    getMod().log().multiply(complexOther).plus(
                    new ComplexNumber(getArg()).multiply(complexOther).multiply(i));
            return new ComplexNumber(Math.exp(pow.getReal())).multiply(
                    new ComplexNumber(Math.cos(pow.getImaginary()), Math.sin(pow.getImaginary()))
            );
        } else {
            throw new NotComputable("complex number to the power of " +
                    other.getClass().toString() + " is not defined");
        }
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#");
        StringBuilder res = new StringBuilder();
        double EPS = 1e-5;
        if (real > EPS || real < -EPS) {
            res.append(df.format(real));
        }
        if (imaginary > EPS) {
            if (real > EPS || real < -EPS) {
                res.append(" + ");
            }
            res.append(df.format(imaginary));
            res.append("i");
        }
        if (imaginary < -EPS) {
            if (real > EPS || real < -EPS) {
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