package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SET;

import java.util.stream.Stream;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author limzk1994

/**
 * Parses the inputs and create a LockCommand object
 */
public class LockCommandParser implements Parser<LockCommand> {


    @Override
    public LockCommand parse(String args) throws ParseException {

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SET, PREFIX_CHANGE, PREFIX_REMOVE);

        if (arePrefixesPresent(argumentMultimap, PREFIX_SET)) {
            return new LockCommand(new LockCommand.SetLock(argumentMultimap.getValue(PREFIX_SET).get()));
        } else if (arePrefixesPresent(argumentMultimap, PREFIX_REMOVE)) {
            return new LockCommand(new LockCommand.ClearLock(
                    argumentMultimap.getValue(PREFIX_REMOVE).get()));
        } else if (arePrefixesPresent(argumentMultimap, PREFIX_CHANGE)) {
            final String newPassword = argumentMultimap.getValue(PREFIX_CHANGE).get();
            requireNonNull(newPassword);
            if (newPassword.length() == 0) {
                throw new ParseException("Password cannot be blank!");
            }
            return new LockCommand(new LockCommand.ChangeLock(newPassword));

        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
