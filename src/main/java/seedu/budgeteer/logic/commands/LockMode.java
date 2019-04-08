package seedu.budgeteer.logic.commands;

import java.io.IOException;

import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.storage.PasswordManager;


/**
 * Represents the different modes for LockCommand
 */
public abstract class LockMode {

    private String pass;

    public LockMode(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    protected boolean passExists() {
        return PasswordManager.passwordExists();
    }

    public abstract CommandResult execute() throws IOException, CommandException;
}
