package seedu.budgeteer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import seedu.budgeteer.commons.core.Config;
import seedu.budgeteer.commons.core.LogsCenter;
import seedu.budgeteer.commons.core.Version;
import seedu.budgeteer.commons.exceptions.DataConversionException;
import seedu.budgeteer.commons.util.ConfigUtil;
import seedu.budgeteer.commons.util.CryptoUtil;
import seedu.budgeteer.commons.util.EncryptionUtil;
import seedu.budgeteer.commons.util.FileUtil;
import seedu.budgeteer.commons.util.StringUtil;
import seedu.budgeteer.logic.Logic;
import seedu.budgeteer.logic.LogicManager;
import seedu.budgeteer.model.EntriesBook;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.ModelManager;
import seedu.budgeteer.model.ReadOnlyEntriesBook;
import seedu.budgeteer.model.ReadOnlyUserPrefs;
import seedu.budgeteer.model.UserPrefs;
import seedu.budgeteer.model.util.SampleDataUtil;
import seedu.budgeteer.storage.BudgeteerStorage;
import seedu.budgeteer.storage.JsonBudgeteerStorage;
import seedu.budgeteer.storage.JsonUserPrefsStorage;
import seedu.budgeteer.storage.Storage;
import seedu.budgeteer.storage.StorageManager;
import seedu.budgeteer.storage.UserPrefsStorage;

import seedu.budgeteer.ui.Ui;
import seedu.budgeteer.ui.UiManager;

/**
 * The main entry point to the application.
 */


public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing EntriesBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        BudgeteerStorage budgeteerStorage = new JsonBudgeteerStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(budgeteerStorage, userPrefsStorage);

        CryptoUtil.getInstance(); //Initialize the crypto prices

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s budgeteer book and {@code userPrefs}. <br>
     * The data from the sample budgeteer book will be used instead if {@code storage}'s budgeteer book is not found,
     * or an empty budgeteer book will be used instead if errors occur when reading {@code storage}'s budgeteer book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyEntriesBook> entrieskOptional;
        ReadOnlyEntriesBook initialData;

        File file = new File(userPrefs.getPasswordFilePath());
        try {
            if (FileUtil.isPassExists(file)) {
                File book = new File(String.valueOf(userPrefs.getAddressBookFilePath()));
                EncryptionUtil.decrypt(book);
            }
        } catch (IOException e) {
            logger.warning("Problem while reading from the password file");
        }

        try {
            entrieskOptional = storage.readEntriesBook();
            if (!entrieskOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample EntriesBook");
            }
            initialData = entrieskOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty EntriesBook");
            initialData = new EntriesBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty EntriesBook");
            initialData = new EntriesBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty EntriesBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting EntriesBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
