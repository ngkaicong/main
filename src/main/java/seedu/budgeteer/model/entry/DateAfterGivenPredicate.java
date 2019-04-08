package seedu.budgeteer.model.entry;

import java.util.function.Predicate;

/**
 * Tests that a {@code Entry}'s {@code Name} matches any of the keywords given.
 */
public class DateAfterGivenPredicate implements Predicate<Entry> {
    private final Date specifiedDate;

    public DateAfterGivenPredicate(Date specifiedDate) {
        this.specifiedDate = specifiedDate;
    }

    @Override
    public boolean test(Entry entry) {
        Date entryDate = entry.getDate();
        return entryDate.isAfter(specifiedDate) || entryDate.equals(specifiedDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAfterGivenPredicate // instanceof handles nulls
                && specifiedDate.equals(((DateAfterGivenPredicate) other).specifiedDate)); // state check
    }

}
