//import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FractionTest {
    @Test
    public void runATest() {
        // Qualify the assertEquals() with "Assertions." to say that it comes from the Assertions library.
        // Assertions library, as can be seen from the import, is: org.junit.jupiter.api.Assertions.
        Assertions.assertEquals(3,3); // Dummy assert... put real code here.
    }
    @Test
    public void testTimes() {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.times( f2 );

        Assertions.assertEquals( f3.toString(), "1/6" );
    }

    @Test
    public void testDivided() {
        try {
            Fraction f1 = new Fraction(-3, 4);
            Fraction f2 = new Fraction(0, 8);
            Fraction f3 = f1.dividedBy(f2);

            Assertions.assertEquals(f3.toDouble(), null);
        } catch (ArithmeticException e) {
            System.out.println("You cannot divide by zero!!!!!!");
        }

    }

    @Test
    public void testAdd() {
        Fraction f1 = new Fraction(-3,4 );
        Fraction f2 = new Fraction( -2, 8 );
        Fraction f3 = f1.plus( f2 );

        Assertions.assertEquals( f3.toString(), "-1/1" );
        Assertions.assertEquals( f3.toDouble(), -1.0);
    }

    @Test
    public void testSubtract() {
        Fraction f1 = new Fraction(-3,4 );
        Fraction f2 = new Fraction( -2, 8 );
        Fraction f3 = f1.minus( f2 );

        Assertions.assertEquals( f3.toString(), "-1/2" );
        Assertions.assertEquals( f3.reciprocal().toString(), "-2/1" );
    }

    @Test
    public void testException() throws RuntimeException{
        new Fraction(-3,0 );
    }
}