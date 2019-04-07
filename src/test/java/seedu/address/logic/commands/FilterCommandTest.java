package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ENTRYS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalEntrys.INDO;
import static seedu.address.testutil.TypicalEntrys.CAIFAN;
import static seedu.address.testutil.TypicalEntrys.WORK;
import static seedu.address.testutil.TypicalEntrys.CHICKENRICE;
import static seedu.address.testutil.TypicalEntrys.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entry.*;
import seedu.address.model.entry.Entry;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        DateContainsSpecifiedKeywordsPredicate firstDatePredicate =
                new DateContainsSpecifiedKeywordsPredicate(Collections.singletonList("12345678"));

        CashFlowContainsSpecifiedKeywordsPredicate firstCashFlowPredicate =
                new CashFlowContainsSpecifiedKeywordsPredicate(Collections.singletonList("first@email.com"));

        TagContainsSpecifiedKeywordsPredicate firstTagPredicate =
                new TagContainsSpecifiedKeywordsPredicate(Collections.singletonList("[important]"));

        FilterCommand findFirstNameCommand = new FilterCommand(firstNamePredicate);
        FilterCommand findSecondNameCommand = new FilterCommand(secondNamePredicate);
        FilterCommand findFirstDateCommand = new FilterCommand(firstDatePredicate);
        FilterCommand findFirstCashFlowCommand = new FilterCommand(firstCashFlowPredicate);
        FilterCommand findFirstTagCommand = new FilterCommand(firstTagPredicate);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));

        // same object -> returns true
        assertTrue(findFirstDateCommand.equals(findFirstDateCommand));

        // same object -> returns true
        assertTrue(findFirstCashFlowCommand.equals(findFirstCashFlowCommand));

        // same object -> returns true
        assertTrue(findFirstTagCommand.equals(findFirstTagCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstNamePredicate);
        assertTrue(findFirstNameCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));

        // different entry -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));
    }

    /**
     * Find entry(s) by name(s).
     */

    @Test
    public void execute_zeroKeywords_invalidCommandFormat() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareFindByNameCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findElleByName_oneEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareFindByNameCommand("Elle");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(WORK));
    }

    @Test
    public void execute_findMultipleNames_multipleEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 3);
        FilterCommand command = prepareFindByNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CAIFAN, WORK, CHICKENRICE));
    }

    /**
     * Find entry(s) by phone(s).
     */
    @Test
    public void execute_findEllebyDate_oneEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareFindByDateCommand("12-12-2018");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(WORK));
    }

    @Test
    public void execute_findMultipleDates_multipleEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 3);
        FilterCommand command = prepareFindByDateCommand("12-12-2018 12-12-2018 12-12-2018");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CAIFAN, WORK, CHICKENRICE));
    }

    /**
     * Find entry(s) by email(s).
     */
    @Test
    public void execute_findEllebyCashFlow_oneEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareFindByCashFlowCommand("+100");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(WORK));
    }

    @Test
    public void execute_findMultipleCashFlows_multipleEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 3);
        FilterCommand command =
                prepareFindByCashFlowCommand("+100 -100 +100");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CAIFAN, WORK, CHICKENRICE));
    }

    /**
     * Find entry(s) by tag(s).
     */
    @Test
    public void execute_findATag_noEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareFindByTagCommand("[BOSS]");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findMultipleTags_noEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareFindByTagCommand("[owesMoney] [friend]");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(INDO));
    }

    /**
     * Creates a new FilterCommand using {@code NameContainsKeywordsPredicate}
     *
     * @param inputString full string of name(s) to find
     * @return a new FilterCommand using {@code NameContainsKeywordsPredicate}
     */
    private FilterCommand prepareFindByNameCommand (String inputString) {
        FilterCommand command = new FilterCommand(
                new NameContainsKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        return command;
    }

    /**
     * Creates a new FilterCommand using {@code DateContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of phone(s) to find
     * @return a new FilterCommand using {@code DateContainsSpecifiedKeywordsPredicate}
     */
    private FilterCommand prepareFindByDateCommand (String inputString) {
        FilterCommand command = new FilterCommand(
                new DateContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        return command;
    }

    /**
     * Creates a new FilterCommand using {@code CashFlowContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of email(s) to find
     * @return a new FilterCommand using {@code CashFlowContainsSpecifiedKeywordsPredicate}
     */
    private FilterCommand prepareFindByCashFlowCommand (String inputString) {
        FilterCommand command = new FilterCommand(
                new CashFlowContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        return command;
    }

    /**
     * Creates a new FilterCommand using {@code TagContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of Tag(s) to find
     * @return a new FilterCommand using {@code TagContainsSpecifiedKeywordsPredicate}
     */
    private FilterCommand prepareFindByTagCommand (String inputString) {
        FilterCommand command = new FilterCommand(
                new TagContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Entry>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage,
                                      List<Entry> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());

        assertEquals(expectedList, model.getFilteredEntryList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
