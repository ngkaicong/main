package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntrys;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import guitests.guihandles.EntryCardHandle;
import guitests.guihandles.EntryListPanelHandle;

import org.junit.Test;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Name;

public class EntryListPanelTest extends GuiUnitTest {
    private static final ObservableList<Entry> TYPICAL_ENTRYS =
            FXCollections.observableList(getTypicalEntrys());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<>();
    private EntryListPanelHandle entryListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ENTRYS);

        for (int i = 0; i < TYPICAL_ENTRYS.size(); i++) {
            entryListPanelHandle.navigateToCard(TYPICAL_ENTRYS.get(i));
            Entry expectedEntry = TYPICAL_ENTRYS.get(i);
            EntryCardHandle actualCard = entryListPanelHandle.getEntryCardHandle(i);

            assertCardDisplaysEntry(expectedEntry, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedEntryChanged_selectionChanges() {
        initUi(TYPICAL_ENTRYS);
        Entry secondEntry = TYPICAL_ENTRYS.get(INDEX_SECOND_ENTRY.getZeroBased());
        guiRobot.interact(() -> selectedEntry.set(secondEntry));
        guiRobot.pauseForHuman();

        EntryCardHandle expectedEntry = entryListPanelHandle.getEntryCardHandle(INDEX_SECOND_ENTRY.getZeroBased());
        EntryCardHandle selectedEntry = entryListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedEntry, selectedEntry);
    }

    /**
     * Verifies that creating and deleting large number of entrys in {@code EntryListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Entry> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of entry cards exceeded time limit");
    }

    /**
     * Returns a list of entrys containing {@code entryCount} entrys that is used to populate the
     * {@code EntryListPanel}.
     */
    private ObservableList<Entry> createBackingList(int entryCount) {
        ObservableList<Entry> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < entryCount; i++) {
            Name name = new Name(i + "a");
            Date date = new Date("31-12-1996");
            CashFlow cashflow = new CashFlow("-0.90");
            Entry entry = new Entry(name, date, cashflow, Collections.emptySet());
            backingList.add(entry);
        }
        return backingList;
    }

    /**
     * Initializes {@code entryListPanelHandle} with a {@code EntryListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EntryListPanel}.
     */
    private void initUi(ObservableList<Entry> backingList) {
        EntryListPanel entryListPanel =
                new EntryListPanel(backingList, selectedEntry, selectedEntry::set);
        uiPartRule.setUiPart(entryListPanel);

        entryListPanelHandle = new EntryListPanelHandle(getChildNode(entryListPanel.getRoot(),
                EntryListPanelHandle.ENTRY_LIST_VIEW_ID));
    }
}
