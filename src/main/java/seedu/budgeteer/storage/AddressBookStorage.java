package seedu.budgeteer.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.budgeteer.commons.exceptions.DataConversionException;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.ReadOnlyEntriesBook;

/**
 * Represents a storage for {@link EntriesBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns EntriesBook data as a {@link ReadOnlyEntriesBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEntriesBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyEntriesBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEntriesBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyEntriesBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyEntriesBook)
     */
    void saveAddressBook(ReadOnlyEntriesBook addressBook, Path filePath) throws IOException;

}
