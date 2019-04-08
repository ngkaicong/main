package seedu.budgeteer.logic.parser;

import seedu.budgeteer.logic.commands.BitcoinCommand;
import seedu.budgeteer.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class BitcoinCommandParser implements Parser<BitcoinCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand
     * and returns an ReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BitcoinCommand parse(String args) throws ParseException {
        return new BitcoinCommand();
    }

}
