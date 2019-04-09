package seedu.budgeteer.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.budgeteer.commons.exceptions.IllegalValueException;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.ReadOnlyEntriesBook;
import seedu.budgeteer.model.entry.Entry;

/**
 * An Immutable EntriesBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_ENTRY = "Entrys list contains duplicate entry(s).";

    private final List<JsonAdaptedEntry> entrys = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given entrys.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("entrys") List<JsonAdaptedEntry> entrys) {
        this.entrys.addAll(entrys);
    }

    /**
     * Converts a given {@code ReadOnlyEntriesBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyEntriesBook source) {
        entrys.addAll(source.getEntryList().stream().map(JsonAdaptedEntry::new).collect(Collectors.toList()));
    }

    /**
     * Converts this budgeteer book into the model's {@code EntriesBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EntriesBook toModelType() throws IllegalValueException {
        EntriesBook entriesBook = new EntriesBook();
        for (JsonAdaptedEntry jsonAdaptedEntry : entrys) {
            Entry entry = jsonAdaptedEntry.toModelType();
            //if (entriesBook.hasEntry(entry)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_ENTRY);
            //}
            entriesBook.addEntry(entry);
        }
        return entriesBook;
    }

}
