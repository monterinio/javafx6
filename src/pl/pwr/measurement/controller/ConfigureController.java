package pl.pwr.measurement.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.pwr.measurement.data.Data;
import pl.pwr.measurement.data.utils.TextFieldNumericValidation;

public class ConfigureController implements Initializable, DataProvider {
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField voltageField;
    private Data data;
    private ArrayList<TextField> textFieldArray;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureButtons();
        textFieldValidator();
    }

    private void configureButtons() {
        confirmButton.setDisable(true);
        confirmButton.setOnAction(x-> {
            data.setCapacity(Double.parseDouble(capacityField.getText()));
            data.setSourceVoltage(Double.parseDouble(voltageField.getText()));
            ((Stage) confirmButton.getScene().getWindow()).close();
        });

        cancelButton.setOnAction(x-> {
            ((Stage) cancelButton.getScene().getWindow()).close();
        });
    }

    private void textFieldValidator() {
        initializeTextFieldList();
        capacityField.textProperty().addListener(new TextFieldNumericValidation(confirmButton, textFieldArray, capacityField));
        voltageField.textProperty().addListener(new TextFieldNumericValidation(confirmButton, textFieldArray, voltageField));
    }

    private void initializeTextFieldList() {
        textFieldArray = new ArrayList<>();
        textFieldArray.add(capacityField);
        textFieldArray.add(voltageField);
    }

    @Override
    public void getData(Data data) {
        this.data = data;
    }

}
