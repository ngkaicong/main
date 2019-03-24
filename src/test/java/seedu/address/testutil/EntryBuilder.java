package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Entry objects.
 */
public class EntryBuilder {

    public static final String DEFAULT_NAME = "Income";
    public static final String DEFAULT_DATE = "11-01-2001";
    public static final String DEFAULT_CASHFLOW = "+111.00";

    private Name name;
    private Date date;
    private CashFlow cashFlow;
    private Set<Tag> tags;

    public EntryBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        cashFlow = new Income(DEFAULT_CASHFLOW);
        tags = new HashSet<>();
    }

    /**
     * Initializes the EntryBuilder with the data of {@code entryToCopy}.
     */
    public EntryBuilder(Entry entryToCopy) {
        name = entryToCopy.getName();
        date = entryToCopy.getDate();
        cashFlow = entryToCopy.getCashFlow();
        tags = new HashSet<>(entryToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Entry} that we are building.
     */
    public EntryBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Entry} that we are building.
     */
    public EntryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code CashFlow} of the {@code Entry} that we are building.
     */
    public EntryBuilder withCashFlow(String cashFlow) {
        if (Expense.isValidExpense(cashFlow)) {
            this.cashFlow = new Expense(cashFlow);
        } else if (Income.isValidIncome(cashFlow)) {
            this.cashFlow = new Income(cashFlow);
        }
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Entry} that we are building.
     */
    public EntryBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Entry build() {
        return new Entry(name, date, cashFlow, tags);
    }

}
