package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.*;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.model.entry.Name;

public class StockCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        Name letters = new Name("msft");
        assertCommandSuccessWithoutString(new StockCommand(letters), model, commandHistory, expectedModel);

        Name numbers = new Name("1234");
        assertCommandSuccessWithoutString(new StockCommand(numbers), model, commandHistory, expectedModel);

        Name both = new Name("a1b2");
        assertCommandSuccessWithoutString(new StockCommand(both), model, commandHistory, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Name letters = new Name("msft");
        assertCommandSuccessWithoutString(new StockCommand(letters), model, commandHistory, expectedModel);

        Name numbers = new Name("1234");
        assertCommandSuccessWithoutString(new StockCommand(numbers), model, commandHistory, expectedModel);

        Name both = new Name("a1b2");
        assertCommandSuccessWithoutString(new StockCommand(both), model, commandHistory, expectedModel);
    }

}
