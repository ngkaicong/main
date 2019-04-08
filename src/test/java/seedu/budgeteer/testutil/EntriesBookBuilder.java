package seedu.budgeteer.testutil;

import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.entry.Entry;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code EntriesBook ab = new EntriesBookBuilder().withEntry("John", "Doe").build();}
 */
public class EntriesBookBuilder {

    private EntriesBook entriesBook;

    public EntriesBookBuilder() {
        entriesBook = new EntriesBook();
    }

    public EntriesBookBuilder(EntriesBook entriesBook) {
        this.entriesBook = entriesBook;
    }

    /**
     * Adds a new {@code Entry} to the {@code EntriesBook} that we are building.
     */
    public EntriesBookBuilder withEntry(Entry entry) {
        entriesBook.addEntry(entry);
        return this;
    }

    public EntriesBook build() {
        return entriesBook;
    }
}
