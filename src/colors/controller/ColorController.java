package colors.controller;

import colors.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorController implements Initializable {

    private Color color;

    // View Fields
    @FXML
    private Slider sliderRed;

    @FXML
    private Slider sliderGreen;

    @FXML
    private Slider sliderBlue;

    @FXML
    private TextField textFieldRed;

    @FXML
    private TextField textFieldGreen;

    @FXML
    private TextField textFieldBlue;

    @FXML
    private TextField textFieldHex;

    @FXML
    private Pane paneColorDisplay;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initSliderListeners();


    }

    private void initSliderListeners() {
        sliderRed.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            textFieldRed.textProperty().setValue(stringFromNumber(newValue));
        });

        sliderGreen.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            textFieldGreen.textProperty().setValue(stringFromNumber(newValue));
        });

        sliderBlue.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            textFieldBlue.textProperty().setValue(stringFromNumber(newValue));
        });
    }

    private void initTextFieldListener() {
        // textFieldRed.setOnAction();
    }

    private String stringFromNumber(Number number) {
        return String.valueOf(number.intValue());
    }
}
