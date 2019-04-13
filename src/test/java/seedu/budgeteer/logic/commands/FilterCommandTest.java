package seedu.budgeteer.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.budgeteer.commons.core.Messages.MESSAGE_ENTRYS_LISTED_OVERVIEW;
import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.model.entry.CashFlowContainsSpecifiedKeywordsPredicate;
import seedu.budgeteer.model.entry.DateContainsSpecifiedKeywordsPredicate;
import seedu.budgeteer.model.entry.NameContainsKeywordsPredicate;
import seedu.budgeteer.model.entry.TagContainsSpecifiedKeywordsPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


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
        Predicate predicate = prepareFindByNamePredicate(" ");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

    }
    @Test
    public void execute_findMalaByName_oneEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        Predicate predicate = prepareFindByNamePredicate("Mala");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_findMultipleNames_multipleEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 3);
        Predicate predicate = prepareFindByNamePredicate("mala Ida Bursary");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Find entry(s) by phone(s).
     */
    @Test
    public void execute_findEllebyDate_oneEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        Predicate predicate = prepareFindByDatePredicate("19-02-2019");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_findMultipleDates_multipleEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 5);
        Predicate predicate = prepareFindByDatePredicate("19-02-2019 18-02-2019 10-02-2019");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Find entry(s) by cashflow(s).
     */
    @Test
    public void execute_findMalabyCashFlow_oneEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        Predicate predicate = prepareFindByCashFlowPredicate("-$10.50");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_findMultipleCashFlows_multipleEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 3);
        Predicate predicate = prepareFindByCashFlowPredicate("-$5.60 -$10.50 +$60.00");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Find entry(s) by tag(s).
     */
    @Test
    public void execute_findATag_noEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 0);
        Predicate predicate = prepareFindByTagPredicate("[BOSS]");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_findMultipleTags_noEntryFound() {
        String expectedMessage = String.format(MESSAGE_ENTRYS_LISTED_OVERVIEW, 1);
        Predicate predicate = prepareFindByTagPredicate("[owesCash] [friend]");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredEntryList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Creates a new FilterCommand using {@code NameContainsKeywordsPredicate}
     *
     * @param inputString full string of name(s) to find
     * @return a new FilterCommand using {@code NameContainsKeywordsPredicate}
     */
    private Predicate prepareFindByNamePredicate (String inputString) {

        return new NameContainsKeywordsPredicate(Arrays.asList(inputString.split("\\s+")));
    }

    /**
     * Creates a new FilterCommand using {@code DateContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of phone(s) to find
     * @return a new FilterCommand using {@code DateContainsSpecifiedKeywordsPredicate}
     */
    private Predicate prepareFindByDatePredicate (String inputString) {

        return new DateContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+")));
    }

    /**
     * Creates a new FilterCommand using {@code CashFlowContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of email(s) to find
     * @return a new FilterCommand using {@code CashFlowContainsSpecifiedKeywordsPredicate}
     */
    private Predicate prepareFindByCashFlowPredicate (String inputString) {

        return new CashFlowContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+")));
    }

    /**
     * Creates a new FilterCommand using {@code TagContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of Tag(s) to find
     * @return a new FilterCommand using {@code TagContainsSpecifiedKeywordsPredicate}
     */
    private Predicate prepareFindByTagPredicate (String inputString) {

        return new TagContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+")));
    }

}
