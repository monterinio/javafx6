package pl.pwr.measurement.data.utils;

import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TextFieldChoiceBoxEmptinessValidation extends TextFieldEmptinessValidation {

    private boolean choiceBoxState;

    public TextFieldChoiceBoxEmptinessValidation(Button addItem, ArrayList<TextField> textFieldArray,
            boolean choiceBoxState) {
        super(addItem, textFieldArray);
        this.choiceBoxState = choiceBoxState;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        boolean addItemState = TextFieldsNotEmpty.textFieldsNotEmpty(textFieldArray) & choiceBoxState;
        addItem.setDisable(addItemState);
    }

}
