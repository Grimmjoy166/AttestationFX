/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import service.EmployeService;
import service.EtudiantService;

/**
 * FXML Controller class
 *
 * @author Sinponzakra
 */
public class ChartsController implements Initializable {

    @FXML
    private VBox mGroup1;
    @FXML
    private VBox mGroup2;
    @FXML
    private VBox mGroup3;
    @FXML
    private Label noDataToDisplay;
    EtudiantService es = new EtudiantService();
    EmployeService emps = new EmployeService();

    //pie
    @FXML
    private PieChart chart;
    @FXML
    private PieChart chart2;

    ObservableList<PieChart.Data> pieChartData
            = FXCollections.observableArrayList();

    ObservableList<PieChart.Data> pieChartData2
            = FXCollections.observableArrayList();
    @FXML
    private DatePicker mDate;

    //Bar
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);

    private void setChart() {
        chart.setTitle("عدد التلاميذ في مستويات الدراسة");
        for (Object[] o : es.getPieChartData()) {
            if (!o[0].toString().equals("")) {
                pieChartData.add(
                        new PieChart.Data(o[0].toString(), Integer.parseInt(o[1].toString())));
            }
        }

        chart.getData().addAll(pieChartData);

    }

    private void setBarChart() {
        bc.getData().clear();
        bc.setTitle("عدد الموظفين في كل وظيفة");
        xAxis.setLabel("الوظائف");
        yAxis.setLabel("عدد الموظفين");
        
        XYChart.Series series = new XYChart.Series();
        for (Object[] o : emps.getChartData()) {  
            series.getData().add(new XYChart.Data(o[0].toString(), Integer.parseInt(o[1].toString())));
        }
        
        bc.getData().add(series);

        mGroup2.getChildren().add(bc);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChart();
        setBarChart();

        mDate.setOnAction(e -> {
            pieChartData2.clear();
            Date dt = new Date();
            Instant instant = Instant.from(mDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            dt = Date.from(instant);

            for (Object[] o : es.getPieChartData2(dt)) {
                if (!o[0].toString().equals("")) {
                    pieChartData2.add(
                            new PieChart.Data(o[0].toString(), Integer.parseInt(o[1].toString()))
                    );
                }else{
                    noDataToDisplay.setVisible(true);
                }
            }

            chart2.getData().clear();
            if (pieChartData2 != null) {
                noDataToDisplay.setVisible(false);
                chart2.setTitle("نسب المستويات الدراسية");
                chart2.getData().addAll(pieChartData2);
            }

        });
    }

}
