package seedu.address.logic.commands;

import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.ShowSummaryTableEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.DateIsWithinIntervalPredicate;
import seedu.address.model.summary.SummaryByCategoryList;
import seedu.address.model.summary.SummaryList;

import java.util.Collections;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEntrys.getTypicalAddressBook;

/**
 * Integration tests for SummaryByCategoryCommand
 */
public class SummaryByCategoryCommandTest {

    private Date sampleStartDate = new Date("2-3-2018");
    private Date sampleEndDate = new Date("10-5-2018");
    private Date sampleFutureStartDate = new Date("2-10-3018");
    private Date sampleFutureEndDate = new Date("10-12-3018");
    private Date samplePastStartDate = new Date("2-10-1018");
    private Date samplePastEndDate = new Date("10-12-1018");
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory;
    private EventListenerStub eventListenerStub;

    @Before
    public void setup() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();
        eventListenerStub = new EventListenerStub();
    }

    @Test
    public void equals() {
        SummaryByCategoryCommand summaryByCategoryCommandOne =
                new SummaryByCategoryCommand(sampleStartDate, sampleEndDate);
        SummaryByCategoryCommand summaryByCategoryCommandTwo =
                new SummaryByCategoryCommand(sampleEndDate, sampleEndDate);

        // same object -> returns true
        assertTrue(summaryByCategoryCommandOne.equals(summaryByCategoryCommandOne));

        // same values -> returns true
        SummaryByCategoryCommand summaryByCategoryCommandOneCopy =
                new SummaryByCategoryCommand(sampleStartDate, sampleEndDate);
        assertTrue(summaryByCategoryCommandOne.equals(summaryByCategoryCommandOneCopy));

        // different types -> returns false
        assertFalse(summaryByCategoryCommandOne.equals(1));

        // null -> returns false
        assertFalse(summaryByCategoryCommandOne.equals(null));

        // different values -> returns false
        assertFalse(summaryByCategoryCommandOne.equals(summaryByCategoryCommandTwo));
    }

    @Test
    public void execute_inputDateRangeNotOverlappingWithDataDateRange_noSummaryList() {
        try { // sampleFutureStartDate > sampleEndDate & sampleFutureEndDate > sampleFutureStartDate
            // -> no summaryList found
            SummaryByCategoryCommand command =
                    new SummaryByCategoryCommand(sampleFutureStartDate, sampleFutureEndDate);

            command.execute(model, commandHistory);
            expectedModel.updateFilteredEntryList(
                    new DateIsWithinIntervalPredicate(sampleFutureStartDate, sampleFutureEndDate));
            SummaryList summaryList = new SummaryByCategoryList(expectedModel.getFilteredEntryList());
            String expectedMessage = String.format(SummaryByCategoryCommand.MESSAGE_SUCCESS, summaryList.size());

            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredEntryList());
            assertEquals(summaryList, eventListenerStub.getSummaryList());

            // samplePastEndDate < sampleStartDate & samplePastStartDate < samplePastEndDate
            // -> no summaryList found
            command = new SummaryByCategoryCommand(samplePastStartDate, samplePastEndDate);

            command.execute(model, commandHistory);
            expectedModel.updateFilteredEntryList(
                    new DateIsWithinIntervalPredicate(samplePastStartDate, samplePastEndDate));
            summaryList = new SummaryByCategoryList(expectedModel.getFilteredEntryList());
            expectedMessage = String.format(SummaryByCategoryCommand.MESSAGE_SUCCESS, summaryList.size());

            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredEntryList());
            assertEquals(summaryList, eventListenerStub.getSummaryList());
        } catch (Exception e) {
            fail("This should not happen");
        }
    }

    @Test
    public void execute_inputDateRangeOverlappingWithDataDateRange_summaryList() {
        // Start date and end date are both within date range -> summaryList of all records in range
        try {
            SummaryByCategoryCommand command = new SummaryByCategoryCommand(sampleStartDate, sampleEndDate);

            command.execute(model, commandHistory);
            expectedModel.updateFilteredEntryList(
                    new DateIsWithinIntervalPredicate(sampleStartDate, sampleEndDate));
            SummaryList summaryList = new SummaryByCategoryList(expectedModel.getFilteredEntryList());
            String expectedMessage = String.format(SummaryByCategoryCommand.MESSAGE_SUCCESS, summaryList.size());

            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(expectedModel.getFilteredEntryList(), model.getFilteredEntryList());
            assertEquals(summaryList, eventListenerStub.getSummaryList());
        } catch (Exception e) {
            fail("This should not happen");
        }
    }

    public class EventListenerStub {

        private SummaryList summaryList;

        public EventListenerStub() {
            EventsCenter.getInstance().registerHandler(this);
        }

        @Subscribe
        private void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
            summaryList = event.data;
        }

        public SummaryList getSummaryList() {
            return summaryList;
        }
    }
}
