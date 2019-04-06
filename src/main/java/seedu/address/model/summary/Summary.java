package seedu.address.model.summary;

import seedu.address.commons.util.MoneyUtil;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Entry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * This class represents a summary in AddressBook which records can be added to. Type T is the class of the
 * identifier that the summary is associated with
 */
public class Summary<Identifier> {

    private Identifier identifier;
    private CashFlow totalExpense;
    private CashFlow totalIncome;
    private CashFlow total;

    public Summary(Entry entry, Identifier identifier) {
        requireAllNonNull(entry, identifier);
        this.identifier = identifier;
        CashFlow money = entry.getCashFlow();
        if (isExpense(money)) {
            totalExpense = money;
            totalIncome = new CashFlow(CashFlow.REPRESENTATION_ZERO);
        } else {
            totalIncome = money;
            totalExpense = new CashFlow(CashFlow.REPRESENTATION_ZERO);
        }
        total = money;
    }

    public Summary(Identifier identifier, CashFlow totalExpense, CashFlow totalIncome, CashFlow total) {
        requireAllNonNull(identifier, totalExpense, totalIncome, total);
        this.identifier = identifier;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.total = total;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public CashFlow getTotalExpense() {
        return totalExpense;
    }

    public CashFlow getTotalIncome() {
        return totalIncome;
    }

    public CashFlow getTotal() {
        return total;
    }

    /**
     * Adds entry into the summary object
     * @param entry entry to be added
     */
    public void add(Entry entry) {
        requireNonNull(entry);
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

    @Override
    public boolean equals(Object other) {
        return this == other // short circuit if same object
                || (other instanceof Summary // instanceof handles nulls
                && identifier.equals(((Summary) other).identifier)
                && totalExpense.equals(((Summary) other).totalExpense)
                && totalIncome.equals(((Summary) other).totalIncome)
                && total.equals(((Summary) other).total));
    }

    @Override
    public String toString() {
        return identifier.getClass().getSimpleName() + ": " + identifier + "\n"
                + "Total Expense: " + totalExpense + "\n"
                + "Total Income: " + totalIncome + "\n"
                + "Total: " + total;
    }
}
