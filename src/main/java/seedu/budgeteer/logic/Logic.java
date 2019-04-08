package seedu.budgeteer.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.budgeteer.commons.core.GuiSettings;
import seedu.budgeteer.logic.commands.CommandResult;
import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.logic.parser.exceptions.ParseException;
import seedu.budgeteer.model.ReadOnlyEntriesBook;
import seedu.budgeteer.model.entry.Entry;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the EntriesBook.
     *
     * @see seedu.budgeteer.model.Model#getAddressBook()
     */
    ReadOnlyEntriesBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of entrys */
    ObservableList<Entry> getFilteredEntryList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' budgeteer book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected entry in the filtered entry list.
     * null if no entry is selected.
     *
     * @see seedu.budgeteer.model.Model#selectedEntryProperty()
     */
    ReadOnlyProperty<Entry> selectedEntryProperty();

    /**
     * Sets the selected entry in the filtered entry list.
     *
     * @see seedu.budgeteer.model.Model#setSelectedEntry(Entry)
     */
    void setSelectedEntry(Entry entry);


}
