package systemtests;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.budgeteer.testutil.TypicalEntrys.KEYWORD_MATCHING_BURSARY;

import org.junit.Test;

import seedu.budgeteer.commons.core.index.Index;
import seedu.budgeteer.logic.commands.ClearCommand;
import seedu.budgeteer.logic.commands.RedoCommand;
import seedu.budgeteer.logic.commands.UndoCommand;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;

public class ClearCommandSystemTest extends EntriesBookSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty budgeteer book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: undo clearing budgeteer book -> original budgeteer book restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo clearing budgeteer book -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: selects first card in entry list and clears budgeteer book -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original budgeteer book
        selectEntry(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardDeselected();

        /* Case: filters the entry list before clearing -> entire budgeteer book cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original budgeteer book
        showEntrysWithName(KEYWORD_MATCHING_BURSARY);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: clear empty budgeteer book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
