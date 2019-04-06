package seedu.address.model.entry;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.ModelManager;
import seedu.address.logic.commands.BitcoinCommand;
import seedu.address.model.tag.Tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;


/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class ReportEntryList {

    private Double total;
    private Double totalIncome;
    private Double totalExpense;
    private ObservableList<Entry>  filteredEntries;
    private HashMap<String, Double> expenseCompositionMap;
    private HashMap<String, Double> incomeCompositionMap;
    private double bitcoin;

    public ReportEntryList(ObservableList<Entry>  filteredEntries) {
        this.total = 0.0;
        this.totalIncome = 0.0;
        this.totalExpense = 0.0;
        this.filteredEntries = filteredEntries;

        updateTotals();
    }

    /**
     * Returns the simple class name of the identifier used to categorise each entry in list
     */
    public Double getTotal() {
        return this.total;
    }

    public Double getTotalIncome() {
        return this.totalIncome;
    }

    public Double getTotalExpense() {
        return this.totalExpense;
    }

    public HashMap<String, Double> getExpenseCompositionMap() { return this.expenseCompositionMap; }

    public HashMap<String, Double> getIncomeCompositionMap() { return this.incomeCompositionMap; }


    /**
     * Returns the size of the internal map
     */
    public int size() {
        return this.filteredEntries.size();
    }

    /**
     * Checks if the internal map is empty
     *
     * @return true if empty and false if otherwise
     */
    public boolean isEmpty() {
        return this.filteredEntries.isEmpty();
    }


    /**
     * Update the total moneyflow, total income and total expense
     */
    private void updateTotals() {
        HashMap<String, Double> incomeComposition = new HashMap<String, Double>();
        HashMap<String, Double> expenseComposition = new HashMap<String, Double>();

        for (Entry i : this.filteredEntries) {
            CashFlow icf = i.getCashFlow();
            Set<Tag> iTags = i.getTags();
            String tagStr = iTags.toString();
            if (tagStr.equalsIgnoreCase("[]"))
                tagStr = "Uncategorized";
            tagStr = tagStr.replaceAll("\\[", "").replaceAll("\\]", "");

            Double value = icf.valueDouble;
            total += value;
            if (value < 0) {
                totalExpense +=  (-1 * value);
                if (expenseComposition.containsKey(tagStr)) {
                    Double oldVal = expenseComposition.get(tagStr);
                    expenseComposition.replace(tagStr, (oldVal + (-1*value)));
                }
                else {
                    expenseComposition.put(tagStr, (-1 * value));
                }
            }

            else {
                totalIncome += value;
                if (incomeComposition.containsKey(tagStr)) {
                    Double oldVal = incomeComposition.get(tagStr);
                    incomeComposition.replace(tagStr, (oldVal + value));
                }
                else {
                    incomeComposition.put(tagStr, value);
                }
            }
        }

        this.incomeCompositionMap = incomeComposition;
        this.expenseCompositionMap = expenseComposition;
    }

    public Double getBitcoin() {
        double price = 0.0;
        try {
            URL url = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC&tsyms=USD");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();

            price = Float.parseFloat(output.substring(14, 21));

            Double amount = total / price;
            price = (double) Math.round(amount * 100.0) / 100.0;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }

}
