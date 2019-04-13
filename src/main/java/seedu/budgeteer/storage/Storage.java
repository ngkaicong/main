package seedu.budgeteer.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.budgeteer.commons.exceptions.DataConversionException;
import seedu.budgeteer.model.ReadOnlyEntriesBook;
import seedu.budgeteer.model.ReadOnlyUserPrefs;
import seedu.budgeteer.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyEntriesBook> readEntriesBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyEntriesBook addressBook) throws IOException;

}
