package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.budgeteer.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Test;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {

        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true,
                false, false);
        assertCommandSuccess(new HelpCommand(), model, commandHistory, expectedCommandResult.getFeedbackToUser(),
                expectedModel);
        //TODO: Not sure if expectedcommandresult.toString() works
    }
}

