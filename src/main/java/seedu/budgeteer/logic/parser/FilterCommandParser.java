package seedu.budgeteer.logic.parser;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_CASHFLOW;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.budgeteer.model.Model.PREDICATE_SHOW_ALL_ENTRYS;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.budgeteer.logic.commands.FilterCommand;
import seedu.budgeteer.logic.parser.exceptions.ParseException;
import seedu.budgeteer.model.entry.CashFlowContainsSpecifiedKeywordsPredicate;
import seedu.budgeteer.model.entry.DateContainsSpecifiedKeywordsPredicate;
import seedu.budgeteer.model.entry.NameContainsKeywordsPredicate;
import seedu.budgeteer.model.entry.TagContainsSpecifiedKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_CASHFLOW, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                && !arePrefixesPresent(argMultimap, PREFIX_DATE)
                && !arePrefixesPresent(argMultimap, PREFIX_CASHFLOW)
                && !arePrefixesPresent(argMultimap, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Predicate finalPredicate = PREDICATE_SHOW_ALL_ENTRYS;
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            String arguments = argMultimap.getValue(PREFIX_NAME).get();
            if (arguments.equalsIgnoreCase("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            String[] keyWords = arguments.split("\\s+");
            finalPredicate = (new NameContainsKeywordsPredicate(Arrays.asList(keyWords)));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            String arguments = argMultimap.getValue(PREFIX_DATE).get();
            if (arguments.equalsIgnoreCase("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            String[] keyWords = arguments.split("\\s+");
            finalPredicate = (new DateContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_CASHFLOW)) {
            String arguments = argMultimap.getValue(PREFIX_CASHFLOW).get();
            if (arguments.equalsIgnoreCase("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            String[] keyWords = arguments.split("\\s+");
            finalPredicate = (new CashFlowContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            String arguments = argMultimap.getValue(PREFIX_TAG).get();
            if (arguments.equalsIgnoreCase("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            String[] keyWords = arguments.split("\\s+");
            finalPredicate = (new TagContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
        }

        return new FilterCommand(finalPredicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
