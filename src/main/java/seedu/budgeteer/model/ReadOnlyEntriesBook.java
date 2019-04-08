package seedu.budgeteer.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.budgeteer.model.entry.Entry;


/**
 * Unmodifiable view of an budgeteer book
 */
public interface ReadOnlyEntriesBook extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */

    ObservableList<Entry> getEntryList();
}
