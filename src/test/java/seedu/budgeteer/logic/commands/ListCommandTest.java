package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.logic.commands.CommandTestUtil.showEntryAtIndex;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;
import static seedu.budgeteer.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;

import org.junit.Before;
import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
