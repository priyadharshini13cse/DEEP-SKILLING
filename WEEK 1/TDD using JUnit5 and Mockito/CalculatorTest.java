import static org.junit.Assert.assertEquals;
import org.junit.Test;

class Calculator {
    int add(int a, int b) {
        return a + b;
    }
}

public class CalculatorTest {

    @Test
    public void testAddition() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }
}