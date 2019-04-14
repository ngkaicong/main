package seedu.budgeteer.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_CASHFLOW = new Prefix("c/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_INTEREST = new Prefix("interest/");
    public static final Prefix PREFIX_YEARS = new Prefix("years/");

    public static final Prefix PREFIX_STARTDATE = new Prefix("s/");
    public static final Prefix PREFIX_ENDDATE = new Prefix("e/");
    public static final Prefix PREFIX_INSIGHT = new Prefix("insight/");

    public static final Prefix PREFIX_SET = new Prefix("set/");
    public static final Prefix PREFIX_CHANGE = new Prefix("change/");
    public static final Prefix PREFIX_REMOVE = new Prefix("remove/");
    public static final Prefix PREFIX_DIR = new Prefix("dir/");


}
