package seedu.budgeteer.model.entry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.budgeteer.testutil.Assert;

public class CashFlowTest {

    // TODO: COME UP WITH TESTCASES FOR CASHFLOW

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> CashFlow.getCashFlow(null));
    }

    @Test
    public void constructor_invalidCashFlow_throwsIllegalArgumentException() {
        String invalidCashFlow = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> CashFlow.getCashFlow(invalidCashFlow));
    }


    @Test
    public void isValidCashFlow() {
        // null cash flow
        Assert.assertThrows(NullPointerException.class, () -> CashFlow.isValidCashFlow(null));

        // invalid cash flow
        assertFalse(CashFlow.isValidCashFlow("")); // empty string
        assertFalse(CashFlow.isValidCashFlow(" ")); // spaces only
        assertFalse(CashFlow.isValidCashFlow("Chicken Ricce")); // letters
        assertFalse(CashFlow.isValidCashFlow("*++++*")); // Symbols which are not '-', '+', '.'
        assertFalse(CashFlow.isValidCashFlow("++-111.0")); // Can only have 1 '+' or '-'
        assertFalse(CashFlow.isValidCashFlow("+")); // Must have a number after the sign
        assertFalse(CashFlow.isValidCashFlow("+123.4.42.456")); // multiple decimal points
        assertFalse(CashFlow.isValidCashFlow("-01.23")); // whole number can only start with 0 if it is 1 digit
        assertFalse(CashFlow.isValidCashFlow("-1.")); // there must be at least 1 digit after the decimal point
        assertFalse(CashFlow.isValidCashFlow("-.1")); // there must be at least 1 digit before decimal point
        assertFalse(CashFlow.isValidCashFlow("-123.0011")); // cashflow with decimal places greater than 2
        assertFalse(CashFlow.isValidCashFlow("-9999999999999.99")); // cashflow with whole number part as 13 digits

        // valid cash flow
        assertTrue(CashFlow.isValidCashFlow("-0")); // 0 is valid
        assertTrue(CashFlow.isValidCashFlow("-123456789.00")); // long cashflow
        assertTrue(CashFlow.isValidCashFlow("+12.30")); // standard cashflow
        assertTrue(CashFlow.isValidCashFlow("+12")); // no need for the decimal point
        assertTrue(CashFlow.isValidCashFlow("-999999999999")); // cashflow with 12 digit whole number
    }
}
