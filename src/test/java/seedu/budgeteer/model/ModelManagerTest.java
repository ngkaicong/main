package seedu.budgeteer.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_CASHFLOW_BOB;
import static seedu.budgeteer.model.Model.PREDICATE_SHOW_ALL_ENTRYS;
import static seedu.budgeteer.testutil.TypicalEntrys.BOB;
import static seedu.budgeteer.testutil.TypicalEntrys.CAIFAN;
import static seedu.budgeteer.testutil.TypicalEntrys.MALA;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.budgeteer.commons.core.GuiSettings;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.NameContainsKeywordsPredicate;
import seedu.budgeteer.model.entry.exceptions.EntryNotFoundException;
import seedu.budgeteer.testutil.EntriesBookBuilder;
import seedu.budgeteer.testutil.EntryBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EntriesBook(), new EntriesBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedEntry());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("budgeteer/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/budgeteer/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setAddressBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("budgeteer/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasEntry_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEntry(null);
    }

    @Test
    public void hasEntry_entryNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEntry(MALA));
    }

    @Test
    public void hasEntry_entryInAddressBook_returnsTrue() {
        modelManager.addEntry(MALA);
        assertTrue(modelManager.hasEntry(MALA));
    }

    @Test
    public void deleteEntry_entryIsSelectedAndFirstEntryInFilteredEntryList_selectionCleared() {
        modelManager.addEntry(MALA);
        modelManager.setSelectedEntry(MALA);
        modelManager.deleteEntry(MALA);
        assertEquals(null, modelManager.getSelectedEntry());
    }

    @Test
    public void deleteEntry_entryIsSelectedAndSecondEntryInFilteredEntryList_firstEntrySelected() {
        modelManager.addEntry(MALA);
        modelManager.addEntry(BOB);
        assertEquals(Arrays.asList(MALA, BOB), modelManager.getFilteredEntryList());
        modelManager.setSelectedEntry(BOB);
        modelManager.deleteEntry(BOB);
        assertEquals(MALA, modelManager.getSelectedEntry());
    }

    @Test
    public void setEntry_entryIsSelected_selectedEntryUpdated() {
        modelManager.addEntry(MALA);
        modelManager.setSelectedEntry(MALA);
        Entry updatedAlice = new EntryBuilder(MALA).withCashFlow(VALID_CASHFLOW_BOB).build();
        modelManager.setEntry(MALA, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedEntry());
    }

    @Test
    public void getFilteredEntryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEntryList().remove(0);
    }

    @Test
    public void setSelectedEntry_entryNotInFilteredEntryList_throwsEntryNotFoundException() {
        thrown.expect(EntryNotFoundException.class);
        modelManager.setSelectedEntry(MALA);
    }

    @Test
    public void setSelectedEntry_entryInFilteredEntryList_setsSelectedEntry() {
        modelManager.addEntry(MALA);
        assertEquals(Collections.singletonList(MALA), modelManager.getFilteredEntryList());
        modelManager.setSelectedEntry(MALA);
        assertEquals(MALA, modelManager.getSelectedEntry());
    }

    @Test
    public void equals() {
        EntriesBook entriesBook = new EntriesBookBuilder().withEntry(MALA).withEntry(CAIFAN).build();
        EntriesBook differentEntriesBook = new EntriesBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(entriesBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(entriesBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different entriesBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEntriesBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = MALA.getName().fullName.split("\\s+");
        modelManager.updateFilteredEntryList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(entriesBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(entriesBook, differentUserPrefs)));
    }
}
