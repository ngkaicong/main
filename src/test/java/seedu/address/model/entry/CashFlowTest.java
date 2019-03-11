package seedu.address.model.entry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CashFlowTest {

    // TODO: COME UP WITH TESTCASES FOR CASHFLOW

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CashFlow(null));
    }

    @Test
    public void constructor_invalidCashFlow_throwsIllegalArgumentException() {
        String invalidCashFlow = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CashFlow(invalidCashFlow));
    }

    @Test
    public void isValidCashFlow() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> CashFlow.isValidCashFlow(null));

        // blank email
        assertFalse(CashFlow.isValidCashFlow("")); // empty string
        assertFalse(CashFlow.isValidCashFlow(" ")); // spaces only

        // missing parts
        assertFalse(CashFlow.isValidCashFlow("@example.com")); // missing local part
        assertFalse(CashFlow.isValidCashFlow("peterjackexample.com")); // missing '@' symbol
        assertFalse(CashFlow.isValidCashFlow("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(CashFlow.isValidCashFlow("peterjack@-")); // invalid domain name
        assertFalse(CashFlow.isValidCashFlow("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(CashFlow.isValidCashFlow("peter jack@example.com")); // spaces in local part
        assertFalse(CashFlow.isValidCashFlow("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(CashFlow.isValidCashFlow(" peterjack@example.com")); // leading space
        assertFalse(CashFlow.isValidCashFlow("peterjack@example.com ")); // trailing space
        assertFalse(CashFlow.isValidCashFlow("peterjack@@example.com")); // double '@' symbol
        assertFalse(CashFlow.isValidCashFlow("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(CashFlow.isValidCashFlow("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(CashFlow.isValidCashFlow("peterjack@.example.com")); // domain name starts with a period
        assertFalse(CashFlow.isValidCashFlow("peterjack@example.com.")); // domain name ends with a period
        assertFalse(CashFlow.isValidCashFlow("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(CashFlow.isValidCashFlow("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(CashFlow.isValidCashFlow("PeterJack_1190@example.com"));
        assertTrue(CashFlow.isValidCashFlow("a@bc")); // minimal
        assertTrue(CashFlow.isValidCashFlow("test@localhost")); // alphabets only
        assertTrue(CashFlow.isValidCashFlow("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(CashFlow.isValidCashFlow("123@145")); // numeric local part and domain name
        assertTrue(CashFlow.isValidCashFlow("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(CashFlow.isValidCashFlow("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(CashFlow.isValidCashFlow("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
