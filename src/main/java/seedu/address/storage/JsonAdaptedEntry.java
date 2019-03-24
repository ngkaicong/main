package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Entry's %s field is missing!";

    private final String name;
    private final String date;
    private final String cashFlow;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEntry} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedEntry(@JsonProperty("name") String name, @JsonProperty("date") String date,
                            @JsonProperty("cashFlow") String cashFlow,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.date = date;
        this.cashFlow = cashFlow;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Entry} into this class for Jackson use.
     */
    public JsonAdaptedEntry(Entry source) {
        name = source.getName().fullName;
        date = source.getDate().value;
        cashFlow = source.getCashFlow().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted entry object into the model's {@code Entry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry.
     */
    public Entry toModelType() throws IllegalValueException {
        final List<Tag> entryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            entryTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (cashFlow == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CashFlow.class.getSimpleName()));
        }
        if (!CashFlow.isValidCashFlow(cashFlow)) {
            throw new IllegalValueException(CashFlow.MESSAGE_CONSTRAINTS);
        }
        final CashFlow modelCashFlow = new CashFlow(cashFlow);


        final Set<Tag> modelTags = new HashSet<>(entryTags);
        return new Entry(modelName, modelDate, modelCashFlow, modelTags);
    }

}
