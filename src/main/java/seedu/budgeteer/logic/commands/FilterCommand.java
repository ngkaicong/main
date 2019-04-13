package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.budgeteer.commons.core.Messages;
import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;

/**
 * Filters all entrys in budgeteer book who contain any of the specified argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entrys who contain any of "
            + "the specified keywords (non case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " filter n/John \n"
            + "Example: " + COMMAND_WORD + " d/12-01-2019\n"
            + "Example: " + COMMAND_WORD + " t/[Friends]";

    private final Predicate predicate;

    public FilterCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEntryList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ENTRYS_LISTED_OVERVIEW, model.getFilteredEntryList().size()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }

}
