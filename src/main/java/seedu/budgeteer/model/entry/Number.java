package seedu.budgeteer.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.budgeteer.commons.util.AppUtil.checkArgument;

/**
 * Represents a Entry's name in the budgeteer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Number {

    /*
     * The first character of the budgeteer must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //TODO: May need to change the constraints
    public static final String VALIDATION_REGEX = "[\\p{Digit}\\.][\\p{Digit}\\. ]*";
    public static final String MESSAGE_CONSTRAINTS = "Numbers can only contain numbers and periods, "
            + "and it should not be blank";

    public final String fullNumber;

    /**
     * Constructs a {@code Name}.
     *
     * @param number A valid number.
     */
    public Number(String number) {
        requireNonNull(number);
        checkArgument(isValidName(number), MESSAGE_CONSTRAINTS);
        fullNumber = number;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Number // instanceof handles nulls
                && fullNumber.equals(((Number) other).fullNumber)); // state check
    }

    @Override
    public int hashCode() {
        return fullNumber.hashCode();
    }

}
