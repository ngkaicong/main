package seedu.address.model.entry;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.ModelManager;


/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class ReportEntryList {

    private Double total;
    private Double totalIncome;
    private Double totalExpense;
    private ObservableList<Entry>  filteredEntries;

    public ReportEntryList(ObservableList<Entry>  filteredEntries) {
        this.total = 0.0;
        this.totalIncome = 0.0;
        this.totalExpense = 0.0;
        this.filteredEntries = filteredEntries;

        updateTotals();
    }

    /**
     * Returns the simple class name of the identifier used to categorise each entry in list
     */
    public Double getTotal() {
        return this.total;
    }

    public Double getTotalIncome() {
        return this.totalIncome;
    }

    public Double getTotalExpense() {
        return this.totalExpense;
    }

    /**
     * Returns the size of the internal map
     */
    public int size() {
        return this.filteredEntries.size();
    }

    /**
     * Checks if the internal map is empty
     *
     * @return true if empty and false if otherwise
     */
    public boolean isEmpty() {
        return this.filteredEntries.isEmpty();
    }


    /**
     * Update the total moneyflow, total income and total expense
     */
    private void updateTotals() {
        for (Entry i : this.filteredEntries) {
            CashFlow icf = i.getCashFlow();
            Double value = icf.valueDouble;
            total += value;
            totalExpense += (value < 0) ? -1 * value : 0;
            totalIncome += (value >= 0) ? value : 0;
        }
    }
}
