package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Expense;
import seedu.address.model.entry.Income;
import seedu.address.model.entry.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Entry[] getSampleEntrys() {
        return new Entry[] {
            new Entry(new Name("Alex Yeoh"), new Date("12-12-2018"), new Income("+100"),
                getTagSet("friends")),
            new Entry(new Name("Bernice Yu"), new Date("12-12-2018"), new Income("+100"),
                getTagSet("colleagues", "friends")),
            new Entry(new Name("Charlotte Oliveiro"), new Date("12-12-2018"), new Income("+100"),
                getTagSet("neighbours")),
            new Entry(new Name("David Li"), new Date("12-12-2018"), new Income("+100"),
                getTagSet("family")),
            new Entry(new Name("Irfan Ibrahim"), new Date("12-12-2018"), new Income("+100"),
                getTagSet("classmates")),
            new Entry(new Name("Roy Balakrishnan"), new Date("12-12-2018"), new Expense("-100"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
