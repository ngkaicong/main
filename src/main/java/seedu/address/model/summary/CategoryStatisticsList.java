package seedu.address.model.summary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entry.Entry;
import seedu.address.model.tag.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a list to store the statistics of each category within a certain time period.
 */
public class CategoryStatisticsList {

    private HashMap<Set<Tag>, CategoryStatistic> categoryStats;

    public CategoryStatisticsList(List<Entry> entryList) {
        requireNonNull(entryList);
        categoryStats = new HashMap<>();
        for (Entry r : entryList) {
            Set<Tag> tags = r.getTags();
            addToCategoryStatistics(r, tags);
        }
    }

    /**
     * Adds the entry into the internal hashMap
     * @param entry entry to be added
     * @param tags the key of the addition
     */
    private void addToCategoryStatistics(Entry entry, Set<Tag> tags) {
        if (categoryStats.containsKey(tags)) {
            categoryStats.get(tags).add(entry);
        } else {
            categoryStats.put(entry.getTags(), new CategoryStatistic(entry));
        }
    }

    /**
     * Returns the contents of the internal {@link HashMap} as a read only {@link ObservableList}
     */
    public ObservableList<CategoryStatistic> getReadOnlyStatsList() {
        List<CategoryStatistic> statsList = categoryStats.keySet().stream()
                .map(s -> categoryStats.get(s)).collect(Collectors.toList());
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(statsList));
    }

    public HashMap<Set<Tag>, CategoryStatistic> getCategoryStatsMap() {
        return categoryStats;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryStatisticsList // instanceof handles nulls
                && categoryStats.equals(((CategoryStatisticsList) other).categoryStats));
    }
}
