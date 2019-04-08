package seedu.budgeteer.model.entry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_CASHFLOW_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.budgeteer.testutil.TypicalEntrys.AMY;
import static seedu.budgeteer.testutil.TypicalEntrys.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.budgeteer.testutil.EntryBuilder;

public class EntryTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Entry person = new EntryBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        person.getTags().remove(0);
    }

    @Test
    public void isSameEntry() {
        // same object -> returns true
        assertTrue(AMY.isSameEntry(AMY));

        // null -> returns false
        assertFalse(AMY.isSameEntry(null));

        // different cashflow and date -> returns false
        Entry editedAlice = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).withDate(VALID_DATE_BOB).build();
        assertFalse(AMY.isSameEntry(editedAlice));

        // different name -> returns false
        editedAlice = new EntryBuilder(AMY).withName(VALID_NAME_BOB).build();
        assertFalse(AMY.isSameEntry(editedAlice));

        // same name, different attributes -> returns false
        editedAlice = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).withDate(VALID_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(AMY.isSameEntry(editedAlice));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Entry aliceCopy = new EntryBuilder(AMY).build();
        assertTrue(AMY.equals(aliceCopy));

        // same object -> returns true
        assertTrue(AMY.equals(AMY));

        // null -> returns false
        assertFalse(AMY.equals(null));

        // different type -> returns false
        assertFalse(AMY.equals(5));

        // different entry -> returns false
        assertFalse(AMY.equals(BOB));

        // different name -> returns false
        Entry editedAlice = new EntryBuilder(AMY).withName(VALID_NAME_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different cashflow -> returns false
        editedAlice = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different date -> returns false
        editedAlice = new EntryBuilder(AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EntryBuilder(AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(AMY.equals(editedAlice));
    }
}
