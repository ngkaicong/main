
package seedu.address.commons.util;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.commons.util.CompareUtil.compareDate;
import static seedu.address.testutil.TypicalEntrys.CAIFAN;
import static seedu.address.testutil.TypicalEntrys.INDO;

import org.junit.Test;

import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.testutil.EntryBuilder;

public class CompareUtilTest {

    @Test
    public void compareNameAttribute_equalsTest() { // test exactly similar names
        Entry entry = new EntryBuilder(CAIFAN).withName("Indo").build();
        int compareResult = CompareUtil.compareNameAttribute().compare(INDO, entry);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareNameAttributeLowerCase_equalsTest() { // test similar names but different case
        Entry entry = new EntryBuilder(CAIFAN).withName("indo").build();
        int compareResult = CompareUtil.compareNameAttribute().compare(INDO, entry);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareNameAttribute_lessThanTest() { // test different names
        Entry entry = new EntryBuilder(CAIFAN).withName(("indo")).build();
        int compareResult = CompareUtil.compareNameAttribute().compare(CAIFAN, entry);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareNameAttribute_greaterThanTest() {
        Entry entry = new EntryBuilder(INDO).withName(("caifan")).build();
        int compareResult = CompareUtil.compareNameAttribute().compare(INDO, entry);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareDateAttribute_equalsTest() {
        Entry entry = new EntryBuilder(INDO).withDate((CAIFAN.getDate().value)).build();
        int compareResult = CompareUtil.compareDateAttribute().compare(CAIFAN, entry);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareDateAttribute_lessThanTest() { // test different names
        Entry entry = new EntryBuilder(INDO).withDate((CAIFAN.getDate().value)).build();
        int compareResult = CompareUtil.compareDateAttribute().compare(INDO, entry);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareDateAttribute_greaterThanTest() {
        Entry entry = new EntryBuilder(CAIFAN).withDate((INDO.getDate().value)).build();
        int compareResult = CompareUtil.compareDateAttribute().compare(CAIFAN, entry);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareCashflowAttribute_equalsTest() {
        Entry entry = new EntryBuilder(CAIFAN).withCashFlow((INDO.getCashFlow().value)).build();
        int compareResult = CompareUtil.compareCashflowAttribute().compare(INDO, entry);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareCashflowAttribute_lessThanTest() { // test different names
        Entry entry = new EntryBuilder(INDO).withCashFlow((CAIFAN.getCashFlow().value)).build();
        int compareResult = CompareUtil.compareCashflowAttribute().compare(INDO, entry);
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareCashflowAttribute_greaterThanTest() {
        Entry entry = new EntryBuilder(CAIFAN).withCashFlow((INDO.getCashFlow().value)).build();
        int compareResult = CompareUtil.compareCashflowAttribute().compare(CAIFAN, entry);
        assertTrue(compareResult >= 1);
    }

    @Test
    public void compareDate_equalsTest() {
        int compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-04-2018"));
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareDate_lessThanTest() {
        int compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-04-2019"));
        assertTrue(compareResult <= -1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-05-2018"));
        assertTrue(compareResult <= -1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("27-04-2018"));
        assertTrue(compareResult <= -1);
    }

    @Test
    public void compareDate_moreThanTest() {
        int compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-04-2017"));
        assertTrue(compareResult >= 1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("26-03-2018"));
        assertTrue(compareResult >= 1);
        compareResult = compareDate().compare(CAIFAN.getDate(), new Date("24-04-2018"));
        assertTrue(compareResult >= 1);
    }



}