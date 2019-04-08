package seedu.budgeteer.logic.parser;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.budgeteer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.budgeteer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.budgeteer.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;

import org.junit.Test;

import seedu.budgeteer.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_ENTRY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
