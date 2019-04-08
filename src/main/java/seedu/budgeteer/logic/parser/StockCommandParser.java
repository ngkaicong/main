package seedu.budgeteer.logic.parser;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.budgeteer.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.budgeteer.logic.commands.StockCommand;
import seedu.budgeteer.logic.parser.exceptions.ParseException;
import seedu.budgeteer.model.entry.Name;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class StockCommandParser implements Parser<StockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StockCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockCommand.MESSAGE_USAGE));
        }

        return new StockCommand(name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
