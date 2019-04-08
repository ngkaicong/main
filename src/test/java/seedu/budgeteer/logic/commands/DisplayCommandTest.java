package seedu.budgeteer.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.logic.commands.DisplayCommand.MESSAGE_SUCCESS;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.testutil.EntriesBookBuilder;

public class DisplayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model emptyModel = new ModelManager(new EntriesBookBuilder().build(), new UserPrefs());
    private Model expectedEmptyModel = new ModelManager(new EntriesBookBuilder().build(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        DisplayCommand sortCommandOne = new DisplayCommand(DisplayCommand.CATEGORY_NAME, true);
        DisplayCommand sortCommandTwo = new DisplayCommand(DisplayCommand.CATEGORY_CASH, false);
        DisplayCommand sortCommandOneCopy = new DisplayCommand(DisplayCommand.CATEGORY_NAME, true);
        DisplayCommand sortCommandDateTrue = new DisplayCommand(DisplayCommand.CATEGORY_DATE, true);
        DisplayCommand sortCommandNameFalse = new DisplayCommand(DisplayCommand.CATEGORY_NAME, false);

        // same object -> returns true
        assertEquals(sortCommandOne, sortCommandOne);
        // same values -> returns true
        assertEquals(sortCommandOne, sortCommandOneCopy);

        // different values -> return false
        assertNotEquals(sortCommandOne, sortCommandDateTrue);
        assertNotEquals(sortCommandOne, sortCommandNameFalse);
        assertNotEquals(sortCommandOne, sortCommandTwo);

        // different kind of objects -> return false
        assertNotEquals(sortCommandOne, null);
        assertNotEquals(sortCommandOne, "not_equal_string");
    }

    @Test
    public void execute_emptyList_listIsUnchanged() {
        DisplayCommand command = new DisplayCommand("name", true);
        String expectedMessage = String.format(DisplayCommand.MESSAGE_SUCCESS, "name", "ascending");

        assertCommandSuccess(command, emptyModel, commandHistory, expectedMessage, expectedEmptyModel);
    }

    @Test
    public void execute_unsortedList_listIsDisplayed() {
        DisplayCommand command = new DisplayCommand(DisplayCommand.CATEGORY_DATE, false);
        String expectedMessage = String.format(MESSAGE_SUCCESS, DisplayCommand.CATEGORY_DATE,
                DisplayCommand.ORDER_DESCENDING);
        // sort the initial model
        Model expectedDisplayedModel = getDisplayedModel(model, DisplayCommand.CATEGORY_DATE, DisplayCommand.ORDER_DESCENDING);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedDisplayedModel);
    }

    @Test
    public void execute_sortedList_listIsUnchanged() {
        DisplayCommand command = new DisplayCommand(DisplayCommand.CATEGORY_CASH, true);
        String expectedMessage = String.format(DisplayCommand.MESSAGE_SUCCESS, DisplayCommand.CATEGORY_CASH,
                DisplayCommand.ORDER_ASCENDING);
        // obtain a sorted model
        Model initialDisplayedModel = getDisplayedModel(model, DisplayCommand.CATEGORY_CASH, DisplayCommand.ORDER_ASCENDING);
        /// try to sorted it again, result should be identical model
        Model expectedModel = getDisplayedModel(initialDisplayedModel, DisplayCommand.CATEGORY_CASH,
                DisplayCommand.ORDER_ASCENDING);

        assertCommandSuccess(command, initialDisplayedModel, commandHistory, expectedMessage, expectedModel);
    }

    public Model getDisplayedModel(Model preDisplayedModel, String category, String order) {
        preDisplayedModel.sortFilteredEntryList(category, (order == DisplayCommand.ORDER_ASCENDING));
        return model;
    }
}
