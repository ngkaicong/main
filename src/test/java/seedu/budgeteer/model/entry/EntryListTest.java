package seedu.budgeteer.model.entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_CASHFLOW_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.budgeteer.testutil.TypicalEntrys.AMY;
import static seedu.budgeteer.testutil.TypicalEntrys.BOB;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.budgeteer.model.entry.exceptions.EntryNotFoundException;
import seedu.budgeteer.testutil.EntryBuilder;

public class EntryListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EntryList entryList = new EntryList();

    @Test
    public void contains_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryList.contains(null);
    }

    @Test
    public void contains_entryNotInList_returnsFalse() {
        assertFalse(entryList.contains(AMY));
    }

    @Test
    public void contains_entryInList_returnsTrue() {
        entryList.add(AMY);
        assertTrue(entryList.contains(AMY));
    }

    @Test
    //TODO: Check if test case is needed or not
    public void contains_entryWithSameIdentityFieldsInList_returnsTrue() {
        entryList.add(AMY);
        Entry editedAlice = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(entryList.contains(editedAlice));
    }

    @Test
    public void add_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryList.add(null);
    }

    //    @Test
    //    //TODO Check if want to retain test case -- there might be duplicate entries with same parameters
    //    public void add_duplicateEntry_throwsDuplicateEntryException() {
    //        entryList.add(AMY);
    //        thrown.expect(DuplicateEntryException.class);
    //        entryList.add(AMY);
    //    }

    @Test
    public void setEntry_nullTargetEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryList.setEntry(null, AMY);
    }

    @Test
    public void setEntry_nullEditedEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryList.setEntry(AMY, null);
    }

    @Test
    public void setEntry_targetEntryNotInList_throwsEntryNotFoundException() {
        thrown.expect(EntryNotFoundException.class);
        entryList.setEntry(AMY, AMY);
    }

    @Test
    public void setEntry_editedEntryIsSameEntry_success() {
        entryList.add(AMY);
        entryList.setEntry(AMY, AMY);
        EntryList expectedEntryList = new EntryList();
        expectedEntryList.add(AMY);
        assertEquals(expectedEntryList, entryList);
    }

    @Test
    //TODO: Check if want to retain test case
    public void setEntry_editedEntryHasSameIdentity_success() {
        entryList.add(AMY);
        Entry editedAlice = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        entryList.setEntry(AMY, editedAlice);
        EntryList expectedEntryList = new EntryList();
        expectedEntryList.add(editedAlice);
        assertEquals(expectedEntryList, entryList);
    }

    @Test
    public void setEntry_editedEntryHasDifferentIdentity_success() {
        entryList.add(AMY);
        entryList.setEntry(AMY, BOB);
        EntryList expectedEntryList = new EntryList();
        expectedEntryList.add(BOB);
        assertEquals(expectedEntryList, entryList);
    }

    //    @Test
    //    public void setEntry_editedEntryHasNonUniqueIdentity_throwsDuplicateEntryException() {
    //        entryList.add(AMY);
    //        entryList.add(BOB);
    //        thrown.expect(DuplicateEntryException.class);
    //        entryList.setEntry(AMY, BOB);
    //    }

    @Test
    public void remove_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryList.remove(null);
    }

    @Test
    public void remove_entryDoesNotExist_throwsEntryNotFoundException() {
        thrown.expect(EntryNotFoundException.class);
        entryList.remove(AMY);
    }

    @Test
    public void remove_existingEntry_removesEntry() {
        entryList.add(AMY);
        entryList.remove(AMY);
        EntryList expectedEntryList = new EntryList();
        assertEquals(expectedEntryList, entryList);
    }

    @Test
    public void setEntrys_nullUniqueEntryList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryList.setEntrys((EntryList) null);
    }

    @Test
    public void setEntrys_uniqueEntryList_replacesOwnListWithProvidedEntryList() {
        entryList.add(AMY);
        EntryList expectedEntryList = new EntryList();
        expectedEntryList.add(BOB);
        entryList.setEntrys(expectedEntryList);
        assertEquals(expectedEntryList, entryList);
    }

    @Test
    public void setEntrys_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entryList.setEntrys((List<Entry>) null);
    }

    @Test
    public void setEntrys_list_replacesOwnListWithProvidedList() {
        entryList.add(AMY);
        List<Entry> entryList = Collections.singletonList(BOB);
        this.entryList.setEntrys(entryList);
        EntryList expectedEntryList = new EntryList();
        expectedEntryList.add(BOB);
        assertEquals(expectedEntryList, this.entryList);
    }

    //    @Test
    //    public void setEntrys_listWithDuplicateEntrys_throwsDuplicateEntryException() {
    //        List<Entry> listWithDuplicateEntrys = Arrays.asList(AMY, AMY);
    //        thrown.expect(DuplicateEntryException.class);
    //        entryList.setEntrys(listWithDuplicateEntrys);
    //    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        entryList.asUnmodifiableObservableList().remove(0);
    }
}
