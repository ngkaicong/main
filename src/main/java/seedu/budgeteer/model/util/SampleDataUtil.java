package seedu.budgeteer.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.ReadOnlyEntriesBook;
import seedu.budgeteer.model.entry.CashFlow;
import seedu.budgeteer.model.entry.Date;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.Name;
import seedu.budgeteer.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EntriesBook} with sample data.
 */
public class SampleDataUtil {
    public static Entry[] getSampleEntrys() {
        return new Entry[] {
            new Entry(new Name("Salary from Alex Yeoh"), new Date("11-11-2019"), CashFlow.getCashFlow("+100"),
                getTagSet("friends")),
            new Entry(new Name("Lunch with Bernice Yu"), new Date("12-12-2018"), CashFlow.getCashFlow("+100"),
                getTagSet("colleagues", "friends")),
            new Entry(new Name("Burger with Charlotte Oliveiro"), new Date("11-11-2019"), CashFlow.getCashFlow("+100"),
                getTagSet("neighbours")),
            new Entry(new Name("School Loan"), new Date("12-12-2018"), CashFlow.getCashFlow("+100"),
                getTagSet("personal")),
            new Entry(new Name("Income"), new Date("12-12-2019"), CashFlow.getCashFlow("+100"),
                getTagSet("work")),
            new Entry(new Name("Dinner with Roy Balakrishnan"), new Date("11-11-2019"), CashFlow.getCashFlow("+100"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyEntriesBook getSampleAddressBook() {
        EntriesBook sampleAb = new EntriesBook();
        for (Entry sampleEntry : getSampleEntrys()) {
            sampleAb.addEntry(sampleEntry);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
