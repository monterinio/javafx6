package pl.pwr.measurement.data.utils;

import java.util.ArrayList;

import javafx.scene.control.TextField;

public class TextFieldsNotEmpty {
    public static boolean textFieldsNotEmpty(ArrayList<TextField> textFieldArray) {
        boolean result = false;
        for (int i = 0; i < textFieldArray.size(); i++) {
            result |= textFieldArray.get(i).getText().isEmpty();
        }
        return result;
    }
}
