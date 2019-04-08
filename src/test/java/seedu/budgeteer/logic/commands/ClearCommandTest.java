package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new EntriesBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
