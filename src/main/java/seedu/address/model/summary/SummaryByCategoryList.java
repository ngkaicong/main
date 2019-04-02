package seedu.address.model.summary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CompareUtil;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Entry;
import seedu.address.model.tag.Tag;
import seedu.address.ui.SummaryEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class SummaryByCategoryList extends SummaryList {
    private HashMap<Set<Tag>, Summary<Set<Tag>>> summaryMap = new HashMap<>();

    public SummaryByCategoryList(List<Entry> entryList) {
        super();
        requireNonNull(entryList);
        for (Entry r : entryList) {
            addRecordToMap(r);
            updateTotals(r);
        }
    }

    @Override
    public ObservableList<SummaryEntry> getSummaryList() {

        List<SummaryEntry> list = summaryMap.keySet().stream().sorted(CompareUtil.compareTags())
                .map(k -> SummaryEntry.convertToUiFriendly(summaryMap.get(k)))
                .collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    public HashMap<Set<Tag>, Summary<Set<Tag>>> getSummaryMap() {
        return summaryMap;
    }

    /** Adds a entry to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Date} of entry, then entry is added to the summary.
     * Else, it creates a summary with the details of the entry.
     * @param entry given entry
     * @see Summary#add(Entry)
     */
    @Override
    protected void addRecordToMap(Entry entry) {
        Set<Tag> tags = entry.getTags();
        if (summaryMap.containsKey(tags)) {
            summaryMap.get(tags).add(entry);
        } else {
            summaryMap.put(tags, new Summary<>(entry, tags));
        }
    }

    @Override
    protected void updateTotals(Entry entry) {
        super.updateTotals(entry);
    }

    @Override
    public int size() {
        return summaryMap.size();
    }

    @Override
    public boolean isEmpty() {
        return summaryMap.size() == 0;
    }

    @Override
    public CashFlow getTotal() {
        return total;
    }

    @Override
    public CashFlow getTotalIncome() {
        return totalIncome;
    }

    @Override
    public CashFlow getTotalExpense() {
        return totalExpense;
    }

    @Override
    public String getIdentifierName() {
        return "Category";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByCategoryList // instanceof handles nulls
                && summaryMap.equals(((SummaryByCategoryList) other).summaryMap));
    }
}
