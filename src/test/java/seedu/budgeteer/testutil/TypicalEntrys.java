package seedu.budgeteer.testutil;

import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_CASHFLOW_AMY;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_CASHFLOW_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.budgeteer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.Date;

/**
 * A utility class containing a list of {@code Entry} objects to be used in tests.
 */
public class TypicalEntrys {

    public static final Entry INDO = new EntryBuilder().withName("Indo")
            .withCashFlow("-5.60").withDate("19-02-2019").withTags("friends").build();
    public static final Entry CAIFAN = new EntryBuilder().withName("caifan with Benson Daniel")
            .withCashFlow("-3.80").withDate("18-02-2019").withTags("owesCash", "friends").build();
    public static final Entry WORK = new EntryBuilder().withName("Income from work").withDate("10-02-2019")
            .withCashFlow("+60.00").build();
    public static final Entry ZT = new EntryBuilder().withName("Payment from ZT").withDate("10-02-2019")
            .withCashFlow("+5.90").withTags("friends").build();
    public static final Entry MALA = new EntryBuilder().withName("Payment for mala to Carl").withDate("10-02-2019")
            .withCashFlow("-10.50").build();
    public static final Entry CHICKENRICE = new EntryBuilder().withName("Payment for chicken rice")
            .withDate("20-12-2018").withCashFlow("-0.90").build();
    public static final Entry RANDOM = new EntryBuilder().withName("Random income").withDate("23-10-2018")
            .withCashFlow("+14.50").build();

    public static final Date TYPICAL_START_DATE = new Date("25-9-2018");
    public static final Date TYPICAL_END_DATE = new Date("26-9-2018");


    public static final Date TYPICAL_START_DATE1 = new Date ("31-03-1999");
    public static final Date TYPICAL_END_DATE1 = new Date ("1-4-1999");
    public static final Date TYPICAL_START_FAR_DATE = new Date("1-1-0000");
    public static final Date TYPICAL_END_FAR_DATE = new Date ("31-12-9999");

    // Manually added
    public static final Entry BURSARY = new EntryBuilder().withName("Income from bursary").withDate("31-03-2018")
            .withCashFlow("+110.50").build();
    public static final Entry IDA = new EntryBuilder().withName("Payment to Ida and Benson Daniel")
            .withDate("04-10-2019")
            .withCashFlow("-12.30").build();

    // Manually added - Entry's details found in {@code C
    // ommandTestUtil}
    public static final Entry AMY = new EntryBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withCashFlow(VALID_CASHFLOW_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Entry BOB = new EntryBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withCashFlow(VALID_CASHFLOW_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_BURSARY = "Income"; // A keyword that matches Bursary

    private TypicalEntrys() {} // prevents instantiation

    /**
     * Returns an {@code EntriesBook} with all the typical entrys.
     */
    public static EntriesBook getTypicalAddressBook() {
        EntriesBook ab = new EntriesBook();
        for (Entry entry : getTypicalEntrys()) {
            ab.addEntry(entry);
        }
        return ab;
    }

    public static List<Entry> getTypicalEntrys() {
        return new ArrayList<>(Arrays.asList(INDO, CAIFAN, WORK, ZT, MALA, CHICKENRICE, BURSARY, IDA));
    }
}
