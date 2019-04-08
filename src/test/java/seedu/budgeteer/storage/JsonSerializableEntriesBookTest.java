package seedu.budgeteer.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.budgeteer.commons.exceptions.IllegalValueException;
import seedu.budgeteer.commons.util.JsonUtil;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.testutil.TypicalEntrys;

public class JsonSerializableEntriesBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEntriesBookTest");
    private static final Path TYPICAL_ENTRYS_FILE = TEST_DATA_FOLDER.resolve("typicalEntrysAddressBook.json");
    private static final Path INVALID_ENTRY_FILE = TEST_DATA_FOLDER.resolve("invalidEntryAddressBook.json");
    private static final Path DUPLICATE_ENTRY_FILE = TEST_DATA_FOLDER.resolve("duplicateEntryAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ENTRYS_FILE,
                JsonSerializableAddressBook.class).get();
        EntriesBook entriesBookFromFile = dataFromFile.toModelType();
        EntriesBook typicalPersonsEntriesBook = TypicalEntrys.getTypicalAddressBook();
        assertEquals(entriesBookFromFile, typicalPersonsEntriesBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_ENTRY_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ENTRY_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableAddressBook.MESSAGE_DUPLICATE_ENTRY);
        dataFromFile.toModelType();
    }

}
