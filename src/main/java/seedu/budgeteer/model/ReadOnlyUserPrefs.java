package seedu.budgeteer.model;

import java.nio.file.Path;

import seedu.budgeteer.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    String getPasswordFilePath();
}
