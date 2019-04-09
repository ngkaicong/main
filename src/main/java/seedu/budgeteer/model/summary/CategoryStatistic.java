package seedu.budgeteer.model.summary;

import seedu.budgeteer.model.entry.CashFlow;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.tag.Tag;

import java.util.Set;

/**
 * This class represents a in memory model of the statistic of a category. It contains totalIncome and totalExpenses.
 */
public class CategoryStatistic {

    private Set<Tag> tags;
    private Double totalIncome = 0.0;
    private Double totalExpense = 0.0;

    public CategoryStatistic(Entry entry) {
        tags = entry.getTags();
        if (entry.getCashFlow().toDouble() == 0) {
            throw new IllegalStateException(CashFlow.MESSAGE_CONSTRAINTS);
        }
        if (isExpense(entry)) {
            totalExpense = Math.abs(entry.getCashFlow().toDouble());
        } else {
            totalIncome = Math.abs(entry.getCashFlow().toDouble());
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    /** Adds {@code CashFlow} of entry to {@code CategoryStatistic} */
    public void add(Entry entry) {
        assert(entry.getTags().equals(tags));
        if (isExpense(entry)) {
            totalExpense += Math.abs(entry.getCashFlow().toDouble());
            if (totalExpense > CashFlow.MAX_CASH) {
                throw new IllegalArgumentException(CashFlow.MESSAGE_CONSTRAINTS);
            }
        } else {
            totalIncome += Math.abs(entry.getCashFlow().toDouble());
            if (totalIncome > CashFlow.MAX_CASH) {
                throw new IllegalArgumentException(CashFlow.MESSAGE_CONSTRAINTS);
            }
        }
    }

    private boolean isExpense(Entry entry) {
        return entry.getCashFlow().toDouble() < 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryStatistic // instanceof handles nulls
                && tags.equals(((CategoryStatistic) other).tags)
                && totalIncome.equals(((CategoryStatistic) other).totalIncome)
                && totalExpense.equals(((CategoryStatistic) other).totalExpense));
    }
}

