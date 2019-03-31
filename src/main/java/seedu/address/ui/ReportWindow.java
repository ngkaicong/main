package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.ReportEntryList;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class ReportWindow extends UiPart<Stage> {


    private static final Logger logger = LogsCenter.getLogger(ReportWindow.class);
    private static final String FXML = "ReportWindow.fxml";

    private Logic logic;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label tLabel, eLabel, iLabel;

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

    private void refresh() {
        ObservableList<Entry> filteredReportList = logic.getFilteredEntryList();
        ReportEntryList reportEntryList = new ReportEntryList(filteredReportList);

        Double total = reportEntryList.getTotal();
        Double income = reportEntryList.getTotalIncome();
        Double expense = reportEntryList.getTotalExpense();

        ObservableList<PieChart.Data> pieChartData = getExpenseIncomePieChartData(reportEntryList);
        pieChart.setData(pieChartData);
        tLabel.setText("Total (Income - Expenses): " + String.format("%.02f", total));
        iLabel.setText("Total Income: " + String.format("%.02f", income));
        eLabel.setText("Total Expense: " + String.format("%.02f", expense));

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



    private ObservableList<PieChart.Data> getExpenseIncomePieChartData(ReportEntryList reportEntryList){
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
}
