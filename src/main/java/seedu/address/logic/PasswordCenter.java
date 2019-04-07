package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.EventBus;

import seedu.address.commons.core.LogsCenter;

/**
 * Manages the password dispatching of the app.
 */
public class PasswordCenter {
    private static final Logger logger = LogsCenter.getLogger(PasswordCenter.class);
    private static PasswordCenter instance;
    private final EventBus eventBus;

    private PasswordCenter() {
        eventBus = new EventBus();
    }

    public static PasswordCenter getInstance() {
        if (instance == null) {
            instance = new PasswordCenter();
        }
        return instance;
    }

    public static void clearSubscribers() {
        instance = null;
    }

    public void registerHandler(Object handler) {
        eventBus.register(handler);
    }

    /**
     * Posts an event to the event bus.
     */
    public <E extends Start> PasswordCenter post(E event) {
        logger.info("------[Event Posted] " + event.getClass().getCanonicalName() + ": " + event.toString());
        eventBus.post(event);
        return this;
    }

}
