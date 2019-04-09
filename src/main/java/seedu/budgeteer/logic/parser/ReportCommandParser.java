package seedu.budgeteer.logic.parser;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_INSIGHT;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.budgeteer.model.Model.PREDICATE_SHOW_ALL_ENTRYS;

import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.budgeteer.logic.commands.ReportCommand;
import seedu.budgeteer.logic.parser.exceptions.ParseException;
import seedu.budgeteer.model.entry.Date;
import seedu.budgeteer.model.entry.DateAfterGivenPredicate;
import seedu.budgeteer.model.entry.DateBeforeGivenPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ReportCommandParser implements Parser<ReportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand
     * and returns an ReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    private static Boolean showDetailedReport = false;

    public static Boolean isRequireDetailedReport() {
        return showDetailedReport;
    }

    /**
     * Parses the ReportCommand with the arguments given in order to determine predicates needed to filter the list
     * @param args
     * @return ReportCommand object initialized with the predicates
     * @throws ParseException
     */

    public ReportCommand parse(String args) throws ParseException {
        Date startDate = null;
        Date endDate = null;
        Predicate afterStartPredicate = null;
        Predicate beforeEndPredicate = null;
        Predicate finalPredicate = null;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_ENDDATE,
                PREFIX_INSIGHT);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReportCommand.MESSAGE_USAGE));
        }


        if (arePrefixesPresent(argMultimap, PREFIX_STARTDATE)) {
            //Get Entries from this date onwards
            startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get());
            afterStartPredicate = new DateAfterGivenPredicate(startDate);

        }

        if (arePrefixesPresent(argMultimap, PREFIX_ENDDATE)) {
            //Get Entries until this date
            endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get());
            beforeEndPredicate = new DateBeforeGivenPredicate(endDate);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_INSIGHT)) {
            showDetailedReport = true;
        } else {
            showDetailedReport = false;
        }

        if (endDate != null && startDate != null && startDate.isAfter(endDate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReportCommand.MESSAGE_USAGE));
        } else if (endDate != null && startDate != null && !startDate.isAfter(endDate)) {
            finalPredicate = afterStartPredicate.and(beforeEndPredicate);
        } else if (endDate == null && startDate == null) {
            finalPredicate = PREDICATE_SHOW_ALL_ENTRYS;
        } else {
            finalPredicate = (afterStartPredicate == null) ? beforeEndPredicate : afterStartPredicate;
        }
        return new ReportCommand(finalPredicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
