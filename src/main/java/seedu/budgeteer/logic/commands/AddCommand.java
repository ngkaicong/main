package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_CASHFLOW;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.entry.Entry;

/**
 * Adds a entry to the budgeteer book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a entry to the budgeteer book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_CASHFLOW + "CASHFLOW "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Salary from John Doe "
            + PREFIX_DATE + "01-01-2019 "
            + PREFIX_CASHFLOW + "+100 "
            + PREFIX_TAG + "friends ";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the budgeteer book";

    private final Entry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Entry}
     */
    public AddCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        //        if (model.hasEntry(toAdd)) {
        //            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        //        }

        model.addEntry(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
