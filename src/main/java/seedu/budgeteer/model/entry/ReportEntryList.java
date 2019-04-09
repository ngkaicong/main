package seedu.budgeteer.model.entry;

import java.util.HashMap;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.budgeteer.model.tag.Tag;


/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class ReportEntryList {

    private Double total;
    private Double totalIncome;
    private Double totalExpense;
    private ObservableList<Entry> filteredEntries;
    private HashMap<String, Double> expenseCompositionMap;
    private HashMap<String, Double> incomeCompositionMap;
    private double bitcoin;

    public ReportEntryList(ObservableList<Entry> filteredEntries) {
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

    public HashMap<String, Double> getExpenseCompositionMap() {
        return this.expenseCompositionMap;
    }

    public HashMap<String, Double> getIncomeCompositionMap() {
        return this.incomeCompositionMap;
    }


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
            if (tagStr.equalsIgnoreCase("[]")) {
                tagStr = "Uncategorized";
            }
            tagStr = tagStr.replaceAll("\\[", "").replaceAll("\\]", "");

            Double value = icf.valueDouble;
            total += value;
            if (value < 0) {
                totalExpense += (-1 * value);
                if (expenseComposition.containsKey(tagStr)) {
                    Double oldVal = expenseComposition.get(tagStr);
                    expenseComposition.replace(tagStr, (oldVal + (-1 * value)));
                } else {
                    expenseComposition.put(tagStr, (-1 * value));
                }
            } else {
                totalIncome += value;
                if (incomeComposition.containsKey(tagStr)) {
                    Double oldVal = incomeComposition.get(tagStr);
                    incomeComposition.replace(tagStr, (oldVal + value));
                } else {
                    incomeComposition.put(tagStr, value);
                }
            }
        }

        this.incomeCompositionMap = incomeComposition;
        this.expenseCompositionMap = expenseComposition;
    }


}
