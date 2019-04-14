package seedu.budgeteer.logic.parser;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_YEARS;

import java.util.Set;
import java.util.stream.Stream;

import seedu.budgeteer.logic.commands.InvestCommand;
import seedu.budgeteer.logic.parser.exceptions.ParseException;
import seedu.budgeteer.model.entry.CashFlow;
import seedu.budgeteer.model.entry.Date;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.Name;
import seedu.budgeteer.model.entry.Number;
import seedu.budgeteer.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class InvestCommandParser implements Parser<InvestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InvestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INTEREST, PREFIX_YEARS);

        if (!arePrefixesPresent(argMultimap, PREFIX_INTEREST, PREFIX_YEARS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InvestCommand.MESSAGE_USAGE));
        }

        Number interest = ParserUtil.parseNumber(argMultimap.getValue(PREFIX_INTEREST).get());
        Number year = ParserUtil.parseNumber(argMultimap.getValue(PREFIX_YEARS).get());

        Number temp = new Number(interest.fullNumber + " " + year.fullNumber);

        return new InvestCommand(temp);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
