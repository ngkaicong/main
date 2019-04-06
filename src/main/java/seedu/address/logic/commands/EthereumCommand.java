package seedu.address.logic.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.entry.CashFlow;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.ReportEntryList;

/**
 * Returns how many Ethereum you can buy at the current market price.
 */
public class EthereumCommand extends Command {

    public static final String COMMAND_WORD = "ethereum";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays how much ethereum you can buy.\n"
            + "Example: " + COMMAND_WORD;

    public String MESSAGE_SUCCESS = "You are able to buy ";

    private final Predicate predicate;

    public EthereumCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        double price = 0.0;
        try {
            URL url = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH&tsyms=USD");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

//            System.out.println("Output from Server .... \n");
            String output = br.readLine();
//            System.out.println(output);
            //            while ((output = br.readLine()) != null) {
            //                System.out.println(output);
            //                temp = output;
            //            }
//            System.out.println(output.substring(14, 21));

            model.updateFilteredEntryList(this.predicate);
            ObservableList<Entry> filteredList = model.getFilteredEntryList();
            ReportEntryList reportList = new ReportEntryList(filteredList);
            Double total = reportList.getTotal();
//            System.out.println(total);

            price = Float.parseFloat(output.substring(14, 20));
//            System.out.println(price);

            Double amount = total / price;
            amount = (double) Math.round(amount * 100.0) / 100.0;

            MESSAGE_SUCCESS = MESSAGE_SUCCESS + amount.toString() + " ethereum.";

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double roundOff = (double) Math.round(price * 100.0) / 100.0;

        // This is where you divide the cashflow by the price of ethereum, and add it to the message

        String currentPrice = " The current price of ethereum is $" + roundOff + ".";
        MESSAGE_SUCCESS = MESSAGE_SUCCESS + currentPrice;
//        System.out.println("The current price of ethereum is $" + roundOff ".");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
