package pl.pwr.measurement.data.utils;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TextFieldEmptinessValidation implements ChangeListener<String> {
    protected Button addItem;
    protected ArrayList<TextField> textFieldArray;

    public TextFieldEmptinessValidation(Button addItem, ArrayList<TextField> textFieldArray) {
        this.addItem = addItem;
        this.textFieldArray = textFieldArray;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        addItem.setDisable(TextFieldsNotEmpty.textFieldsNotEmpty(textFieldArray));
    }
}