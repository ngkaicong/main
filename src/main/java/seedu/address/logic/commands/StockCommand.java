package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.entry.Name;
import seedu.address.model.entry.NameContainsKeywordsPredicate;
import java.util.function.Predicate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Returns how much stock you can buy at the current market price.
 */
public class StockCommand extends Command {

    public static final String COMMAND_WORD = "stock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays how much of a certain stock you can buy.\n"
            + "Example: " + COMMAND_WORD;

    public static String MESSAGE_SUCCESS = "The price of the stock ";

    public String firstURL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";
    public String stock = "MSFT";
    public String secondURL = "&apikey=Y6G36I3BIPQL5I2";

    private final Name name;

    public StockCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        double price = 0.0;
        try {
            stock = name.fullName;
            String temp = firstURL + stock + secondURL;
//            System.out.println(temp);
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
//                System.out.println(output);
                y -= 1;
            }

            if (full == null) {
                MESSAGE_SUCCESS = "Sorry, your input is not a valid stock.";
            } else if (full.length() < 30) {
                MESSAGE_SUCCESS = "Sorry, your input is not a valid stock.";
            } else {
                price = Float.parseFloat(full.substring(22, 30));

                Double amount = (double) Math.round(price * 100.0) / 100.0;
//                System.out.println(amount);

//                System.out.println(name);
                MESSAGE_SUCCESS = "The price of the stock " + name.fullName + " is $" + amount.toString() + ".";
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
