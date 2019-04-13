package seedu.budgeteer.commons.model;

import seedu.budgeteer.model.ReadOnlyEntriesBook;

/** Indicates the EntriesBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyEntriesBook data;

    public AddressBookChangedEvent(ReadOnlyEntriesBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of records " + data.getEntryList().size();
    }
}
