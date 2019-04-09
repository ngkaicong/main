package seedu.budgeteer.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_CASHFLOW;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.budgeteer.commons.core.index.Index;
import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.logic.commands.exceptions.CommandException;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.NameContainsKeywordsPredicate;
import seedu.budgeteer.testutil.EditEntryDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Allowance from Amy";
    public static final String VALID_NAME_BOB = "Payment from Bob";
    public static final String VALID_DATE_AMY = "11-10-2004";
    public static final String VALID_DATE_BOB = "11-02-2004";
    public static final String VALID_CASHFLOW_AMY = "+10.90";
    public static final String VALID_CASHFLOW_BOB = "-11.50";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String CASHFLOW_DESC_AMY = " " + PREFIX_CASHFLOW + VALID_CASHFLOW_AMY;
    public static final String CASHFLOW_DESC_BOB = " " + PREFIX_CASHFLOW + VALID_CASHFLOW_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "911a"; // alphabets not allowed in date
    public static final String INVALID_CASHFLOW_DESC = " "
            + PREFIX_CASHFLOW + "11."; // missing digit after decimal point
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEntryDescriptor DESC_AMY;
    public static final EditCommand.EditEntryDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditEntryDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDate(VALID_DATE_AMY).withCashFlow(VALID_CASHFLOW_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditEntryDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDate(VALID_DATE_BOB).withCashFlow(VALID_CASHFLOW_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccessWithoutString(Command command, Model actualModel,
                                                         CommandHistory actualCommandHistory,
                                                         Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the budgeteer book and the filtered entry list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        EntriesBook expectedEntriesBook = new EntriesBook(actualModel.getAddressBook());
        List<Entry> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEntryList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedEntriesBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredEntryList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the entry at the given {@code targetIndex} in the
     * {@code model}'s budgeteer book.
     */
    // TODO: Look at this code again
    public static void showEntryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEntryList().size());

        Entry entry = model.getFilteredEntryList().get(targetIndex.getZeroBased());
        final String[] splitName = entry.getName().fullName.split("\\s+");
        model.updateFilteredEntryList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEntryList().size());
    }

    /**
     * Deletes the first entry in {@code model}'s filtered list from {@code model}'s budgeteer book.
     */
    public static void deleteFirstEntry(Model model) {
        Entry firstEntry = model.getFilteredEntryList().get(0);
        model.deleteEntry(firstEntry);
        model.commitAddressBook();
    }

}
