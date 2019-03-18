package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.EasyFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entry.NameContainsKeywordsPredicate;
import seedu.address.model.entry.DateContainsSpecifiedKeywordsPredicate;
import seedu.address.model.entry.TagContainsSpecifiedKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class EasyFindCommandParser implements Parser<EasyFindCommand>{
    /**
     * Parses the given {@code String} of arguments in the context of the FindSpecificCommand
     * and returns an FindSpecificCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EasyFindCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        /**
         * Used for initial separation of prefix and args.
         */

        final Pattern prefixFormat = Pattern.compile("(?<prefix>\\w/)(?<arguments>.*)");

        final Matcher matcher = prefixFormat.matcher(trimmedArgs);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EasyFindCommand.MESSAGE_USAGE));
        }

        final String prefix = matcher.group("prefix");
        final String arguments = matcher.group("arguments");

        String[] keyWords = arguments.split("\\s+");

        if (prefix.equals("n/")) {
            return new EasyFindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keyWords)));
        } else if (prefix.equals("d/")) {
            return new EasyFindCommand(new DateContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
        } else if (prefix.equals("t/")) {
            return new EasyFindCommand(new TagContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EasyFindCommand.MESSAGE_USAGE));
        }
    }

}