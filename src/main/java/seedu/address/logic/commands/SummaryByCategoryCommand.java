package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.ShowSummaryTableEvent;
import seedu.address.commons.util.DateUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.DateIsWithinIntervalPredicate;
import seedu.address.model.entry.Entry;
import seedu.address.model.summary.SummaryByCategoryList;
import seedu.address.model.summary.SummaryList;

import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

/** List all the summary of records within a period of time specified */
public class SummaryByCategoryCommand extends SummaryCommand {

    public static final String COMMAND_MODE_WORD = "category";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_MODE_WORD
            + ": Lists the summary for each category for a "
            + "period of time.\n A category refers to any set of tags that is assigned to a entry.\n"
            + " Parameters: "
            + PREFIX_DATE + "START_DATE " + "END_DATE "
            + "Example: " + COMMAND_WORD + " " + COMMAND_MODE_WORD + " "
            + PREFIX_DATE + "1-1-2018 " + "12-12-2018 ";

    public static final String MESSAGE_SUCCESS = "Listed summary for %d category(s)";
    public static final String FORMAT_TITLE_SUMMARY = "Summary by category from %s to %s";

    private static Logger logger = LogsCenter.getLogger(SummaryByCategoryCommand.class);

    private final Date startDate;
    private final Date endDate;
    private final Predicate<Entry> predicate;

    private SummaryList summaryList;

    public SummaryByCategoryCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.updateFilteredEntryList(predicate);
            summaryList = new SummaryByCategoryList(model.getFilteredEntryList());
            logger.info("Created SummaryByCategoryList: " + summaryList.size() + " summaries");
            String tabTitle = String.format(FORMAT_TITLE_SUMMARY, DateUtil.formatDate(startDate),
                    DateUtil.formatDate(endDate));
            EventsCenter.getInstance().post(new ShowSummaryTableEvent(summaryList, TOTAL_LABEL,
                    tabTitle));
        } catch (Exception e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, summaryList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByCategoryCommand // instanceof handles nulls
                && startDate.equals(((SummaryByCategoryCommand) other).startDate)
                && endDate.equals(((SummaryByCategoryCommand) other).endDate));
    }
}
