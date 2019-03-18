package seedu.address.model.record.exceptions;
//ADDED BY KAICONG
/**
 * This message will indicates that the period of tine has already had a limit.
 */
public class RedundantLimitDatesException extends RuntimeException {
    public RedundantLimitDatesException() {
        super("Operation would result in setting two limit at the same period of time.");
    }
}
