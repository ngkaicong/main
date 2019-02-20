package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.entry.Entry;
import seedu.address.testutil.PersonBuilder;

public class EntryCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Entry personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        EntryCard entryCard = new EntryCard(personWithNoTags, 1);
        uiPartRule.setUiPart(entryCard);
        assertCardDisplay(entryCard, personWithNoTags, 1);

        // with tags
        Entry personWithTags = new PersonBuilder().build();
        entryCard = new EntryCard(personWithTags, 2);
        uiPartRule.setUiPart(entryCard);
        assertCardDisplay(entryCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Entry person = new PersonBuilder().build();
        EntryCard entryCard = new EntryCard(person, 0);

        // same entry, same index -> returns true
        EntryCard copy = new EntryCard(person, 0);
        assertTrue(entryCard.equals(copy));

        // same object -> returns true
        assertTrue(entryCard.equals(entryCard));

        // null -> returns false
        assertFalse(entryCard.equals(null));

        // different types -> returns false
        assertFalse(entryCard.equals(0));

        // different entry, same index -> returns false
        Entry differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(entryCard.equals(new EntryCard(differentPerson, 0)));

        // same entry, different index -> returns false
        assertFalse(entryCard.equals(new EntryCard(person, 1)));
    }

    /**
     * Asserts that {@code entryCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EntryCard entryCard, Entry expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(entryCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify entry details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
