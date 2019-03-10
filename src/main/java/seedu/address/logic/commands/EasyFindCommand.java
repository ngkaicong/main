package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all entrys in address book who contain any of the specified argument keywords.
 * Keyword matching is case sensitive.
 */
public class EasyFindCommand extends Command {
    public static final String COMMAND_WORD = "finds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entrys who contain any of "
            + "the specified keywords (non case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/John \n"
            + "Example: " + COMMAND_WORD + " d/12-01-2019\n"
            + "Example: " + COMMAND_WORD + " t/[Friends]";

    private final Predicate predicate;

    public EasyFindCommand(Predicate predicate) {
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
                || (other instanceof EasyFindCommand // instanceof handles nulls
                && this.predicate.equals(((EasyFindCommand) other).predicate)); // state check
    }

}
