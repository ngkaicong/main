package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.PasswordManager;


/**
 * Contain methods to modify the password
 */
public class LockCommand extends Command {

    public static final String COMMAND_WORD = "lock";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set, change or remove password\n"
            + "Set Password Parameters:" + COMMAND_WORD + " set/yourchosenpassword\n"
            + "Change Password Parameters:" + COMMAND_WORD + " change/yournewpassword\n"
            + "Remove Password Parameters: " + COMMAND_WORD + " remove/youroldpassword\n";

    public static final String MESSAGE_SUCCESS = "Password set!";
    public static final String MESSAGE_PASSWORD_CHANGE = "Password successfully changed!";
    public static final String MESSAGE_PASSWORD_EXISTS = "Password already exists!";
    public static final String MESSAGE_PASSWORD_REMOVE = "Password removed!";
    public static final String MESSAGE_NO_PASSWORD_EXISTS = "No password!";
    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password!";

    private final LockMode mode;

    /**
     * Creates an LockCommand
     */
    public LockCommand(LockMode mode) {
        this.mode = mode;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            return mode.execute();
        } catch (IOException ioe) {
            throw new CommandException("Password File not found");
        }

    }

    /**
     * Set password if it does not exists
     */
    public static class setLock extends LockMode {
        public setLock(String password) {
            super(password);
        }

        @Override
        public CommandResult execute() throws IOException, CommandException {
            if (passExists()) {
                throw new CommandException(MESSAGE_PASSWORD_EXISTS);
            } else {
                PasswordManager.savePassword(getPass());
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
    }

    /**
     * Removes password if it exists
     */
    public static class clearLock extends LockMode {
        public clearLock(String password) {
            super(password);
        }

        @Override
        public CommandResult execute() throws IOException, CommandException {
            if (passExists()) {
                try {
                    PasswordManager.removePassword(getPass());
                } catch (WrongPasswordException e) {
                    throw new CommandException(MESSAGE_WRONG_PASSWORD);
                }
                return new CommandResult(MESSAGE_PASSWORD_REMOVE);
            } else {
                throw new CommandException(MESSAGE_NO_PASSWORD_EXISTS);
            }
        }
    }

    /**
     * Changes password if it exists
     */
    public static class changeLock extends LockMode {

        private String newPass;
        public changeLock(String newPassword) {
            super(newPassword);
            newPass = newPassword;
        }

        @Override
        public CommandResult execute() throws IOException {
            if (passExists()) {
                PasswordManager.savePassword(newPass);
                return new CommandResult(MESSAGE_PASSWORD_CHANGE);
            } else {
                return new CommandResult(MESSAGE_NO_PASSWORD_EXISTS);
            }
        }
    }
}
