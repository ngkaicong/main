package seedu.address.model.summary;

import org.junit.Test;
import seedu.address.model.entry.Entry;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.SummaryBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEntrys.*;

public class SummaryByCategoryListTest {

    private static final Entry CAIFAN_A =
            new EntryBuilder(CAIFAN).withTags("A").build();
    private static final Entry INDO_A =
            new EntryBuilder(INDO).withTags("A").build();
    private static final Entry INDO_B =
            new EntryBuilder(INDO).withTags("B").build();
    private static final Entry RANDOM_C =
            new EntryBuilder(RANDOM).withTags("C").build();
    private static final Entry WORK_D =
            new EntryBuilder(WORK).withTags("D").build();
    private static final Entry MALA_D =
            new EntryBuilder(MALA).withTags("D").build();
    private static final Entry MALA_E =
            new EntryBuilder(MALA).withTags("E").build();
    private static final Entry ZT_F =
            new EntryBuilder(ZT).withTags("F").build();
    private static final Entry CHICKEN_RICE_G =
            new EntryBuilder(CHICKENRICE).withTags("G").build();


    private static final List<Entry> ENTRY_LIST_ALL_UNIQUE_CATEGORIES = Arrays.asList(CAIFAN_A, INDO_B, RANDOM_C,
            WORK_D, MALA_E, ZT_F, CHICKEN_RICE_G);

    private static List<Entry> entryListOverlappingCategories = Arrays.asList(CAIFAN_A, INDO_A, RANDOM_C,
            WORK_D, MALA_D, ZT_F, CHICKEN_RICE_G);

    @Test
    public void equals() {
        SummaryByCategoryList summaryByCategoryListOne = new SummaryByCategoryList(ENTRY_LIST_ALL_UNIQUE_CATEGORIES);
        SummaryByCategoryList summaryByCategoryListTwo = new SummaryByCategoryList(entryListOverlappingCategories);

        // same object -> returns true
        assertTrue(summaryByCategoryListOne.equals(summaryByCategoryListOne));

        // same values -> returns true
        SummaryByCategoryList summaryByCategoryListOneCopy = new SummaryByCategoryList(ENTRY_LIST_ALL_UNIQUE_CATEGORIES);
        assertTrue(summaryByCategoryListOne.equals(summaryByCategoryListOneCopy));

        // different types -> returns false
        assertFalse(summaryByCategoryListOne.equals(1));

        // null -> returns false
        assertFalse(summaryByCategoryListOne.equals(null));

        // different values -> returns false
        assertFalse(summaryByCategoryListOne.equals(summaryByCategoryListTwo));
    }

    @Test
    public void constructor_nullParam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryByCategoryList(
                null));
    }

    @Test
    public void constructor_recordListWithUniqueCategoriesNoFilter_success() {
        HashMap<Set<Tag>, Summary<Set<Tag>>> expectedMap = new HashMap<>();
        for (Entry r : ENTRY_LIST_ALL_UNIQUE_CATEGORIES) {
            expectedMap.put(r.getTags(), new Summary<>(r, r.getTags()));
        }
        assertEquals(new SummaryByCategoryList(ENTRY_LIST_ALL_UNIQUE_CATEGORIES).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithOverlappingCategoriesNoFilter_success() {
        Summary<Set<Tag>> summaryA = new SummaryBuilder(CAIFAN_A).buildCategorySummary();
        summaryA.add(INDO_A);

        Summary<Set<Tag>> summaryD = new SummaryBuilder(WORK_D).buildCategorySummary();
        summaryD.add(MALA_D);

        List<Summary<Set<Tag>>> summaryListOverLappingCategories = Arrays.asList(summaryA,
                new SummaryBuilder(RANDOM_C).buildCategorySummary(), summaryD,
                new SummaryBuilder(ZT_F).buildCategorySummary(),
                new SummaryBuilder(CHICKEN_RICE_G).buildCategorySummary());

        HashMap<Set<Tag>, Summary<Set<Tag>>> expectedMap = new HashMap<>();
        for (Summary<Set<Tag>> s : summaryListOverLappingCategories) {
            expectedMap.put(s.getIdentifier(), s);
        }
        assertEquals(new SummaryByCategoryList(entryListOverlappingCategories).getSummaryMap(),
                expectedMap);
    }
}
