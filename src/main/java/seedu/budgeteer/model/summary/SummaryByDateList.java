package seedu.budgeteer.model.summary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.budgeteer.commons.util.CompareUtil;
import seedu.budgeteer.model.entry.CashFlow;
import seedu.budgeteer.model.entry.Date;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.ui.SummaryEntry;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class SummaryByDateList extends SummaryList {
    private HashMap<Date, Summary<Date>> summaryMap = new HashMap<>();

    public SummaryByDateList(List<Entry> entryList) {
        super();
        requireNonNull(entryList);
        for (Entry r : entryList) {
            addRecordToMap(r);
            updateTotals(r);
        }
    }

    @Override
    public ObservableList<SummaryEntry> getSummaryList() {

        List<SummaryEntry> list = summaryMap.keySet().stream().sorted(CompareUtil.compareDate())
                .map(k -> SummaryEntry.convertToUiFriendly(summaryMap.get(k)))
                .collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    public HashMap<Date, Summary<Date>> getSummaryMap() {
        return summaryMap;
    }


    @Override
    protected void addRecordToMap(Entry entry) {
        Date date = entry.getDate();
        if (summaryMap.containsKey(date)) {
            summaryMap.get(date).add(entry);
        } else {
            summaryMap.put(date, new Summary<>(entry, date));
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
        return Date.class.getSimpleName();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByDateList // instanceof handles nulls
                && summaryMap.equals(((SummaryByDateList) other).summaryMap));
    }
}
