package systemtests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CASHFLOW_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CASHFLOW_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CASHFLOW_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CASHFLOW_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRYS;
import static seedu.address.testutil.TypicalEntrys.AMY;
import static seedu.address.testutil.TypicalEntrys.BOB;
import static seedu.address.testutil.TypicalEntrys.KEYWORD_MATCHING_BURSARY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EntryBuilder;

public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_ENTRY;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + DATE_DESC_BOB + " " + CASHFLOW_DESC_BOB + "  " + TAG_DESC_HUSBAND + " ";
        Entry editedEntry = new EntryBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedEntry);

        /* Case: undo editing the last entry in the list -> last entry restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last entry in the list -> last entry edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setEntry(getModel().getFilteredEntryList().get(INDEX_FIRST_ENTRY.getZeroBased()), editedEntry);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a entry with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + DATE_DESC_BOB
                + CASHFLOW_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a entry with new values same as another entry's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getEntryList().contains(BOB));
        index = INDEX_SECOND_ENTRY;
        assertNotEquals(getModel().getFilteredEntryList().get(index.getZeroBased()), BOB);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + DATE_DESC_BOB
                + CASHFLOW_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedEntry = new EntryBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedEntry);

        /* Case: edit a entry with new values same as another entry's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_ENTRY;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + DATE_DESC_AMY
                + CASHFLOW_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedEntry = new EntryBuilder(BOB).withDate(VALID_DATE_AMY).withCashFlow(VALID_CASHFLOW_AMY).build();
        assertCommandSuccess(command, index, editedEntry);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_ENTRY;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Entry entryToEdit = getModel().getFilteredEntryList().get(index.getZeroBased());
        editedEntry = new EntryBuilder(entryToEdit).withTags().build();
        assertCommandSuccess(command, index, editedEntry);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered entry list, edit index within bounds of address book and entry list -> edited */
        showEntrysWithName(KEYWORD_MATCHING_BURSARY);
        index = INDEX_FIRST_ENTRY;
        assertTrue(index.getZeroBased() < getModel().getFilteredEntryList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        entryToEdit = getModel().getFilteredEntryList().get(index.getZeroBased());
        editedEntry = new EntryBuilder(entryToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedEntry);

        /* Case: filtered entry list, edit index within bounds of address book but out of bounds of entry list
         * -> rejected
         */
        showEntrysWithName(KEYWORD_MATCHING_BURSARY);
        int invalidIndex = getModel().getAddressBook().getEntryList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a entry card is selected -------------------------- */

        /* Case: selects first card in the entry list, edit a entry -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllEntrys();
        index = INDEX_FIRST_ENTRY;
        selectEntry(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + DATE_DESC_AMY
                + CASHFLOW_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new entry's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredEntryList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid cashflow -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_CASHFLOW_DESC, CashFlow.MESSAGE_CASH_FLOW_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Entry, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Entry, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Entry editedEntry) {
        assertCommandSuccess(command, toEdit, editedEntry, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the entry at index {@code toEdit} being
     * updated to values specified {@code editedEntry}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Entry editedEntry,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setEntry(expectedModel.getFilteredEntryList().get(toEdit.getZeroBased()), editedEntry);
        expectedModel.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
