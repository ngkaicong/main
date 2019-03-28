package seedu.address.model.entry;

import java.util.function.Predicate;

/**
 * Tests that a {@code Entry}'s {@code Name} matches any of the keywords given.
 */
public class AlwaysTruePredicate implements Predicate<Entry> {

    @Override
    public boolean test(Entry entry) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AlwaysTruePredicate);
    }

}
