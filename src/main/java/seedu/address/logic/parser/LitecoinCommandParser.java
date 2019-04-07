package seedu.address.logic.parser;

import seedu.address.logic.commands.LitecoinCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class LitecoinCommandParser implements Parser<LitecoinCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand
     * and returns an ReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LitecoinCommand parse(String args) throws ParseException {
        return new LitecoinCommand();
    }


}
