package seedu.budgeteer.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.budgeteer.model.entry.Entry;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class EntryCard extends UiPart<Region> {

    private static final String FXML = "EntryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on EntriesBook level 4</a>
     */

    public final Entry entry;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label cashFlow;
    @FXML
    private FlowPane tags;

    public EntryCard(Entry entry, int displayedIndex) {
        super(FXML);
        this.entry = entry;
        id.setText(displayedIndex + ". ");
        name.setText(entry.getName().fullName);
        date.setText(entry.getDate().value);
        cashFlow.setText(entry.getCashFlow().toString());
        entry.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntryCard)) {
            return false;
        }

        // state check
        EntryCard card = (EntryCard) other;
        return id.getText().equals(card.id.getText())
                && entry.equals(card.entry);
    }
}
