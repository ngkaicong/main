package seedu.address.model.entry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Entry in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Entry {

    // Identity fields
    private final Name name;


    // Data fields
    private final Date date;
    private final CashFlow cashFlow;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Entry(Name name, Date date, CashFlow cashFlow, Set<Tag> tags) {
        requireAllNonNull(name, date, cashFlow, tags);
        this.name = name;
        this.date = date;
        this.cashFlow = cashFlow;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public CashFlow getCashFlow() {
        return cashFlow;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both entrys of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two entrys.
     */
    public boolean isSameEntry(Entry otherEntry) {
        if (otherEntry == this) {
            return true;
        }

        return otherEntry != null
                && otherEntry.getName().equals(getName())
                && (otherEntry.getDate().equals(getDate()) || otherEntry.getCashFlow().equals(getCashFlow()));
    }

    /**
     * Returns true if both entrys have the same identity and data fields.
     * This defines a stronger notion of equality between two entrys.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherEntry = (Entry) other;
        return otherEntry.getName().equals(getName())
                && otherEntry.getDate().equals(getDate())
                && otherEntry.getCashFlow().equals(getCashFlow())
                && otherEntry.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, cashFlow, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" CashFlow: ")
                .append(getCashFlow())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
