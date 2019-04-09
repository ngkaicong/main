package seedu.budgeteer.commons.util;

import static seedu.budgeteer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.budgeteer.model.entry.CashFlow.FORMAT_STANDARD_MONEY;
import static seedu.budgeteer.model.entry.CashFlow.REPRESENTATION_ZERO;

import seedu.budgeteer.model.entry.CashFlow;

/**
 * Contains helper methods to process data of {@code CashFlow}
 */
public class MoneyUtil {

    /**
     * Takes in 2 {@code CashFlow} parameters and returns the sum
     */
    public static CashFlow add(CashFlow money1, CashFlow money2) {
        requireAllNonNull(money1, money2);
        Double newMoney = money1.toDouble() + money2.toDouble();
        return CashFlow.getCashFlow(formatIntoMoneyFlowFormat(newMoney));
    }

    /**
     * Formats a string into a string that is readable by {@code CashFlow}
     */
    private static String formatIntoMoneyFlowFormat(Double money) {
        String formattedMoney;
        if (money == 0) {
            formattedMoney = REPRESENTATION_ZERO;
        } else if (money > 0) {
            formattedMoney = String.format("+" + FORMAT_STANDARD_MONEY, money);
        } else {
            formattedMoney = String.format(FORMAT_STANDARD_MONEY, money);
        }
        return formattedMoney;
    }
}
