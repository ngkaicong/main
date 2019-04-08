package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.Model;

/**
 * Clears the budgeteer book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Bugeter has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setAddressBook(new EntriesBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
