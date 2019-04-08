package seedu.budgeteer.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.budgeteer.commons.core.GuiSettings;
import seedu.budgeteer.model.entry.Entry;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRYS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' budgeteer book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' budgeteer book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces budgeteer book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyEntriesBook addressBook);

    /** Returns the EntriesBook */
    ReadOnlyEntriesBook getAddressBook();

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in the budgeteer book.
     */
    boolean hasEntry(Entry entry);

    /**
     * Deletes the given entry.
     * The entry must exist in the budgeteer book.
     */
    void deleteEntry(Entry target);

    /**
     * Adds the given entry.
     * {@code entry} must not already exist in the budgeteer book.
     */
    void addEntry(Entry entry);

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the budgeteer book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the budgeteer book.
     */
    void setEntry(Entry target, Entry editedEntry);

    /** Returns an unmodifiable view of the filtered entry list */
    ObservableList<Entry> getFilteredEntryList();

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEntryList(Predicate<Entry> predicate);

    /**
     * Returns true if the model has previous budgeteer book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone budgeteer book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's budgeteer book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's budgeteer book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current budgeteer book state for undo/redo.
     */
    void commitAddressBook();


    /**
     * Updates the filtered record list to sort by the given {@code category}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void sortFilteredEntryList(String category, Boolean reversed);

    /**
     * Selected entry in the filtered entry list.
     * null if no entry is selected.
     */
    ReadOnlyProperty<Entry> selectedEntryProperty();

    /**
     * Returns the selected entry in the filtered entry list.
     * null if no entry is selected.
     */
    Entry getSelectedEntry();

    /**
     * Sets the selected entry in the filtered entry list.
     */
    void setSelectedEntry(Entry entry);

}
