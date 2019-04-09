package seedu.budgeteer.logic.commands;

import org.junit.Test;
import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.testutil.EntriesBookBuilder;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccessWithoutString;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

public class BitcoinCommandTest {

//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    private Model emptyModel = new ModelManager(new EntriesBookBuilder().build(), new UserPrefs());
//    private Model expectedEmptyModel = new ModelManager(new EntriesBookBuilder().build(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
//        expectedModel.commitAddressBook();

        assertCommandSuccessWithoutString(new BitcoinCommand(), model, commandHistory, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        expectedModel.setAddressBook(new EntriesBook());
//        expectedModel.commitAddressBook();

        assertCommandSuccessWithoutString(new BitcoinCommand(), model, commandHistory, expectedModel);
    }

}
