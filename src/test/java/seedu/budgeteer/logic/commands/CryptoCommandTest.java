package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccessWithoutString;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.model.entry.Name;

public class CryptoCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        Name letters = new Name("btc");
        assertCommandSuccessWithoutString(new CryptoCommand(letters), model, commandHistory, expectedModel);

        Name numbers = new Name("123");
        assertCommandSuccessWithoutString(new CryptoCommand(numbers), model, commandHistory, expectedModel);

        Name both = new Name("a1b");
        assertCommandSuccessWithoutString(new CryptoCommand(both), model, commandHistory, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Name letters = new Name("btc");
        assertCommandSuccessWithoutString(new CryptoCommand(letters), model, commandHistory, expectedModel);

        Name numbers = new Name("123");
        assertCommandSuccessWithoutString(new CryptoCommand(numbers), model, commandHistory, expectedModel);

        Name both = new Name("a1b");
        assertCommandSuccessWithoutString(new CryptoCommand(both), model, commandHistory, expectedModel);
    }

}
