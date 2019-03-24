package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.exceptions.EntryNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Entry> filteredEntrys;
    private final SimpleObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEntrys = new FilteredList<>(versionedAddressBook.getEntryList());
        filteredEntrys.addListener(this::ensureSelectedEntryIsValid);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }
    /**
     * removes tag from all entrys
     * @return
     */



    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return versionedAddressBook.hasEntry(entry);
    }

    @Override
    public void deleteEntry(Entry target) {
        versionedAddressBook.removeEntry(target);
    }

    @Override
    public void addEntry(Entry entry) {
        versionedAddressBook.addEntry(entry);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        versionedAddressBook.setEntry(target, editedEntry);
    }

    //=========== Filtered Entry List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Entry} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        return filteredEntrys;
    }

    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredEntrys.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Modifying Entry List Accessors =============================================================

    @Override
    public void sortFilteredEntryList(String category, Boolean ascending) {
        requireAllNonNull(category, ascending);
        versionedAddressBook.sortEntrys(category, ascending);
    }


    //=========== Selected entry ===========================================================================

    @Override
    public ReadOnlyProperty<Entry> selectedEntryProperty() {
        return selectedEntry;
    }

    @Override
    public Entry getSelectedEntry() {
        return selectedEntry.getValue();
    }

    @Override
    public void setSelectedEntry(Entry entry) {
        if (entry != null && !filteredEntrys.contains(entry)) {
            throw new EntryNotFoundException();
        }
        selectedEntry.setValue(entry);
    }

    /**
     * Ensures {@code selectedEntry} is a valid entry in {@code filteredEntrys}.
     */
    private void ensureSelectedEntryIsValid(ListChangeListener.Change<? extends Entry> change) {
        while (change.next()) {
            if (selectedEntry.getValue() == null) {
                // null is always a valid selected entry, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedEntryReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedEntry.getValue());
            if (wasSelectedEntryReplaced) {
                // Update selectedEntry to its new value.
                int index = change.getRemoved().indexOf(selectedEntry.getValue());
                selectedEntry.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedEntryRemoved = change.getRemoved().stream()
                    .anyMatch(removedEntry -> selectedEntry.getValue().isSameEntry(removedEntry));
            if (wasSelectedEntryRemoved) {
                // Select the entry that came before it in the list,
                // or clear the selection if there is no such entry.
                selectedEntry.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredEntrys.equals(other.filteredEntrys)
                && Objects.equals(selectedEntry.get(), other.selectedEntry.get());
    }

}
