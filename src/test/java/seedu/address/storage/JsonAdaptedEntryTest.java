package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEntrys.MALA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Name;
import seedu.address.testutil.Assert;

public class JsonAdaptedEntryTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_CASHFLOW = "+abc";
    private static final String INVALID_DATE = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = MALA.getName().toString();
    private static final String VALID_CASHFLOW = MALA.getCashFlow().toString();
    private static final String VALID_DATE = MALA.getDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = MALA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedEntry person = new JsonAdaptedEntry(MALA);
        assertEquals(MALA, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEntry person =
                new JsonAdaptedEntry(INVALID_NAME, VALID_CASHFLOW, VALID_DATE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEntry person = new JsonAdaptedEntry(null, VALID_CASHFLOW, VALID_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCashFlow_throwsIllegalValueException() {
        JsonAdaptedEntry person =
                new JsonAdaptedEntry(VALID_NAME, VALID_DATE, INVALID_CASHFLOW, VALID_TAGS);
        String expectedMessage = CashFlow.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCashFlow_throwsIllegalValueException() {
        JsonAdaptedEntry person = new JsonAdaptedEntry(VALID_NAME, VALID_DATE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CashFlow.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedEntry person =
                new JsonAdaptedEntry(VALID_NAME, INVALID_DATE, VALID_CASHFLOW, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedEntry person = new JsonAdaptedEntry(VALID_NAME, null, VALID_CASHFLOW, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedEntry person =
                new JsonAdaptedEntry(VALID_NAME, VALID_CASHFLOW, VALID_DATE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
