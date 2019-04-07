package seedu.address.model.summary;

import org.junit.Test;
import seedu.address.commons.util.MoneyUtil;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.SummaryBuilder;

import java.util.Set;

import static seedu.address.testutil.SummaryBuilder.*;

public class SummaryTest {

    private static final String DEFAULT_IDENTIFIER = "10-2-2018";
    private static final String DEFAULT_MONEYFLOW_EXPENSE = "-10.00";
    private static final String DEFAULT_MONEYFLOW_INCOME = "+100";
    private static final String DEFAULT_MONEYFLOW_TOTAL = "+90";

    private static final SummaryBuilder summaryBuilder = new SummaryBuilder();

    @Test
    public void constructor_nullRecordSummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new EntryBuilder().build(), null));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(null, new Date(DEFAULT_IDENTIFIER)));
    }

    @Test
    public void constructor_nullParamSummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Summary<Date>(null,
                new CashFlow(DEFAULT_MONEYFLOW_EXPENSE), new CashFlow(DEFAULT_MONEYFLOW_INCOME),
                new CashFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new Date(DEFAULT_IDENTIFIER),
                null, new CashFlow(DEFAULT_MONEYFLOW_INCOME),
                new CashFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new Date(DEFAULT_IDENTIFIER),
                new CashFlow(DEFAULT_MONEYFLOW_EXPENSE), null,
                new CashFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new Date(DEFAULT_IDENTIFIER),
                new CashFlow(DEFAULT_MONEYFLOW_EXPENSE), new CashFlow(DEFAULT_MONEYFLOW_INCOME),
                null));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {

        Summary<Set<Tag>> categorySummary = summaryBuilder.buildCategorySummary();
        Assert.assertThrows(NullPointerException.class, () -> categorySummary.add(null));
    }

    @Test
    public void add_recordWithExpenseMoneyFlowToSummary_success() {

        EntryBuilder entryBuilder = new EntryBuilder();
        Entry toAdd = entryBuilder.withDate(DEFAULT_IDENTIFIER).withCashFlow(DEFAULT_MONEYFLOW_EXPENSE).build();


        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        CashFlow targetTotalExpense = MoneyUtil.add(new CashFlow(DEFAULT_TOTAL_EXPENSE), toAdd.getCashFlow());
        CashFlow targetTotal = MoneyUtil.add(new CashFlow(DEFAULT_TOTAL), toAdd.getCashFlow());


    }

    @Test
    public void add_recordWithIncomeMoneyFlowToSummary_success() {

        EntryBuilder entryBuilder = new EntryBuilder();
        Entry toAdd = entryBuilder.withDate(DEFAULT_IDENTIFIER).withCashFlow(DEFAULT_MONEYFLOW_INCOME).build();

        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        CashFlow targetTotalIncome = MoneyUtil.add(new CashFlow(DEFAULT_TOTAL_INCOME), toAdd.getCashFlow());
        CashFlow targetTotal = MoneyUtil.add(new CashFlow(DEFAULT_TOTAL), toAdd.getCashFlow());

    }
}
