package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DisplayCommand.ASCENDING_CONDITION;
import static seedu.address.logic.commands.DisplayCommand.CATEGORY_AND_ORDER_SPECIFIED;
import static seedu.address.logic.commands.DisplayCommand.CATEGORY_NAME;
import static seedu.address.logic.commands.DisplayCommand.CATEGORY_SET;
import static seedu.address.logic.commands.DisplayCommand.DESCENDING_CONDITION;
import static seedu.address.logic.commands.DisplayCommand.ONLY_CATEGORY_OR_ORDER_SPECIFIED;
import static seedu.address.logic.commands.DisplayCommand.ORDER_SET;

import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DisplayCommand object
 */
public class DisplayCommandParser implements Parser<DisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns a DisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE)));
        }

        String category;
        Boolean ascending;
        String[] argList = trimmedArgs.split("\\s+");

        if ((argList.length) == ONLY_CATEGORY_OR_ORDER_SPECIFIED) {
            if (!CATEGORY_SET.contains(argList[0].toLowerCase()) && !ORDER_SET.contains(argList[0].toLowerCase())) {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE)));
            }
            category = argList[0].toLowerCase();
            ascending = true;
            if (category.equals(DESCENDING_CONDITION)) {
                category = CATEGORY_NAME;
                ascending = false;
            } else if (category.equals(ASCENDING_CONDITION)) {
                category = CATEGORY_NAME;
            }
        } else if (argList.length == CATEGORY_AND_ORDER_SPECIFIED) {
            if ((ORDER_SET.contains(argList[0].toLowerCase()) || ORDER_SET.contains(argList[1].toLowerCase()))
                    && (CATEGORY_SET.contains(argList[0].toLowerCase())
                    || CATEGORY_SET.contains(argList[1].toLowerCase()))) {
                if (ORDER_SET.contains(argList[0].toLowerCase())) {
                    ascending = !(argList[0].toLowerCase().equals(DESCENDING_CONDITION));
                    category = argList[1].toLowerCase();
                } else {
                    ascending = (argList[1].toLowerCase().equals(ASCENDING_CONDITION));
                    category = argList[0].toLowerCase();
                }
            } else {
                throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE)));
            }
        } else {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE)));
        }

        return new DisplayCommand(category, ascending);
    }

}