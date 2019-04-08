package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.budgeteer.model.Model.PREDICATE_SHOW_ALL_ENTRYS;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.model.Model;

/**
 * Reverts the {@code model}'s budgeteer book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
