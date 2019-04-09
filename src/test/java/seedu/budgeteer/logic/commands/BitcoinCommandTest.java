package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;
import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccessWithoutString;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;

public class BitcoinCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccessWithoutString(new BitcoinCommand(), model, commandHistory, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccessWithoutString(new BitcoinCommand(), model, commandHistory, expectedModel);
    }

}
