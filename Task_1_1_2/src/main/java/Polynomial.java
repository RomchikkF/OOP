import java.util.Arrays;

import static com.google.common.math.IntMath.pow;
import static java.lang.Integer.max;

public class Polynomial {
    int[] coefficients;
    int length;
    public Polynomial(int[] _coefficients){
        coefficients = _coefficients;
        length = coefficients.length;
    }

    public Polynomial plus(Polynomial other){
        int[] newCoefficients = new int[max(length, other.length)];
        for (int i = 0; i < newCoefficients.length; ++i){
            if (i < length){
                newCoefficients[i] += coefficients[i];
            }
            if (i < other.length){
                newCoefficients[i] += other.coefficients[i];
            }
        }
        return new Polynomial(newCoefficients);
    }

    public Polynomial times(int n) {
        int[] newCoefficients = new int[length];
        for (int i = 0; i < length; ++i) {
            newCoefficients[i] = coefficients[i] * n;
        }
        return new Polynomial(newCoefficients);
    }

    public Polynomial times(Polynomial other) {
        int[] newCoefficients = new int[length + other.length - 1];
        for (int i = 0 ; i < length; ++i){
            for (int j = 0; j < other.length; ++j){
                newCoefficients[i + j] += coefficients[i] * other.coefficients[j];
            }
        }
        return new Polynomial(newCoefficients);
    }

    public Polynomial minus(Polynomial other){
        return this.plus(other.times(-1));
    }

    public int evaluate(int x){
        int result = 0;
        for (int i = 0; i < length; ++i){
            result += pow(x, i) * coefficients[i];
        }
        return result;
    }

    public Polynomial differentiate(int n) {
        int[] newCoefficients = new int[max(1, length - n)];
        for (int i = 0; i < length - n; ++i){
            newCoefficients[i] += coefficients[i + n] * (i + n);
        }
        return new Polynomial(newCoefficients);
    }

    public boolean equals(Polynomial other){
        return Arrays.equals(coefficients, other.coefficients);
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = length - 1; i >= 0; --i){
            if (coefficients[i] != 0){
                if (!str.isEmpty()){
                    str.append(" + ");
                }
                if (coefficients[i] != 1 || i == 0) {
                    str.append(coefficients[i]);
                }
                if (i > 1){
                    str.append("x^").append(i);
                } else if (i > 0){
                    str.append("x");
                }
            }
        }
        return str.toString();
    }
}