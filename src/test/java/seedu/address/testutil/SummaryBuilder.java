package seedu.address.testutil;

import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.summary.Summary;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.Set;

/**
 * A utility class to help build Summary objects for testing
 */
public class SummaryBuilder {

    public static final String DEFAULT_TOTAL_EXPENSE = "-10";
    public static final String DEFAULT_TOTAL_INCOME = "+300";
    public static final String DEFAULT_TOTAL = "+290";
    public static final String DEFAULT_TAG = "default";

    private CashFlow totalExpense;
    private CashFlow totalIncome;
    private CashFlow total;
    private Set<Tag> category;

    public SummaryBuilder() {

        category = SampleDataUtil.getTagSet(DEFAULT_TAG);
        totalExpense = new CashFlow(DEFAULT_TOTAL_EXPENSE);
        totalIncome = new CashFlow(DEFAULT_TOTAL_INCOME);
        total = new CashFlow(DEFAULT_TOTAL);
    }

    public SummaryBuilder(Entry entry) {

        category = entry.getTags();
        if (entry.getCashFlow().valueDouble > 0) {
            totalExpense = new CashFlow(CashFlow.REPRESENTATION_ZERO);
            totalIncome = entry.getCashFlow();
        } else {
            totalExpense = entry.getCashFlow();
            totalIncome = new CashFlow(CashFlow.REPRESENTATION_ZERO);
        }
        total = entry.getCashFlow();
    }

    /**
     * Sets the {@code category} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withCategory(String... tags) {
        this.category = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code totalExpense} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withTotalExpense(String totalExpense) {
        this.totalExpense = new CashFlow(totalExpense);
        return this;
    }

    /**
     * Sets the {@code totalIncome} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withTotalIncome(String totalIncome) {
        this.totalIncome = new CashFlow(totalIncome);
        return this;
    }

    /**
     * Sets the {@code total} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withTotal(String total) {
        this.total = new CashFlow(total);
        return this;
    }

    public Summary<Set<Tag>> buildCategorySummary() {
        return new Summary<>(category, totalExpense, totalIncome, total);
    }

}
