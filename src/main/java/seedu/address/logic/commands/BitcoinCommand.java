package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Returns how many Bitcoin you can buy at the current market price.
 */
public class BitcoinCommand extends Command {

    public static final String COMMAND_WORD = "bitcoin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays how much bitcoin you can buy.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "You are able to buy this much bitcoin.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return null;
    }

    @Override
    public CommandResult execute() {
        return null;
    }


}