import java.io.IOException;

public class Fraction {
    long numerator;
    long denominator;

    public Fraction () {
        numerator = 0;
        denominator = 1;
        reduce();
    }

    public Fraction(long n, long d) {
        try {
           System.out.println(n / d);
            if (d < 0) {
                numerator = n * -1;
                denominator = d * -1;
            } else {
                numerator = n;
                denominator = d;
            }
            reduce();
        } catch (ArithmeticException e) {
            System.out.println("You cannot divide by 0!");
        }

    }

    public Fraction plus(Fraction rhs) {
        Fraction plus = new Fraction();
        long addedDenominator = denominator * rhs.denominator;
        plus.denominator = addedDenominator;
        plus.numerator = (numerator * rhs.denominator) + (rhs.numerator * denominator);
        return plus;
    }

    public Fraction minus(Fraction rhs) {
        Fraction minus = new Fraction();
        long subtractedDenominator = denominator * rhs.denominator;
        minus.denominator = subtractedDenominator;
        minus.numerator = (numerator * rhs.denominator) - (rhs.numerator * denominator);
        return minus;
    }

    public Fraction times(Fraction rhs) {
        Fraction multiplied = new Fraction();
        multiplied.numerator = numerator * rhs.numerator;
        multiplied.denominator = denominator * rhs.denominator;
        return multiplied;
    }

    public Fraction dividedBy(Fraction rhs) {
        Fraction divided = new Fraction();
        divided.numerator = numerator * rhs.denominator;
        divided.denominator = denominator * rhs.numerator;
        return divided;
    }

    public Fraction reciprocal() {
        Fraction reciprocal = new Fraction();
        if (numerator < 0) {
            reciprocal.denominator = numerator * -1;
            reciprocal.numerator = denominator * -1;
        } else {
            reciprocal.denominator = numerator;
            reciprocal.numerator = denominator;
        }
        return reciprocal;
    }

    public String toString() {
        reduce();
        if (denominator < 0) {
            numerator = numerator * -1;
            denominator = denominator * -1;
        }
        String fracString = numerator + "/" + denominator;
        return fracString;
    }

    public double toDouble() {
        double fracAsDouble = numerator / denominator;
        return fracAsDouble;
    }

    private long gcd() {
        long gcd = numerator;
        long remainder = denominator;
        while( remainder != 0 ) {
            long temp = remainder;
            remainder = gcd % remainder;
            gcd = temp;
        }
        return gcd;
    }

    private void reduce() {
        long gcd = gcd();
        numerator = numerator / gcd;
        denominator = denominator / gcd;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}