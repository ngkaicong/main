package seedu.address.model.entry;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Entry}'s {@code Cashflow} matches any of the keywords given.
 */
public class CashFlowContainsSpecifiedKeywordsPredicate implements Predicate<Entry> {
    private final List<String> keywords;

    public CashFlowContainsSpecifiedKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Entry entry) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(entry.getCashFlow().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CashFlowContainsSpecifiedKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((CashFlowContainsSpecifiedKeywordsPredicate) other).keywords)); // state check
    }

}