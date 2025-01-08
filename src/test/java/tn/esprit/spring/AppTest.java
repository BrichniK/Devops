package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testAddition() {
        int result = 2 + 3;
        assertEquals(5, result, "Addition test failed!"); // Test passes if result = 5
    }

    @Test
    public void testSubtraction() {
        int result = 5 - 3;
        assertEquals(2, result, "Subtraction test failed!"); // Test passes if result = 2
    }

    @Test
    public void testMultiplication() {
        int result = 3 * 4;
        assertEquals(12, result, "Multiplication test failed!"); // Test passes if result = 12
    }

    @Test
    public void testDivision() {
        int result = 10 / 2;
        assertEquals(5, result, "Division test failed!"); // Test passes if result = 5
    }

    @Test
    public void testDivisionByZero() {
        // Test division by zero (edge case)
        assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0; // This will throw an ArithmeticException
        }, "Division by zero should throw an exception!");
    }

    @Test
    public void testNegativeNumber() {
        int result = -3 + 3;
        assertEquals(0, result, "Negative number test failed!"); // Test passes if result = 0
    }

    @Test
    public void testLargeNumbers() {
        long result = 1000000000L + 2000000000L;
        assertEquals(3000000000L, result, "Large numbers addition test failed!"); // Test passes if result = 3000000000L
    }
}
