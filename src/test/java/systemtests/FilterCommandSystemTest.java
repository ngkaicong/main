package systemtests;

import static seedu.budgeteer.commons.core.Messages.MESSAGE_ENTRYS_LISTED_OVERVIEW;
import static seedu.budgeteer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.budgeteer.testutil.TypicalEntrys.CAIFAN;
import static seedu.budgeteer.testutil.TypicalEntrys.WORK;
import static seedu.budgeteer.testutil.TypicalEntrys.CHICKENRICE;
import static seedu.budgeteer.testutil.TypicalEntrys.KEYWORD_MATCHING_BURSARY;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.budgeteer.commons.core.index.Index;
import seedu.budgeteer.logic.commands.ClearCommand;
import seedu.budgeteer.logic.commands.DeleteCommand;
import seedu.budgeteer.logic.commands.FilterCommand;
import seedu.budgeteer.logic.commands.RedoCommand;
import seedu.budgeteer.logic.commands.UndoCommand;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.tag.Tag;

public class FilterCommandSystemTest extends EntriesBookSystemTest {
    private static final String NAME_PREFIX_WORD = "n/";
    private static final String DATE_PREFIX_WORD = "d/";
    private static final String CASH_FLOW_PREFIX_WORD = "c/";
    private static final String TAG_PREFIX_WORD = "t/";

    @Test
    public void find() {
        /* Case: find multiple entrys in budgeteer book, command with leading spaces and trailing spaces
         * -> 2 entrys found
         */
        String command = "   " + FilterCommand.COMMAND_WORD + " "
                + NAME_PREFIX_WORD + KEYWORD_MATCHING_BURSARY + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CAIFAN, CHICKENRICE); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where entry list is displaying the entrys we are finding
         * -> 2 entrys found
         */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + KEYWORD_MATCHING_BURSARY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry where entry list is not displaying the entry we are finding -> 1 entry found */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Carl";
        ModelHelper.setFilteredList(expectedModel, WORK);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 keywords -> 2 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, CAIFAN, CHICKENRICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 keywords in reversed order -> 2 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 keywords with 1 repeat -> 2 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple entrys in budgeteer book, 2 matching keywords and 1 non-matching keyword
         * -> 2 entrys found
         */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Daniel Benson NonMatchingKeyWord";
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
        assert !getModel().getAddressBook().getEntryList().contains(CAIFAN);
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + KEYWORD_MATCHING_BURSARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CHICKENRICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry in budgeteer book, keyword is same as name but of different case -> 1 entry found */
        command = FilterCommand.COMMAND_WORD +  " " + NAME_PREFIX_WORD + "bursu";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry in budgeteer book, keyword is substring of name -> 0 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Bur";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry in budgeteer book, name is substring of keyword -> 0 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "bursary";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find entry not in budgeteer book -> 0 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of entry in budgeteer book -> 1 entry found */
        command = FilterCommand.COMMAND_WORD + " " + DATE_PREFIX_WORD + CHICKENRICE.getDate().value;
        ModelHelper.setFilteredList(expectedModel, CHICKENRICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phone numbers of entrys in budgeteer book -> 2 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + DATE_PREFIX_WORD
                + CAIFAN.getDate().value + " " + WORK.getDate().value;
        ModelHelper.setFilteredList(expectedModel, CAIFAN, WORK);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find cashflow of entry in budgeteer book -> 1 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + CASH_FLOW_PREFIX_WORD + CHICKENRICE.getCashFlow().value;
        ModelHelper.setFilteredList(expectedModel, CHICKENRICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple cashflows of entrys in budgeteer book -> 2 entrys found */
        command = FilterCommand.COMMAND_WORD + " " + CASH_FLOW_PREFIX_WORD
                + WORK.getCashFlow().value + " " + CHICKENRICE.getCashFlow().value;
        ModelHelper.setFilteredList(expectedModel, WORK, CHICKENRICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of entry in budgeteer book -> 1 entrys found */
        List<Tag> tags = new ArrayList<>(CAIFAN.getTags());
        command = FilterCommand.COMMAND_WORD + " " + TAG_PREFIX_WORD + "[" + tags.get(0).tagName + "]";
        ModelHelper.setFilteredList(expectedModel, CAIFAN);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tags of entrys in budgeteer book -> 2 entrys found */
        tags = new ArrayList<>(WORK.getTags());
        String firstTag = "[" + tags.get(0).tagName + "]";
        tags = new ArrayList<>(CAIFAN.getTags());
        String secondTag = "[" + tags.get(0).tagName + "]";
        command = FilterCommand.COMMAND_WORD + " " + TAG_PREFIX_WORD + firstTag + " " + secondTag;
        ModelHelper.setFilteredList(expectedModel, CAIFAN, WORK);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a entry is selected -> selected card deselected */
        showAllEntrys();
        selectEntry(Index.fromOneBased(1));
        assert !getEntryListPanel().getHandleToSelectedCard().getName().equals(CHICKENRICE.getName().fullName);
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + "Daniel";
        ModelHelper.setFilteredList(expectedModel, CHICKENRICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find entry in empty budgeteer book -> 0 entrys found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getEntryList().size() == 0;
        command = FilterCommand.COMMAND_WORD + " " + NAME_PREFIX_WORD + KEYWORD_MATCHING_BURSARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CHICKENRICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNds " + NAME_PREFIX_WORD + KEYWORD_MATCHING_BURSARY;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    } //@@author

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
