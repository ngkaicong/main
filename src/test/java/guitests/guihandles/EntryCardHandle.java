package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.budgeteer.model.entry.Entry;

/**
 * Provides a handle to a entry card in the entry list panel.
 */
public class EntryCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#date";
    private static final String CASHFLOW_FIELD_ID = "#cashFlow";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label dateLabel;
    private final Label cashFlowLabel;
    private final List<Label> tagLabels;

    public EntryCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        cashFlowLabel = getChildNode(CASHFLOW_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getCashFlow() {
        return cashFlowLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code entry}.
     */
    public boolean equals(Entry entry) {
        return getName().equals(entry.getName().fullName)
                && getDate().equals(entry.getDate().value)
                && getCashFlow().equals(entry.getCashFlow().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(entry.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
