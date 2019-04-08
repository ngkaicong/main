package systemtests;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.budgeteer.logic.commands.CommandTestUtil.CASHFLOW_DESC_AMY;
import static seedu.budgeteer.logic.commands.CommandTestUtil.CASHFLOW_DESC_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.budgeteer.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.INVALID_CASHFLOW_DESC;
import static seedu.budgeteer.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.budgeteer.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.budgeteer.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.budgeteer.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.budgeteer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_CASHFLOW_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.budgeteer.testutil.TypicalEntrys.AMY;
import static seedu.budgeteer.testutil.TypicalEntrys.BOB;
import static seedu.budgeteer.testutil.TypicalEntrys.CHICKENRICE;
import static seedu.budgeteer.testutil.TypicalEntrys.IDA;
import static seedu.budgeteer.testutil.TypicalEntrys.KEYWORD_MATCHING_BURSARY;
import static seedu.budgeteer.testutil.TypicalEntrys.MALA;

import org.junit.Test;

import seedu.budgeteer.commons.core.Messages;
import seedu.budgeteer.commons.core.index.Index;
import seedu.budgeteer.logic.commands.AddCommand;
import seedu.budgeteer.logic.commands.RedoCommand;
import seedu.budgeteer.logic.commands.UndoCommand;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.entry.CashFlow;
import seedu.budgeteer.model.entry.Date;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.tag.Tag;
import seedu.budgeteer.testutil.EntryBuilder;
import seedu.budgeteer.testutil.EntryUtil;

public class AddCommandSystemTest extends EntriesBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a entry without tags to a non-empty budgeteer book, command with leading spaces and trailing spaces
         * -> added
         */
        Entry toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  "
                + DATE_DESC_AMY + "   " + CASHFLOW_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEntry(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a entry with all fields same as another entry in the budgeteer book except name -> added */
        toAdd = new EntryBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + CASHFLOW_DESC_AMY + DATE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a entry with all fields same as another entry in the budgeteer book except phone and email
         * -> added
         */
        toAdd = new EntryBuilder(AMY).withCashFlow(VALID_CASHFLOW_BOB).withDate(VALID_DATE_BOB).build();
        command = EntryUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty budgeteer book -> added */
        deleteAllEntrys();
        assertCommandSuccess(CHICKENRICE);

        /* Case: add a entry with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + CASHFLOW_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + DATE_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a entry, missing tags -> added */
        assertCommandSuccess(MALA);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the entry list before adding -> added */
        showEntrysWithName(KEYWORD_MATCHING_BURSARY);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a entry card is selected --------------------------- */

        /* Case: selects first card in the entry list, add a entry -> added, card selection remains unchanged */
        // TODO: CHECK IF THIS IS RIGHT OR WRONG
        selectEntry(Index.fromOneBased(1));
        assertCommandSuccess(MALA);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */
        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + DATE_DESC_AMY + CASHFLOW_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing cashflow -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CASHFLOW_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + CASHFLOW_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + EntryUtil.getEntryDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid date -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_CASHFLOW_DESC + DATE_DESC_AMY + CASHFLOW_DESC_AMY;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid cashflow -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_DATE_DESC + CASHFLOW_DESC_AMY;
        assertCommandFailure(command, CashFlow.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DATE_DESC_AMY + CASHFLOW_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code EntryListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Entry toAdd) {
        assertCommandSuccess(EntryUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Entry)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Entry)
     */
    private void assertCommandSuccess(String command, Entry toAdd) {
        Model expectedModel = getModel();
        expectedModel.addEntry(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Entry)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EntryListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Entry)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code EntryListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
