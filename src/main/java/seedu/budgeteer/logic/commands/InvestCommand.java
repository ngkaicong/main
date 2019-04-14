package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_YEARS;
import static seedu.budgeteer.model.Model.PREDICATE_SHOW_ALL_ENTRYS;

import javafx.collections.ObservableList;
import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.Number;
import seedu.budgeteer.model.entry.ReportEntryList;

/**
 * Returns how much stock you can buy at the current market price.
 */
public class InvestCommand extends Command {

    public static final String COMMAND_WORD = "invest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculate long term investments based on current balance.\n"
            + "Parameters: "
            + PREFIX_INTEREST + "INTEREST RATE "
            + PREFIX_YEARS + "YEARS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INTEREST + "5 "
            + PREFIX_YEARS + "20 ";

    private final Number num;

    public InvestCommand(Number num) {
        this.num = num;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String messageReturn;

        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
        ObservableList<Entry> filteredList = model.getFilteredEntryList();
        ReportEntryList reportList = new ReportEntryList(filteredList);
        Double total = reportList.getTotal();

        String[] splited = num.fullNumber.split("\\s+");
        int interestCount = splited[0].length() - splited[0].replace(".", "").length();
        int yearCount = splited[1].length() - splited[1].replace(".", "").length();
        if ((interestCount > 1) || (yearCount > 1)) {
            messageReturn = "Sorry, you entered an invalid number.\n"
            + "Numbers can only have one decimal point.";
        } else {
            double interestRate = Double.parseDouble(splited[0]);
            double numYears = Double.parseDouble(splited[1]);

            double compound = total * (Math.pow((1 + interestRate / 100), numYears));
            double investment = (double) Math.round(compound * 100.0) / 100.0;

            String pre = "Your current balance is S$" + total + ".\n";
            String first = "At an interest rate of " + interestRate + "% for " + numYears + " years,";
            String second = " you would have S$" + investment + ".\nCongratulations!";

            messageReturn = pre + first + second;
        }

        return new CommandResult(messageReturn);
    }
}
