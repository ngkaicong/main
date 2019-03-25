package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

public class ReportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_report_throwsCommandException() {
        ReportCommand reportCommand = new ReportCommand();

        assertCommandFailure(reportCommand, model, commandHistory, Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}

