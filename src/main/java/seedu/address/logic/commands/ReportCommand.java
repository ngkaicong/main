package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.ReportEntryList;



/**
 * Filters all entrys in address book who contain any of the specified argument keywords.
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
        //Double bitcoin = reportList.getBitcoin();
        return new CommandResult("Overview (Income - Expenses): " + String.format("%.02f", total)
                + "\n" + "Total Income: " + String.format("%.02f", income)
                + "\n" + "Total Expenses: " + String.format("%.02f", expense),
                false, true, false);
    }
}
