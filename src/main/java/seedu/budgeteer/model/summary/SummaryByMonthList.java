package seedu.budgeteer.model.summary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.budgeteer.commons.util.CompareUtil;
import seedu.budgeteer.model.Month;
import seedu.budgeteer.model.entry.CashFlow;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.ui.SummaryEntry;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions.
 */
public class SummaryByMonthList extends SummaryList {

    protected HashMap<Month, Summary<Month>> summaryMap = new HashMap<>();

    public SummaryByMonthList(List<Entry> entryList) {
        super();
        requireNonNull(entryList);
        for (Entry r : entryList) {
            addRecordToMap(r);
            updateTotals(r);
        }
    }

    @Override
    public ObservableList<SummaryEntry> getSummaryList() {
        List<SummaryEntry> list = summaryMap.keySet().stream().sorted(CompareUtil.compareMonth())
                .map(k -> SummaryEntry.convertToUiFriendly(summaryMap.get(k)))
                .collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    public HashMap<Month, Summary<Month>> getSummaryMap() {
        return summaryMap;
    }

    /** Adds a entry to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Month} of entry, then entry is added to the summary.
     * Else, it creates a summary with the details of the entry.
     * @param entry given entry
     * @see Summary#add(Entry)
     */
    protected void addRecordToMap(Entry entry) {
        Month month = new Month(entry.getDate().getMonth(), entry.getDate().getYear());
        if (summaryMap.containsKey(month)) {
            summaryMap.get(month).add(entry);
        } else {
            summaryMap.put(month, new Summary<>(entry, month));
        }
    }

    @Override
    protected void updateTotals(Entry entry) {
        super.updateTotals(entry);
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
        return Month.class.getSimpleName();
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByMonthList // instanceof handles nulls
                && summaryMap.equals(((SummaryByMonthList) other).summaryMap));
    }
}
