package cl.usach.ingesoft.agendator.util.business.validator;

import cl.usach.ingesoft.agendator.common.BusinessException;
import org.junit.Test;

public class ValidatorTest {

    // -----------------------------------

    @Test(expected = BusinessException.class)
    public void shouldBeNullTest() {
        Validator.shouldBeNull("foo");
    }

    @Test
    public void shouldBeNull2Test() {
        Validator.shouldBeNull(null);
    }

    // -----------------------------------

    @Test
    public void shouldNotBeNullTest() {
        Validator.shouldNotBeNull(new Object());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotBeNull2Test() {
        Validator.shouldNotBeNull(null);
    }

    // -----------------------------------

    @Test(expected = BusinessException.class)
    public void shouldBeMoneyTest() {
        Validator.shouldNotBeNegative(-500);
    }

    @Test
    public void shouldBeMoney2Test() {
        Validator.shouldNotBeNegative(1500);
    }

    // -----------------------------------

    @Test(expected = RuntimeException.class)
    public void shouldBeOrderedTest() {
        Validator.shouldBeOrdered(1, 3, 2, 4);
    }

    @Test
    public void shouldBeOrdered2Test() {
        Validator.shouldBeOrdered(1, 2, 3, 4);
    }

    // -----------------------------------

    @Test(expected = RuntimeException.class)
    public void shouldBeFoundTest() {
        Validator.shouldBeFound(null);
    }

    @Test
    public void shouldBeFound2Test() {
        Validator.shouldBeFound("foo");
    }

    // -----------------------------------

    @Test(expected = RuntimeException.class)
    public void shouldNotBeEmptyTest() {
        Validator.shouldNotBeEmpty(null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotBeEmpty2Test() {
        Validator.shouldNotBeEmpty("");
    }

    @Test
    public void shouldNotBeEmpty3Test() {
        Validator.shouldNotBeEmpty("asd");
    }
}
