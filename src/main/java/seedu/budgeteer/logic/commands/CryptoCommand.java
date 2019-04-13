package seedu.budgeteer.logic.commands;

import static seedu.budgeteer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.budgeteer.model.Model.PREDICATE_SHOW_ALL_ENTRYS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.collections.ObservableList;
import seedu.budgeteer.logic.CommandHistory;
import seedu.budgeteer.model.Model;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.Name;
import seedu.budgeteer.model.entry.ReportEntryList;

/**
 * Returns how much cryptocurrency you can buy at the current market price.
 */
public class CryptoCommand extends Command {

    public static final String COMMAND_WORD = "crypto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays how much of a certain "
            + "cryptocurrency you can buy.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CRYPTOCURRENCY NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MSFT";

    private static final String MESSAGE_SUCCESS = "The price of the cryptocurrency ";

    private String firstUrl = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=";
    private String crypto = "MSFT";
    private String secondUrl = "&tsyms=SGD";

    private final Name name;

    public CryptoCommand(Name name) {
        this.name = name;
    }

    /**
     * Function that calls the crypto API and returns the JSON in string format
     */
    public String cryptoPrice() {
        String ret = "";
        try {
            String temp = firstUrl + name.fullName.toUpperCase() + secondUrl;

            URL url = new URL(temp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            ret = br.readLine();

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        double price;
        String messageReturn;

        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
        ObservableList<Entry> filteredList = model.getFilteredEntryList();
        ReportEntryList reportList = new ReportEntryList(filteredList);
        Double total = reportList.getTotal();

        String full = cryptoPrice();

        if (full == null) {
            messageReturn = "Sorry, your input is not a valid cryptocurrency. Please try again.";
        } else {
            full = full.substring(14);
            full = full.substring(0, full.length() - 2);
            price = Float.parseFloat(full);
            Double printPrice = (double) Math.round(price * 100.0) / 100.0;

            String first = "You are able to buy ";
            Double amount = total / price;
            amount = (double) Math.round(amount * 100.0) / 100.0;
            String second = first + amount + " " + name.fullName.toUpperCase() + ". ";

            messageReturn = second + "The price of the cryptocurrency " + name.fullName.toUpperCase()
                    + " is $" + printPrice.toString() + ".";
        }

        return new CommandResult(messageReturn);
    }
}
