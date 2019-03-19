package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.UniqueEntryList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEntry comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEntryList entrys;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        entrys = new UniqueEntryList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Entrys in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the entry list with {@code entrys}.
     * {@code entrys} must not contain duplicate entrys.
     */
    public void setEntrys(List<Entry> entrys) {
        this.entrys.setEntrys(entrys);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setEntrys(newData.getEntryList());
    }

    //// entry-level operations

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the address book.
     */
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return entrys.contains(entry);
    }

    /**
     * Adds a entry to the address book.
     * The entry must not already exist in the address book.
     */
    public void addEntry(Entry p) {
        entrys.add(p);
        indicateModified();
    }

    /**
     * Replaces the given entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the address book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the address book.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);

        entrys.setEntry(target, editedEntry);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEntry(Entry key) {
        entrys.remove(key);
        indicateModified();
    }


    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return entrys.asUnmodifiableObservableList().size() + " entrys";
        // TODO: refine later
    }

    @Override
    public ObservableList<Entry> getEntryList() {
        return entrys.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && entrys.equals(((AddressBook) other).entrys));
    }

    @Override
    public int hashCode() {
        return entrys.hashCode();
    }
}
