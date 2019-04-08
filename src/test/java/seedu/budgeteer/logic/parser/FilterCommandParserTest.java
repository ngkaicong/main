package seedu.budgeteer.logic.parser;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.budgeteer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.budgeteer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.budgeteer.logic.commands.FilterCommand;
import seedu.budgeteer.model.entry.CashFlowContainsSpecifiedKeywordsPredicate;
import seedu.budgeteer.model.entry.NameContainsKeywordsPredicate;
import seedu.budgeteer.model.entry.DateContainsSpecifiedKeywordsPredicate;
import seedu.budgeteer.model.entry.TagContainsSpecifiedKeywordsPredicate;


public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPrefixWithEmptyArgs_throwsParseException() {
        // name prefix with empty args
        assertParseFailure(parser, "n/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // date number prefix with empty args
        assertParseFailure(parser, "d/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // cashflow prefix with empty args
        assertParseFailure(parser, "c/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // tag prefix with empty args
        assertParseFailure(parser, "t/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // Parse names with no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFilterCommand);

        // Parse names with multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ Alice  Bob    ", expectedFilterCommand);

        // Parse date numbers with no leading and trailing whitespaces
        expectedFilterCommand =
                new FilterCommand(
                        new DateContainsSpecifiedKeywordsPredicate(Arrays.asList("12-12-2018", "12-12-2018")));

        // Parse date numbers with multiple white spaces between keywords
        assertParseSuccess(parser, "   d/ 12-12-2019 12-12-2019 ", expectedFilterCommand);

        // Parse  cashflows with no leading and trailing whitespaces
        expectedFilterCommand =
                new FilterCommand(new CashFlowContainsSpecifiedKeywordsPredicate(
                        Arrays.asList("+100", "+100")));

        // Parse cashflows with multiple white spaces between keywords
        assertParseSuccess(parser, "   c/  +100.0 +100.9",
                expectedFilterCommand);

        // Parse  tags with no leading and trailing whitespaces
        expectedFilterCommand =
                new FilterCommand(new TagContainsSpecifiedKeywordsPredicate(
                        Arrays.asList("[friends]", "[important]")));

        // Parse tags with multiple white spaces between keywords
        assertParseSuccess(parser, "   t/    [friends]    [important]", expectedFilterCommand);
    }
}
