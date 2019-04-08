package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;

/**
 * Classifies all entrys in the current displayed list by a specified category and/or order.
 * Keyword matching is case insensitive and regardless of order of entry.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final int ONLY_CATEGORY_OR_ORDER_SPECIFIED = 1;
    public static final int CATEGORY_AND_ORDER_SPECIFIED = 2;
    public static final String DESCENDING_CONDITION = "des";
    public static final String ASCENDING_CONDITION = "asc";

    public static final String MESSAGE_SUCCESS = "Entrys displayed by ";

    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_CASHFLOW = "cashflow";
    public static final String CATEGORY_CASH = "cash";
    public static final String CATEGORY_DATE = "date";

    public static final String ORDER_ASCENDING = "in ascending order";
    public static final String ORDER_DESCENDING = "in descending order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays sorted entrys in the currently list "
            + "by the specified category and order.\n"
            + "Parameters: [CATEGORY] [ORDER]\n"
            + "Example: " + COMMAND_WORD + " " + CATEGORY_NAME + " " + DESCENDING_CONDITION;

    public static final Set<String> CATEGORY_SET = new HashSet<>(Arrays.asList(CATEGORY_NAME, CATEGORY_CASHFLOW,
            CATEGORY_CASH, CATEGORY_DATE));
    public static final Set<String> ORDER_SET = new HashSet<>(Arrays.asList(DESCENDING_CONDITION, ASCENDING_CONDITION));

    private final String category;
    private final Boolean ascending;

    public DisplayCommand(String category, Boolean ascending) {
        this.category = category;
        this.ascending = ascending;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortFilteredEntryList(category, ascending);
        String returnMessageCategory = category;
        String returnMessageOrder = ascending ? ORDER_ASCENDING : ORDER_DESCENDING;
        return new CommandResult(MESSAGE_SUCCESS + returnMessageCategory + " "
                + returnMessageOrder + ".\n");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayCommand // instanceof handles nulls
                && category.equals(((DisplayCommand) other).category)
                && ascending.equals(((DisplayCommand) other).ascending)); // state check
    }
}
