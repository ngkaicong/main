package seedu.address.logic;

/**
 * The base class for all event classes.
 */
public abstract class Start {

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     *
     * For example, the event manager post method will call any posted event's toString and print it in the console.
     */
    public abstract String toString();

}