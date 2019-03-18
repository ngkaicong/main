package seedu.address.model.entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CASHFLOW_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEntrys.AMY;
import static seedu.address.testutil.TypicalEntrys.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.entry.exceptions.DuplicateEntryException;
import seedu.address.model.entry.exceptions.EntryNotFoundException;
import seedu.address.testutil.EntryBuilder;

public class UniqueEntryListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEntryList uniqueEntryList = new UniqueEntryList();

    @Test
    public void contains_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.contains(null);
    }

    @Test
    public void contains_entryNotInList_returnsFalse() {
        assertFalse(uniqueEntryList.contains(AMY));
    }

    @Test
    public void contains_entryInList_returnsTrue() {
        uniqueEntryList.add(AMY);
        assertTrue(uniqueEntryList.contains(AMY));
    }

    @Test
    //TODO: Check if test case is needed or not
    public void contains_entryWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEntryList.add(AMY);
        Entry editedAlice = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEntryList.contains(editedAlice));
    }

    @Test
    public void add_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.add(null);
    }

    @Test
    //TODO Check if want to retain test case -- there might be duplicate entries with same parameters
    public void add_duplicateEntry_throwsDuplicateEntryException() {
        uniqueEntryList.add(AMY);
        thrown.expect(DuplicateEntryException.class);
        uniqueEntryList.add(AMY);
    }

    @Test
    public void setEntry_nullTargetEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntry(null, AMY);
    }

    @Test
    public void setEntry_nullEditedEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntry(AMY, null);
    }

    @Test
    public void setEntry_targetEntryNotInList_throwsEntryNotFoundException() {
        thrown.expect(EntryNotFoundException.class);
        uniqueEntryList.setEntry(AMY, AMY);
    }

    @Test
    public void setEntry_editedEntryIsSameEntry_success() {
        uniqueEntryList.add(AMY);
        uniqueEntryList.setEntry(AMY, AMY);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(AMY);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    //TODO: Check if want to retain test case
    public void setEntry_editedEntryHasSameIdentity_success() {
        uniqueEntryList.add(AMY);
        Entry editedAlice = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEntryList.setEntry(AMY, editedAlice);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(editedAlice);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntry_editedEntryHasDifferentIdentity_success() {
        uniqueEntryList.add(AMY);
        uniqueEntryList.setEntry(AMY, BOB);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(BOB);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntry_editedEntryHasNonUniqueIdentity_throwsDuplicateEntryException() {
        uniqueEntryList.add(AMY);
        uniqueEntryList.add(BOB);
        thrown.expect(DuplicateEntryException.class);
        uniqueEntryList.setEntry(AMY, BOB);
    }

    @Test
    public void remove_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.remove(null);
    }

    @Test
    public void remove_entryDoesNotExist_throwsEntryNotFoundException() {
        thrown.expect(EntryNotFoundException.class);
        uniqueEntryList.remove(AMY);
    }

    @Test
    public void remove_existingEntry_removesEntry() {
        uniqueEntryList.add(AMY);
        uniqueEntryList.remove(AMY);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntrys_nullUniqueEntryList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntrys((UniqueEntryList) null);
    }

    @Test
    public void setEntrys_uniqueEntryList_replacesOwnListWithProvidedUniqueEntryList() {
        uniqueEntryList.add(AMY);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(BOB);
        uniqueEntryList.setEntrys(expectedUniqueEntryList);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntrys_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEntryList.setEntrys((List<Entry>) null);
    }

    @Test
    public void setEntrys_list_replacesOwnListWithProvidedList() {
        uniqueEntryList.add(AMY);
        List<Entry> entryList = Collections.singletonList(BOB);
        uniqueEntryList.setEntrys(entryList);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(BOB);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntrys_listWithDuplicateEntrys_throwsDuplicateEntryException() {
        List<Entry> listWithDuplicateEntrys = Arrays.asList(AMY, AMY);
        thrown.expect(DuplicateEntryException.class);
        uniqueEntryList.setEntrys(listWithDuplicateEntrys);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEntryList.asUnmodifiableObservableList().remove(0);
    }
}
