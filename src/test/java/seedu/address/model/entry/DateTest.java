package seedu.address.model.entry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    // TODO: COME UP WITH TESTCASES FOR DATE

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDateFormat(null));

        // invalid phone numbers
        assertFalse(Date.isValidDateFormat("")); // empty string
        assertFalse(Date.isValidDateFormat(" ")); // spaces only
        assertFalse(Date.isValidDateFormat("91")); // less than 3 numbers
        assertFalse(Date.isValidDateFormat("phone")); // non-numeric
        assertFalse(Date.isValidDateFormat("70/01/2019")); // alphabets within digits
        assertFalse(Date.isValidDateFormat("31/02/2019")); // spaces within digits

        // valid phone numbers
        assertTrue(Date.isValidDateFormat("12/12/2018")); // exactly 3 numbers
        assertTrue(Date.isValidDateFormat("31/01/2019"));
        assertTrue(Date.isValidDateFormat("20/03/2019")); // long phone numbers
    }
}
