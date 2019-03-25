package seedu.address.logic.parser;

import seedu.address.logic.commands.ReportCommand;
import seedu.address.logic.commands.ReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entry.CashFlowContainsSpecifiedKeywordsPredicate;
import seedu.address.model.entry.DateContainsSpecifiedKeywordsPredicate;
import seedu.address.model.entry.NameContainsKeywordsPredicate;
import seedu.address.model.entry.TagContainsSpecifiedKeywordsPredicate;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ReportCommandParser implements Parser<ReportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand
     * and returns an ReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReportCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_ENDDATE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReportCommand.MESSAGE_USAGE));
        }

        if(arePrefixesPresent(argMultimap, PREFIX_STARTDATE, PREFIX_ENDDATE)){
            //TODO
        }
        else if(!arePrefixesPresent(argMultimap, PREFIX_STARTDATE)){
            //TODO
        }
        else if(!arePrefixesPresent(argMultimap, PREFIX_ENDDATE)){
            //TODO
        }


        return new ReportCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

//    /**
//     * Parses the given {@code String} of arguments in the context of the AddCommand
//     * and returns an AddCommand object for execution.
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public AddCommand parse(String args) throws ParseException {
//        ArgumentMultimap argMultimap =
//                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_CASHFLOW, PREFIX_TAG);
//
//        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE, PREFIX_CASHFLOW)
//                || !argMultimap.getPreamble().isEmpty()) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//        }
//
//        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
//        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
//        CashFlow cashFlow = ParserUtil.parseCashFlow(argMultimap.getValue(PREFIX_CASHFLOW).get());
//        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
//
//        Entry person = new Entry(name, date, cashFlow, tagList);
//
//        return new AddCommand(person);
//    }
//
//    /**
//     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
//     * {@code ArgumentMultimap}.
//     */
//    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
//        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
//    }

}
