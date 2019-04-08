package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.budgeteer.commons.core.Messages;
import seedu.budgeteer.commons.core.index.Index;
import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.entry.Entry;

/**
 * Selects a entry identified using it's displayed index from the budgeteer book.
 */
public class SelectCommand extends Command {

     public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the entry identified by the index number used in the displayed entry list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ENTRY_SUCCESS = "Selected Entry: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Entry> filteredEntryList = model.getFilteredEntryList();

        if (targetIndex.getZeroBased() >= filteredEntryList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        model.setSelectedEntry(filteredEntryList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_ENTRY_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
