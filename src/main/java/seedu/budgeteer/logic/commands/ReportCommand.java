package seedu.budgeteer.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.ReportEntryList;



/**
 * Filters all entrys in budgeteer book who contain any of the specified argument keywords.
 * Keyword matching is case sensitive.
 */
public class ReportCommand extends Command {
    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a report of the finances incurred "
            + "During the specified timeframe. Using the prefix insight/ gives a more detailed report.\n"
            + "Parameters: KEYWORD s/[start_date] e/[end_date] [insight/]...\n"
            + "Example: " + COMMAND_WORD + " report \n"
            + "Example: " + COMMAND_WORD + " report s/21-01-2019\n"
            + "Example: " + COMMAND_WORD + " report e/25-03-2019\n"
            + "Example: " + COMMAND_WORD + " report s/01-01-2018 e/31-12-2018";

    private final Predicate predicate;

    public ReportCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredEntryList(this.predicate);
        ObservableList<Entry> filteredList = model.getFilteredEntryList();
        ReportEntryList reportList = new ReportEntryList(filteredList);
        Double total = reportList.getTotal();
        Double income = reportList.getTotalIncome();
        Double expense = reportList.getTotalExpense();
        return new CommandResult("Overview (Income - Expenses): " + String.format("%.02f", total)
                + "\n" + "Total Income: " + String.format("%.02f", income)
                + "\n" + "Total Expenses: " + String.format("%.02f", expense),
                false, true, false);
    }
}
