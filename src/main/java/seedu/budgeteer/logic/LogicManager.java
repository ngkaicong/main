package seedu.budgeteer.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.budgeteer.commons.core.GuiSettings;
import seedu.budgeteer.commons.core.LogsCenter;
import seedu.budgeteer.commons.util.EncryptionUtil;
import seedu.budgeteer.logic.commands.Command;
import seedu.budgeteer.logic.commands.CommandResult;
import seedu.budgeteer.logic.commands.LockCommand;
import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.logic.parser.EntriesBookParser;
import seedu.budgeteer.logic.parser.exceptions.ParseException;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ReadOnlyEntriesBook;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.storage.PasswordManager;
import seedu.budgeteer.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final EntriesBookParser entriesBookParser;
    private boolean isLocked;
    private boolean addressBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        entriesBookParser = new EntriesBookParser();
        this.isLocked = PasswordManager.passwordExists();

        // Set addressBookModified to true whenever the models' budgeteer book is modified.
        model.getAddressBook().addListener(observable -> addressBookModified = true);

    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;

        CommandResult commandResult;

        if (isLocked) {
            tryUnlock(commandText);
            if (isLocked) {
                throw new CommandException(LockCommand.MESSAGE_WRONG_PASSWORD);
            } else {
                decryptFile();
                return new CommandResult("Welcome to Budgeter");
            }
        }

        try {
            Command command = entriesBookParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    /**
     * Method to decrypt file
     */
    private void decryptFile() {
        try {
            UserPrefs userPrefs = new UserPrefs();
            File file = new File(String.valueOf(userPrefs.getAddressBookFilePath()));
            EncryptionUtil.decrypt(file);
        } catch (IOException ioe) {
            logger.warning("File not found" + ioe.getMessage());
        }
    }

    /**
     * Checks with the PasswordManger on whether to unlock the program
     * @param commandText password provided
     * @throws CommandException if password file is corrupted
     */
    private void tryUnlock(String commandText) throws CommandException {

        try {
            isLocked = !PasswordManager.verifyPassword(commandText);
        } catch (IOException ioe) {
            throw new CommandException("Unable to open password file");
        }
    }



    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public ObservableList<Entry> getFilteredEntryList() {
        return model.getFilteredEntryList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Entry> selectedEntryProperty() {
        return model.selectedEntryProperty();
    }

    @Override
    public void setSelectedEntry(Entry entry) {
        model.setSelectedEntry(entry);
    }

    @Override
    public ReadOnlyEntriesBook getAddressBook() {
        return model.getAddressBook();
    }

}
