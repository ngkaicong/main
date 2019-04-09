package seedu.budgeteer.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.budgeteer.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.budgeteer.commons.util.CompareUtil;
import seedu.budgeteer.logic.commands.DisplayCommand;
import seedu.budgeteer.model.entry.exceptions.EntryNotFoundException;

/**
 * A list of entrys that enforces uniqueness between its elements and does not allow nulls.
 * A entry is considered unique by comparing using {@code Entry#isSameEntry(Entry)}. As such, adding and updating of
 * entrys uses Entry#isSameEntry(Entry) for equality so as to ensure that the entry being added or updated is
 * unique in terms of identity in the EntryList. However, the removal of a entry uses Entry#equals(Object) so
 * as to ensure that the entry with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Entry#isSameEntry(Entry)
 */
public class EntryList implements Iterable<Entry> {

    private final ObservableList<Entry> internalList = FXCollections.observableArrayList();
    private final ObservableList<Entry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent entry as the given argument.
     */
    public boolean contains(Entry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Adds a entry to the list.
     * The entry must not already exist in the list.
     */
    public void add(Entry toAdd) {
        requireNonNull(toAdd);
        /*
        if (contains(toAdd)) {
            throw new DuplicateEntryException();
        }
        */
        internalList.add(toAdd);
    }

    /**
     * Replaces the entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the list.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the list.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }
        /*
        if (!target.isSameEntry(editedEntry) && contains(editedEntry)) {
            throw new DuplicateEntryException();
        }
        */
        internalList.set(index, editedEntry);
    }

    /**
     * Removes the equivalent entry from the list.
     * The entry must exist in the list.
     */
    public void remove(Entry toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntrys(EntryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code entrys}.
     * {@code entrys} must not contain duplicate entrys.
     */
    public void setEntrys(List<Entry> entrys) {
        requireAllNonNull(entrys);
        /*
        if (!entrysAreUnique(entrys)) {
            throw new DuplicateEntryException();
        }
        */
        internalList.setAll(entrys);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Entry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Entry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryList // instanceof handles nulls
                        && internalList.equals(((EntryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code entrys} contains only unique entrys.
     */
    private boolean entrysAreUnique(List<Entry> entrys) {
        for (int i = 0; i < entrys.size() - 1; i++) {
            for (int j = i + 1; j < entrys.size(); j++) {
                if (entrys.get(i).isSameEntry(entrys.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sort the entries in the list according to their category, in ascending or descending order
     * @param category category to sort the entries with
     * @param ascending set to true to sort in ascending order, false for descending order
     */
    public void sortEntrys(String category, Boolean ascending) {
        switch (category) {

        case DisplayCommand.CATEGORY_NAME:
            if (!ascending) {
                internalList.sort(CompareUtil.compareNameAttribute().reversed());
            } else {
                internalList.sort(CompareUtil.compareNameAttribute());
            }
            break;

        case DisplayCommand.CATEGORY_DATE:
            if (!ascending) {
                internalList.sort(CompareUtil.compareDateAttribute().reversed());
            } else {
                internalList.sort(CompareUtil.compareDateAttribute());
            }
            break;

        case DisplayCommand.CATEGORY_CASHFLOW:
        case DisplayCommand.CATEGORY_CASH:
            if (!ascending) {
                internalList.sort(CompareUtil.compareCashflowAttribute().reversed());
            } else {
                internalList.sort(CompareUtil.compareCashflowAttribute());
            }
            break;

        default: break;
        }

    }
}
