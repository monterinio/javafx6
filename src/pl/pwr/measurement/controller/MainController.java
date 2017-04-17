package pl.pwr.measurement.controller;

import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;
import pl.pwr.measurement.data.ConnectionData;
import pl.pwr.measurement.data.Data;
import pl.pwr.measurement.data.Strings;
import pl.pwr.measurement.util.ConnectionUtil;
import pl.pwr.measurement.util.SaveLoadUtil;
import pl.pwr.measurement.util.WindowUtil;

public class MainController implements Initializable {

    private Data data;
    private ConnectionData connectionData;

    @FXML
    private MenuItem connectionSettingsItem;
    @FXML
    private MenuItem closeItem;
    @FXML
    private MenuItem aboutItem;
    @FXML
    private Button startMeasurement;
    @FXML
    private Button stopMeasurement;
    @FXML
    private AreaChart<Double, Double> areaChart;
    @FXML
    private Button configureButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private XYChart.Series<Double, Double> dataSeries;
    private Timeline animation;

    public MainController() {
        data = new Data();
        connectionData = SaveLoadUtil.loadApplicationState(Strings.FILE_NAME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureMenuItems();
        configureButtons();
        configurePlotting();
    }

    private void configureMenuItems() {
        connectionSettingsItem.setOnAction(x -> WindowUtil.loadWindowAndSendData(Strings.CONNECTION_LAYOUT_NAME,
                Strings.CONNECTION_SETTINGS_ITEM_NAME, connectionData));
        aboutItem.setOnAction(x -> WindowUtil.loadWindow(Strings.ABOUT_LAYOUT_NAME, Strings.ABOUT_ITEM_NAME));
        closeItem.setOnAction(x -> WindowUtil.loadWindow(Strings.EXIT_LAYOUT_NAME, Strings.EXIT_ITEM_NAME));
    }

    private void configureButtons() {
        configureButton();
        startButton();
        stopButton();
    }

    private void configureButton() {
        configureButton.setOnAction(x -> {
            WindowUtil.loadWindowAndSendData(Strings.CONFIGURE_LAYOUT_NAME, Strings.CONFIGURE_ITEM_NAME, data);
        });
    }

    private void startButton() {
        startButton.setOnAction(x -> {
            if(data.getSourceVoltage()!=0 && data.getCapacity()!=0) {
                data.setStartMeasurement(Instant.now());
                configureButton.setDisable(true);
                dataSeries.getData().add(new XYChart.Data<Double, Double>(0.0, 0.0));
                animation.play();
            } else {
                WindowUtil.loadWindow(Strings.WARNING_LAYOUT_NAME, Strings.WARNING_ITEM_NAME);
            }
        });
    }

    private void stopButton() {
        stopButton.setOnAction(x -> {
            animation.stop();
            dataSeries.getData().clear();
            configureButton.setDisable(false);
        });
    }

    private void configurePlotting() {
        animation = new Timeline();
        areaChart.setAnimated(false);
        dataSeries = new XYChart.Series<>();
        animation.setCycleCount(Animation.INDEFINITE);
        areaChart.getData().add(dataSeries);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(1000), (ActionEvent actionEvent) -> {
            plotTime();
            ConnectionUtil.run(connectionData, data);
            if(data.getChartPoint()>0.95*data.getSourceVoltage()) {
                WindowUtil.loadWindow(Strings.FINISH_LAYOUT_NAME, Strings.FINISH_ITEM_NAME);
                animation.stop();
            }
        }));
    }

    private void plotTime() {
        dataSeries.getData().add(new XYChart.Data<Double, Double>(data.getTime(), data.getChartPoint()));
    }

    public ConnectionData getConnectionData() {
        return connectionData;
    }
}
