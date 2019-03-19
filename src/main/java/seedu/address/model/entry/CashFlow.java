package seedu.address.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/** Shows the income and expense in the entry of budgeter
 * Guarantees: immutable; is valid as declared in {@link #isValidCashFlow(String)}
 */
public class CashFlow {

    public static final String MESSAGE_CONSTRAINTS =
            "Any form of cash flow should consist of '+' or '-', "
                    + "followed by a digits and/or decimal points ('.')."
                    + "1. <number> cannot start from '0' unless it has only 1 digit. "
                    + "There must be at least 1 digit in this field.\n"
                    + "2. At most 1 decimal point can be present. Decimal point is optional."
                    + "If decimal point is present, it must have at least 1 digit after it";

    // Any form of cash flow entered must follow the format defined above
    private static final String CASHFLOW_WHOLE_NUMBER_ZERO_REGEX = "0";
    private static final String CASHFLOW_WHOLE_NUMBER_NONZERO_REGEX = "[1-9]{1}\\d*";
    private static final String CASHFLOW_DECIMAL_PART_REGEX = ".\\d{2}";
    private static final String CASHFLOW_SIGN_PART_REGEX = "[\\+-]{1}";
    // This only represents the numerical part of the string pattern
    // UNSIGNED_CASHFLOW_VALIDATION_REGEX = "(0|[1-9]{1}\d*)($|.\d+)"
    public static final String UNSIGNED_CASHFLOW_VALIDATION_REGEX = "(" + CASHFLOW_WHOLE_NUMBER_ZERO_REGEX + "|"
            + CASHFLOW_WHOLE_NUMBER_NONZERO_REGEX + ")" + "(" + "$" + "|" + CASHFLOW_DECIMAL_PART_REGEX + ")";
    // This represents the whole pattern
    public static final String CASHFLOW_VALIDATION_REGEX = "^" + CASHFLOW_SIGN_PART_REGEX
            + UNSIGNED_CASHFLOW_VALIDATION_REGEX;

    public final Float value;

    public CashFlow(String cashFlowStr) {
        requireNonNull(cashFlowStr);
        checkArgument(isValidCashFlow(cashFlowStr), MESSAGE_CONSTRAINTS);
        Float cashFlow = Float.parseFloat(cashFlowStr);
        this.value = cashFlow;
    }

    /**
     * Returns if a given string is a valid cashflow parameter.
     */
    public static boolean isValidCashFlow(String test) {
        return test.matches(CASHFLOW_VALIDATION_REGEX);
    }

    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CashFlow // instanceof handles nulls
                && value.equals(((CashFlow) other).value)); // state check
    }

    public int hashCode() {
        return value.hashCode();
    }
}
