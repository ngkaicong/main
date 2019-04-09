package seedu.budgeteer.logic.commands;

import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;

public class ReportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    //    @Test
    //    public void execute_report_throwsCommandException() {
    //        ReportCommand reportCommand = new ReportCommand();
    //
    //        assertCommandFailure(reportCommand, model, commandHistory, Messages.MESSAGE_UNKNOWN_COMMAND);
    //    }
}

