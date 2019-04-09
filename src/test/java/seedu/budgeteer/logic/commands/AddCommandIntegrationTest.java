package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.testutil.EntryBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newEntry_success() {
        Entry validEntry = new EntryBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEntry(validEntry);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validEntry), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validEntry), expectedModel);
    }

}
