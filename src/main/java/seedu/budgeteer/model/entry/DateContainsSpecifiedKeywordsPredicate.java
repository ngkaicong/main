package seedu.budgeteer.model.entry;

import java.util.List;
import java.util.function.Predicate;

import seedu.budgeteer.commons.util.StringUtil;

/**
 * Tests that a {@code Entry}'s {@code Date} matches any of the keywords given.
 */
public class DateContainsSpecifiedKeywordsPredicate implements Predicate<Entry> {
    private final List<String> keywords;

    public DateContainsSpecifiedKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Entry entry) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(entry.getDate().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateContainsSpecifiedKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DateContainsSpecifiedKeywordsPredicate) other).keywords)); // state check
    }

}
