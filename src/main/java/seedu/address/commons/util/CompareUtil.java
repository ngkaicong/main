package seedu.address.commons.util;

import java.util.Comparator;
import java.util.Set;

import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.tag.Tag;


/**
 * Comparator to sort {@code Entry}s by name, date and moneyflow attributes.
 */
public class CompareUtil {

    public static Comparator<Entry> compareNameAttribute() {
        return Comparator.comparing(a -> a.getName().fullName.toLowerCase());
    }

    public static Comparator<Entry> compareDateAttribute() {
        return (a, b) -> compareDate().compare(a.getDate(), b.getDate());
    }

    public static Comparator<Entry> compareCashflowAttribute() {
        return Comparator.comparing(a -> a.getCashFlow().valueDouble);
    }

    /**
     * This function compares date1 and date2 and returns an integer.
     * If date1 is earlier than date2, it returns -1.
     * If date1 is later than date2, it returns 1.
     * If date1 is equal to date2, it returns 0.
     */
    public static Comparator<Date> compareDate() {
        return (date1, date2) -> {
            if (date1.getYear() < date2.getYear()) {
                return -1;
            } else if (date1.getYear() == date2.getYear()) {
                if (date1.getMonth() < date2.getMonth()) {
                    return -1;
                } else if (date1.getMonth() == date2.getMonth()) {
                    if (date1.getDay() < date2.getDay()) {
                        return -1;
                    } else if (date1.getDay() == date2.getDay()) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        };
    }


    public static Comparator<Set<Tag>> compareTags() {
        return Comparator.comparing(Object::toString);
    }
}

