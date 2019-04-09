package seedu.budgeteer.logic.commands;

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
 * Returns how much stock you can buy at the current market price.
 */
public class StockCommand extends Command {

    public static final String COMMAND_WORD = "stock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays how much of a certain stock you can buy.\n"
            + "Example: " + COMMAND_WORD;

    private static final String firstUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";
    private static final String secondUrl = "&apikey=Y6G36I3BIPQL5I2";

    private final Name name;

    private String stock = "MSFT";


    public StockCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        double price = 0.0;
        String successMessage = "";
        try {
            model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRYS);
            ObservableList<Entry> filteredList = model.getFilteredEntryList();
            ReportEntryList reportList = new ReportEntryList(filteredList);
            Double total = reportList.getTotal();

            stock = name.fullName;
            String temp = firstUrl + stock + secondUrl;
            URL url = new URL(temp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String full = "temp";
            String output;
            int y = 7;
            while (y > 0) {
                output = br.readLine();
                full = output;
                y -= 1;
            }


            if (full == null) {
                successMessage = "Sorry, your input is not a valid stock.";
            } else if (full.length() < 30) {
                successMessage = "Sorry, your input is not a valid stock.";
            } else {
                price = Float.parseFloat(full.substring(22, 30));
                Double amount = (double) Math.round(price * 100.0) / 100.0;
                successMessage = "The price of the stock " + name.fullName + " is $" + amount.toString() + ".";
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult(successMessage);
    }
}
