package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditEntryDescriptor;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditEntryDescriptor objects.
 */
public class EditEntryDescriptorBuilder {

    private EditEntryDescriptor descriptor;

    public EditEntryDescriptorBuilder() {
        descriptor = new EditEntryDescriptor();
    }

    public EditEntryDescriptorBuilder(EditEntryDescriptor descriptor) {
        this.descriptor = new EditEntryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEntryDescriptor} with fields containing {@code entry}'s details
     */
    public EditEntryDescriptorBuilder(Entry entry) {
        descriptor = new EditEntryDescriptor();
        descriptor.setName(entry.getName());
        descriptor.setDate(entry.getDate());
        descriptor.setCashFlow(entry.getCashFlow());
        descriptor.setTags(entry.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code CashFlow} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withCashFlow(String cashFlow) {
        descriptor.setCashFlow(new CashFlow(cashFlow));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEntryDescriptor}
     * that we are building.
     */
    public EditEntryDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditEntryDescriptor build() {
        return descriptor;
    }
}
