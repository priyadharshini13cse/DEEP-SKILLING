import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

class Calculator {
    int add(int a, int b) {
        return a + b;
    }
}

public class AAATest {

    Calculator calc;

    @Before
    public void setUp() {
        calc = new Calculator();
        System.out.println("Setup Done");
    }

    @Test
    public void testAddition() {

        // Arrange
        int a = 2;
        int b = 3;

        // Act
        int result = calc.add(a, b);

        // Assert
        assertEquals(5, result);
    }

    @After
    public void tearDown() {
        System.out.println("Teardown Done");
    }
}