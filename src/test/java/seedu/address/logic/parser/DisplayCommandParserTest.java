package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DisplayCommand;

public class DisplayCommandParserTest {
    private DisplayCommandParser parser = new DisplayCommandParser();

    @Test
    public void parse_firstFieldsPresent_success() {
        // Tag specified
        Command command = new DisplayCommand(DisplayCommand.CATEGORY_CASH, true);
        String inputCommand = DisplayCommand.CATEGORY_CASH;
        assertParseSuccess(parser, inputCommand, command);

        // Order specified
        command = new DisplayCommand(DisplayCommand.CATEGORY_NAME, false);
        inputCommand = DisplayCommand.DESCENDING_CONDITION;
        assertParseSuccess(parser, inputCommand, command);

        // Order specified
        command = new DisplayCommand(DisplayCommand.CATEGORY_NAME, true);
        inputCommand = DisplayCommand.ASCENDING_CONDITION;
        assertParseSuccess(parser, inputCommand, command);
    }

    @Test
    public void parse_bothFieldsPresent_success() {
        // Tag and order specified
        Command command = new DisplayCommand(DisplayCommand.CATEGORY_CASH, false);
        String inputCommand = DisplayCommand.CATEGORY_CASH + " "
                + DisplayCommand.DESCENDING_CONDITION;
        assertParseSuccess(parser, inputCommand, command);

        // Order and tag specified
        command = new DisplayCommand(DisplayCommand.CATEGORY_DATE, true);
        inputCommand = DisplayCommand.ASCENDING_CONDITION + " "
                + DisplayCommand.CATEGORY_DATE;
        assertParseSuccess(parser, inputCommand, command);
    }

    @Test
    public void parse_additionalWhitespacesPresent_success() {
        Command command = new DisplayCommand(DisplayCommand.CATEGORY_CASHFLOW, false);
        String inputCommand = "     " + DisplayCommand.CATEGORY_CASHFLOW + "     "
                + DisplayCommand.DESCENDING_CONDITION + "      ";
        assertParseSuccess(parser, inputCommand, command);
    }


    @Test
    public void parse_noFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE);
        String inputCommand = "";

        assertParseFailure(parser, inputCommand, expectedMessage);
    }

    @Test
    public void parse_invalidFieldPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE);
        String inputCommand = "invalidword";
        assertParseFailure(parser, inputCommand, expectedMessage);

        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE);
        inputCommand = DisplayCommand.ASCENDING_CONDITION + " " + "invalidword";
        assertParseFailure(parser, inputCommand, expectedMessage);
    }

    @Test
    public void parse_tooManyFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE);
        String inputCommand = DisplayCommand.DESCENDING_CONDITION + " " + DisplayCommand.CATEGORY_DATE + " "
                + "unnecessarystring" + " " + "morestrings";
        assertParseFailure(parser, inputCommand, expectedMessage);
    }
}