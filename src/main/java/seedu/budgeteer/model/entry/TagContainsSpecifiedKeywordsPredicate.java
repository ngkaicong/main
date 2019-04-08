package seedu.budgeteer.model.entry;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.budgeteer.model.tag.Tag;


/**
 * Tests that a {@code Entry}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsSpecifiedKeywordsPredicate implements Predicate<Entry> {
    private final List<String> keywords;

    public TagContainsSpecifiedKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Entry entry) {
        Set<Tag> tags = entry.getTags();
        List<Tag> tagList = tags.stream().collect(Collectors.toList()); // Converts Set to List

        return keywords.stream()
                .anyMatch(keyword -> tagList.stream().anyMatch(tagname -> keyword.contains(tagname.toString())) & true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsSpecifiedKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsSpecifiedKeywordsPredicate) other).keywords)); // state check
    }

}
