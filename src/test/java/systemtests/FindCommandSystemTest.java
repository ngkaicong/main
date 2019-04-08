package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.budgeteer.commons.core.Messages.MESSAGE_ENTRYS_LISTED_OVERVIEW;
import static seedu.budgeteer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.budgeteer.testutil.TypicalEntrys.CAIFAN;
import static seedu.budgeteer.testutil.TypicalEntrys.IDA;
import static seedu.budgeteer.testutil.TypicalEntrys.KEYWORD_MATCHING_BURSARY;
import static seedu.budgeteer.testutil.TypicalEntrys.MALA;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.budgeteer.commons.core.index.Index;
import seedu.budgeteer.logic.commands.DeleteCommand;
import seedu.budgeteer.logic.commands.FindCommand;
import seedu.budgeteer.logic.commands.RedoCommand;
import seedu.budgeteer.logic.commands.UndoCommand;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.tag.Tag;

public class FindCommandSystemTest extends EntriesBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple entrys in budgeteer book, command with leading spaces and trailing spaces
         * -> 2 entrys found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CAIFAN, IDA); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where entry list is displaying the entrys we are finding
         * -> 2 entrys found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry where entry list is not displaying the entry we are finding -> 1 entry found */
        command = FindCommand.COMMAND_WORD + " Carl";
        ModelHelper.setFilteredList(expectedModel, MALA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 keywords -> 2 entrys found */
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, CAIFAN, IDA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 keywords in reversed order -> 2 entrys found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 keywords with 1 repeat -> 2 entrys found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 matching keywords and 1 non-matching keyword
         * -> 2 entrys found
         */
        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same entrys in budgeteer book after deleting 1 of them -> 1 entry found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getEntryList().contains(CAIFAN));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, IDA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry in budgeteer book, keyword is same as name but of different case -> 1 entry found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry in budgeteer book, keyword is substring of name -> 0 entrys found */
        command = FindCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry in budgeteer book, name is substring of keyword -> 0 entrys found */
        command = FindCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry not in budgeteer book -> 0 entrys found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of entry in budgeteer book -> 0 entrys found */
        command = FindCommand.COMMAND_WORD + " " + IDA.getDate().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of entry in budgeteer book -> 0 entrys found */
        // TODO: Might have problem here
        command = FindCommand.COMMAND_WORD + " " + IDA.getCashFlow().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of entry in budgeteer book -> 0 entrys found */
        List<Tag> tags = new ArrayList<>(IDA.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a entry is selected -> selected card deselected */
        showAllEntrys();
        selectEntry(Index.fromOneBased(1));
        assertFalse(getEntryListPanel().getHandleToSelectedCard().getName().equals(IDA.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, IDA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find entry in empty budgeteer book -> 0 entrys found */
        deleteAllEntrys();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BURSARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, IDA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_ENTRYS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see EntriesBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_ENTRYS_LISTED_OVERVIEW, expectedModel.getFilteredEntryList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
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
