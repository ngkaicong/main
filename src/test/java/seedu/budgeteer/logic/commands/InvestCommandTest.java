package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccessWithoutString;
import static seedu.budgeteer.testutil.TypicalEntrys.getTypicalAddressBook;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.model.entry.Number;

public class InvestCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // zero inputs for interest and years
        Number zeros = new Number("0 0");
        assertCommandSuccessWithoutString(new InvestCommand(zeros), model, commandHistory, expectedModel);

        // integer inputs for interest and years
        Number integers = new Number("123 123");
        assertCommandSuccessWithoutString(new InvestCommand(integers), model, commandHistory, expectedModel);

        // double inputs for interest and years
        Number doubles = new Number("12.3 12.3");
        assertCommandSuccessWithoutString(new InvestCommand(doubles), model, commandHistory, expectedModel);

        // both inputs for interest and years
        Number both = new Number("123 12.3");
        assertCommandSuccessWithoutString(new InvestCommand(both), model, commandHistory, expectedModel);

        Number other = new Number("12.3 123");
        assertCommandSuccessWithoutString(new InvestCommand(other), model, commandHistory, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // zero inputs for interest and years
        Number zeros = new Number("0 0");
        assertCommandSuccessWithoutString(new InvestCommand(zeros), model, commandHistory, expectedModel);

        // integer inputs for interest and years
        Number integers = new Number("123 123");
        assertCommandSuccessWithoutString(new InvestCommand(integers), model, commandHistory, expectedModel);

        // double inputs for interest and years
        Number doubles = new Number("12.3 12.3");
        assertCommandSuccessWithoutString(new InvestCommand(doubles), model, commandHistory, expectedModel);

        // both inputs for interest and years
        Number both = new Number("123 12.3");
        assertCommandSuccessWithoutString(new InvestCommand(both), model, commandHistory, expectedModel);

        Number other = new Number("12.3 123");
        assertCommandSuccessWithoutString(new InvestCommand(other), model, commandHistory, expectedModel);
    }

    @Test
    public void execute_addressBook_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // invalid interest input
        Number invalidInterest = new Number("10..12 1012");
        assertCommandSuccess(new InvestCommand(invalidInterest), model, commandHistory,
                "Sorry, you entered an invalid number.\n"
                + "Numbers can only have one decimal point.", expectedModel);

        // invalid years input
        Number invalidYears = new Number("1012 10..12");
        assertCommandSuccess(new InvestCommand(invalidYears), model, commandHistory,
                "Sorry, you entered an invalid number.\n"
                + "Numbers can only have one decimal point.", expectedModel);

        // invalid years input
        Number invalidBoth = new Number("10..12 10..12");
        assertCommandSuccess(new InvestCommand(invalidBoth), model, commandHistory,
                "Sorry, you entered an invalid number.\n"
                + "Numbers can only have one decimal point.", expectedModel);

    }

}
