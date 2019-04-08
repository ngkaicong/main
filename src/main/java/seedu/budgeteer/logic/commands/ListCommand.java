package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.budgeteer.model.Model.PREDICATE_SHOW_ALL_ENTRYS;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;

/**
 * Lists all entrys in the budgeteer book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all entrys";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
