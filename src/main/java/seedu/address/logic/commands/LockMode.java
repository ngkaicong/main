package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.PasswordManager;


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