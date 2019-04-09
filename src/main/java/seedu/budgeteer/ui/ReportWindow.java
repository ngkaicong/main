package seedu.budgeteer.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.budgeteer.commons.core.LogsCenter;
import seedu.budgeteer.logic.Logic;
import seedu.budgeteer.logic.parser.ReportCommandParser;
import seedu.budgeteer.model.entry.Entry;
import seedu.budgeteer.model.entry.ReportEntryList;



/**
 * Controller for a help page
 */
public class ReportWindow extends UiPart<Stage> {


    private static final Logger logger = LogsCenter.getLogger(ReportWindow.class);
    private static final String FXML = "ReportWindow.fxml";

    private Logic logic;

    private Boolean isDetailsHidden = false;

    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart expenseInsightPieChart;
    @FXML
    private PieChart incomeInsightPieChart;
    @FXML
    private Label tLabel;
    @FXML
    private Label eLabel;
    @FXML
    private Label iLabel;
    @FXML
    private Label bitcoinLabel;
    @FXML
    private Label incomeBreakdownLabel;
    @FXML
    private Label expenseBreakdownLabel;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public ReportWindow(Stage root, Logic logic) {
        super(FXML, root);
        this.logic = logic;

        refresh();
    }

    /**
     * Creates a new HelpWindow.
     */
    public ReportWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Refreshes the display with updated filtered entry list.
     */
    private void refresh() {
        ObservableList<Entry> filteredReportList = logic.getFilteredEntryList();
        ReportEntryList reportEntryList = new ReportEntryList(filteredReportList);

        Double total = reportEntryList.getTotal();
        Double income = reportEntryList.getTotalIncome();
        Double expense = reportEntryList.getTotalExpense();

        ObservableList<PieChart.Data> pieChartData = getExpenseIncomePieChartData(reportEntryList);
        ObservableList<PieChart.Data> expenseInsightPieChartData = getExpenseInsightPieChartData(reportEntryList);
        ObservableList<PieChart.Data> incomeInsightPieChartData = getIncomeInsightPieChartData(reportEntryList);

        pieChart.setData(pieChartData);
        expenseInsightPieChart.setData(expenseInsightPieChartData);
        incomeInsightPieChart.setData(incomeInsightPieChartData);
        tLabel.setText("Total (Income - Expenses): " + String.format("%.02f", total));
        iLabel.setText("Total Income: " + String.format("%.02f", income));
        eLabel.setText("Total Expense: " + String.format("%.02f", expense));

        if (!ReportCommandParser.isRequireDetailedReport() && !isDetailsHidden) {
            expenseInsightPieChart.setVisible(false);
            incomeInsightPieChart.setVisible(false);
            expenseBreakdownLabel.setVisible(false);
            incomeBreakdownLabel.setVisible(false);
            expenseInsightPieChart.setManaged(false);
            incomeInsightPieChart.setManaged(false);
            expenseBreakdownLabel.setManaged(false);
            incomeBreakdownLabel.setManaged(false);
            isDetailsHidden = true;
        } else if (ReportCommandParser.isRequireDetailedReport() && isDetailsHidden) {
            expenseInsightPieChart.setVisible(true);
            incomeInsightPieChart.setVisible(true);
            expenseBreakdownLabel.setVisible(true);
            incomeBreakdownLabel.setVisible(true);
            expenseInsightPieChart.setManaged(true);
            incomeInsightPieChart.setManaged(true);
            expenseBreakdownLabel.setManaged(true);
            incomeBreakdownLabel.setManaged(true);
            isDetailsHidden = false;
        }

    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        refresh();
        getRoot().show();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        refresh();
        getRoot().requestFocus();
    }



    private ObservableList<PieChart.Data> getExpenseIncomePieChartData(ReportEntryList reportEntryList) {
        Double income = reportEntryList.getTotalIncome();
        Double expense = reportEntryList.getTotalExpense();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Income", income),
                new PieChart.Data("Expense", expense)
        );

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " $", data.pieValueProperty()
                        )
                )
        );

        return pieChartData;
    }

    private ObservableList<PieChart.Data> getIncomeInsightPieChartData(ReportEntryList reportEntryList) {
        ArrayList<PieChart.Data> pieChartDataArr = new ArrayList<>();
        HashMap<String, Double> incomeInsight = reportEntryList.getIncomeCompositionMap();
        Iterator it = incomeInsight.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            PieChart.Data pieData = new PieChart.Data((String) pair.getKey(), (Double) pair.getValue());
            pieChartDataArr.add(pieData);
            it.remove(); // avoids a ConcurrentModificationException
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(pieChartDataArr);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " $", data.pieValueProperty()
                        )
                )
        );

        return pieChartData;

    }

    private ObservableList<PieChart.Data> getExpenseInsightPieChartData(ReportEntryList reportEntryList) {
        ArrayList<PieChart.Data> pieChartDataArr = new ArrayList<>();
        HashMap<String, Double> expenseInsight = reportEntryList.getExpenseCompositionMap();
        Iterator it = expenseInsight.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            PieChart.Data pieData = new PieChart.Data((String) pair.getKey(), (Double) pair.getValue());
            pieChartDataArr.add(pieData);
            it.remove(); // avoids a ConcurrentModificationException
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(pieChartDataArr);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " $", data.pieValueProperty()
                        )
                )
        );

        return pieChartData;

    }
}
