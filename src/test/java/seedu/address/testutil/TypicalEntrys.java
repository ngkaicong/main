package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CASHFLOW_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CASHFLOW_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.entry.Entry;

/**
 * A utility class containing a list of {@code Entry} objects to be used in tests.
 */
public class TypicalEntrys {

    public static final Entry INDO = new EntryBuilder().withName("Indo")
            .withCashFlow("-5.6").withDate("18-9-2012").withTags("friends").build();
    public static final Entry CAIFAN = new EntryBuilder().withName("caifan")
            .withCashFlow("-3.80").withDate("18-12-2012").withTags("owesCash", "friends").build();
    public static final Entry WORK = new EntryBuilder().withName("Income from work").withDate("10-12-2012")
            .withCashFlow("+60.0").build();
    public static final Entry ZT = new EntryBuilder().withName("Payment from ZT").withDate("10-1-2012")
            .withCashFlow("+5.90").withTags("friends").build();
    public static final Entry MALA = new EntryBuilder().withName("Payment for mala").withDate("10-1-2003")
            .withCashFlow("-10.50").build();
    public static final Entry CHICKENRICE = new EntryBuilder().withName("Payment for chicken rice")
            .withDate("31-12-1996").withCashFlow("-0.90").build();
    public static final Entry RANDOM = new EntryBuilder().withName("Random income").withDate("23-10-2010")
            .withCashFlow("+14.50").build();

    // Manually added
    public static final Entry BURSARY = new EntryBuilder().withName("Income from bursary").withDate("31-4-2080")
            .withCashFlow("+11.50").build();
    public static final Entry IDA = new EntryBuilder().withName("Payment to Ida").withDate("4-10-2030")
            .withCashFlow("-12.30").build();

    // Manually added - Entry's details found in {@code CommandTestUtil}
    public static final Entry AMY = new EntryBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withCashFlow(VALID_CASHFLOW_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Entry BOB = new EntryBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withCashFlow(VALID_CASHFLOW_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_BURSARY = "Income"; // A keyword that matches MEIER

    private TypicalEntrys() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical entrys.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Entry entry : getTypicalEntrys()) {
            ab.addEntry(entry);
        }
        return ab;
    }

    public static List<Entry> getTypicalEntrys() {
        return new ArrayList<>(Arrays.asList(INDO, CAIFAN, WORK, ZT, MALA, CHICKENRICE, RANDOM));
    }
}
