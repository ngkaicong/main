package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.logic.commands.CommandTestUtil.deleteFirstEntry;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstEntry(model);
        deleteFirstEntry(model);

        deleteFirstEntry(expectedModel);
        deleteFirstEntry(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
