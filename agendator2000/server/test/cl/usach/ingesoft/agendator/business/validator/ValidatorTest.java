package cl.usach.ingesoft.agendator.business.validator;

import cl.usach.ingesoft.agendator.util.BusinessException;
import org.junit.Test;

public class ValidatorTest {

    // -----------------------------------

    @Test(expected = BusinessException.class)
    public void testShouldBeNull() {
        Validator.shouldBeNull("foo");
    }

    @Test
    public void testShouldBeNull2() {
        Validator.shouldBeNull(null);
    }

    // -----------------------------------

    @Test
    public void testShouldNotBeNull() {
        Validator.shouldNotBeNull(new Object());
    }

    @Test(expected = RuntimeException.class)
    public void testShouldNotBeNull2() {
        Validator.shouldNotBeNull(null);
    }

    // -----------------------------------

    @Test(expected = BusinessException.class)
    public void testShouldBeMoney() {
        Validator.shouldNotBeNegative(-500);
    }

    @Test
    public void testShouldBeMoney2() {
        Validator.shouldNotBeNegative(1500);
    }

    // -----------------------------------

    @Test(expected = RuntimeException.class)
    public void testShouldBeOrdered() {
        Validator.shouldBeOrdered(1, 3, 2, 4);
    }

    @Test
    public void testShouldBeOrdered2() {
        Validator.shouldBeOrdered(1, 2, 3, 4);
    }

    // -----------------------------------

    @Test(expected = RuntimeException.class)
    public void testShouldBeFound() {
        Validator.shouldBeFound(null);
    }

    @Test
    public void testShouldBeFound2() {
        Validator.shouldBeFound("foo");
    }

    // -----------------------------------

    @Test(expected = RuntimeException.class)
    public void testShouldNotBeEmpty() {
        Validator.shouldNotBeEmpty(null);
    }

    @Test(expected = RuntimeException.class)
    public void testShouldNotBeEmpty2() {
        Validator.shouldNotBeEmpty("");
    }

    @Test
    public void testShouldNotBeEmpty3() {
        Validator.shouldNotBeEmpty("asd");
    }
}
