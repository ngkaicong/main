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
        assertFalse(CashFlow.isValidCashFlow("5.32")); // No +/- Sign
        assertFalse(CashFlow.isValidCashFlow("+1")); // No Decimal
        assertFalse(CashFlow.isValidCashFlow("+1.23123")); // Too much decimals
        assertFalse(CashFlow.isValidCashFlow("+1 300 230")); // Spaces

        // valid email
        assertTrue(CashFlow.isValidCashFlow("+5.00"));
        assertTrue(CashFlow.isValidCashFlow("-5.00")); // negative value
        assertTrue(CashFlow.isValidCashFlow("+194324.00")); // high value only


    }
}
