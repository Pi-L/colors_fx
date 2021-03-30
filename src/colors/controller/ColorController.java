package colors.controller;

import colors.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.awt.event.MouseEvent;
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
    private Pane paneMain;

    @FXML
    private Pane paneColorDisplay;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        color = new Color(0, 0, 0);

        updateColorPane();

        initSliderListeners();
        initTextFieldListener();


    }

    private void initSliderListeners() {
        sliderRed.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            color.setRed(newValue.intValue());
            updateColorPane();
            updateTextFieldValue();
        });

        sliderGreen.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            color.setGreen(newValue.intValue());
            updateColorPane();
            updateTextFieldValue();
        });

        sliderBlue.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            color.setBlue(newValue.intValue());
            updateColorPane();
            updateTextFieldValue();
        });
    }

    private void initTextFieldListener() {

        textFieldRed.textProperty().addListener((observableValue, oldValue, newValue) -> {

        });
    }

//    private void setColorValues() {s
//        int redValue = (int) sliderRed.getValue();
//        int greenValue = (int) sliderRed.getValue();
//        int blueValue = (int) sliderRed.getValue();
//
//    }

    private void updateColorPane() {
        paneColorDisplay.setStyle("-fx-background-color: " + color.getHexValue());
    }

    private void updateTextFieldValue() {
        textFieldRed.textProperty().setValue(String.valueOf(color.getRed()));
        textFieldGreen.textProperty().setValue(String.valueOf(color.getGreen()));
        textFieldBlue.textProperty().setValue(String.valueOf(color.getBlue()));
        textFieldHex.textProperty().setValue(color.getHexValue());
    }

    private String stringFromNumber(Number number) {
        return String.valueOf(number.intValue());
    }
}
