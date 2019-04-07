package seedu.address.model.summary;

import javafx.collections.ObservableList;
import seedu.address.commons.util.MoneyUtil;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Entry;
import seedu.address.ui.SummaryEntry;

/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public abstract class SummaryList {

    protected CashFlow total;
    protected CashFlow totalIncome;
    protected CashFlow totalExpense;

    public SummaryList() {
        total = new CashFlow("-0");
        totalIncome = new CashFlow("-0");
        totalExpense = new CashFlow("-0");
    }

    /**
     * Returns the simple class name of the identifier used to categorise each entry in list
     */
    public abstract String getIdentifierName();

    public abstract CashFlow getTotal();

    public abstract CashFlow getTotalIncome();

    public abstract CashFlow getTotalExpense();

    /**
     * Returns the size of the internal map
     */
    public abstract int size();

    /**
     * Checks if the internal map is empty
     * @return true if empty and false if otherwise
     */
    public abstract boolean isEmpty();

    /** Adds a entry to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Date} of entry, then entry is added to the summary.
     * Else, it creates a summary with the details of the entry.
     * @param entry given entry
     * @see Summary#add(Entry)
     */
    protected abstract void addRecordToMap(Entry entry);

    /**
     * Converts internal map to a list of SummaryEntry objects and returns a read-only copy of that list
     * @return read-only list of SummaryEntry objects
     */
    public abstract ObservableList<SummaryEntry> getSummaryList();

    /** Update the total moneyflow, total income and total expense */
    protected void updateTotals(Entry entry) {
        CashFlow money = entry.getCashFlow();
        if (isExpense(money)) {
            totalExpense = MoneyUtil.add(totalExpense, money);
        } else {
            totalIncome = MoneyUtil.add(totalIncome, money);
        }
        total = MoneyUtil.add(total, money);
    }

    private boolean isExpense(CashFlow money) {
        return money.toDouble() < 0;
    }
}
