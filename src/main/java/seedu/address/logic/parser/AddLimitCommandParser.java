package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Date;
import seedu.address.model.record.Limit;
import seedu.address.model.record.MoneyFlow;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.DateUtil.isLaterThan;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASHFLOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
//ADDED BY KAICONG


/**
* The Parser will parse those values in one format Limit and return back to AddLimitCommand.
* */
public class AddLimitCommandParser implements Parser<AddLimitCommand> {
    /**
     * Parses the information required for the limit command.
     * and returns a limit object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    private static final int DOUBLE_DATE = 2;
    private static final int SINGLE_DATE = 1;
    private String [] datesIn; //the string is used to divide two the whole strings into two substrings.
    private String moneyString;
    private Date dateStart;
    private Date dateEnd;

    @Override
    public AddLimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_CASHFLOW);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_CASHFLOW)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLimitCommand.MESSAGE_USAGE));
        }
        moneyString = "-" + argMultimap.getValue(PREFIX_CASHFLOW).get();

        if (!(MoneyFlow.isValidMoneyFlow(moneyString))) {
            throw new ParseException("The limit money can only be normal real number. \n"
                    + "Example: m/100.");
        }
        MoneyFlow money = ParserUtil.parseMoneyFlow(moneyString);
        datesIn = argMultimap.getValue(PREFIX_DATE).get().split("\\s+");
        if (datesIn.length == DOUBLE_DATE) {
            dateStart = ParserUtil.parseDate(datesIn[0]);
            dateEnd = ParserUtil.parseDate(datesIn[1]);
        } else if (datesIn.length == SINGLE_DATE) {
            dateStart = ParserUtil.parseDate(datesIn[0]);
            dateEnd = ParserUtil.parseDate(datesIn[0]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLimitCommand.MESSAGE_USAGE));
        }

        if (isLaterThan(dateStart, dateEnd)) {
            throw new ParseException("The dateStart must be earlier than or equals to dateEnd.");
        }
        Limit limit = new Limit(dateStart, dateEnd, money);


        return new AddLimitCommand(limit);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
