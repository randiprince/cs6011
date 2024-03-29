import java.io.IOException;

interface Comparable<T> {
    int compareTo(T o);
}
class Fraction implements Comparable<Fraction>{
    private long numerator;
    private long denominator;

    public Fraction () {
        numerator = 0;
        denominator = 1;
        reduce();
    }

    public Fraction(long n, long d) throws ArithmeticException{
        if (d == 0) {
            throw new ArithmeticException("You cannot divide by zero!!");
        }
        if (d < 0) {
            numerator = n * -1;
            denominator = d * -1;
        } else {
            numerator = n;
            denominator = d;
        }
        reduce();
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

    public int compareTo(Fraction rhs) {
        if (this.toDouble() < rhs.toDouble()) {
            return -1;
        } else if (this.toDouble() == rhs.toDouble()) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}