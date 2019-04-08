package seedu.budgeteer.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.budgeteer.testutil.TypicalEntrys.AMY;
import static seedu.budgeteer.testutil.TypicalEntrys.MALA;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.budgeteer.model.entry.Entry;


public class EntriesBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EntriesBook entriesBook = new EntriesBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), entriesBook.getEntryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entriesBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EntriesBook newData = getTypicalAddressBook();
        entriesBook.resetData(newData);
        assertEquals(newData, entriesBook);
    }

    @Test
    public void hasEntry_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        entriesBook.hasEntry(null);
    }

    @Test
    public void hasEntry_personNotInAddressBook_returnsFalse() {
        assertFalse(entriesBook.hasEntry(AMY));
    }

    @Test
    public void hasEntry_personInAddressBook_returnsTrue() {
        entriesBook.addEntry(AMY);
        assertTrue(entriesBook.hasEntry(AMY));
    }

    @Test
    public void getEntryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        entriesBook.getEntryList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        entriesBook.addListener(listener);
        entriesBook.addEntry(MALA);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        entriesBook.addListener(listener);
        entriesBook.removeListener(listener);
        entriesBook.addEntry(MALA);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyEntriesBook whose persons list can violate interface constraints.
     */
    private static class EntriesBookStub implements ReadOnlyEntriesBook {
        private final ObservableList<Entry> persons = FXCollections.observableArrayList();

        EntriesBookStub(Collection<Entry> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Entry> getEntryList() {
            return persons;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
