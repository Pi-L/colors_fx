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

            if(newValue == null || newValue.equals(oldValue))  return;

            color.setRed(newValue.intValue());
            update();
        });

        sliderGreen.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue == null || newValue.equals(oldValue))  return;

            color.setGreen(newValue.intValue());
            update();

        });

        sliderBlue.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue == null || newValue.equals(oldValue))  return;

            color.setBlue(newValue.intValue());
            update();

        });
    }

    private void initTextFieldListener() {

        textFieldRed.textProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue == null || newValue.equals(oldValue))  return;

            try {
                color.setRed(Integer.parseInt(newValue));
                textFieldRed.setStyle("-fx-border-color: none");
                update();

            } catch (IllegalArgumentException e) {
                textFieldRed.setStyle("-fx-border-color: #FF0000");
            }

        });

        textFieldGreen.textProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue == null || newValue.equals(oldValue))  return;

            try {
                color.setGreen(Integer.parseInt(newValue));
                textFieldGreen.setStyle("-fx-border-color: none");
                update();

            } catch (IllegalArgumentException e) {
                textFieldGreen.setStyle("-fx-border-color: #FF0000");
            }

        });

        textFieldBlue.textProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue == null || newValue.equals(oldValue))  return;

            try {
                color.setBlue(Integer.parseInt(newValue));
                textFieldBlue.setStyle("-fx-border-color: none");
                update();

            } catch (IllegalArgumentException e) {
                textFieldBlue.setStyle("-fx-border-color: #FF0000");
            }

        });

        textFieldHex.textProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue != null && !newValue.equals(oldValue)) {
                try {
                    color.setHexValue(newValue.toUpperCase());
                    textFieldHex.setStyle("-fx-border-color: none");
                    update();
                } catch (IllegalArgumentException e) {
                    textFieldHex.setStyle("-fx-border-color: #FF0000");
                }
            }
        });
    }

    private void update() {
        updateColorPane();
        updateTextFieldValue();
        updateSliderValue();
    }

    private void updateColorPane() {
        paneColorDisplay.setStyle("-fx-background-color: " + color.getHexValue());
    }

    private void updateTextFieldValue() {
        textFieldRed.textProperty().setValue(String.valueOf(color.getRed()));
        textFieldGreen.textProperty().setValue(String.valueOf(color.getGreen()));
        textFieldBlue.textProperty().setValue(String.valueOf(color.getBlue()));
        textFieldHex.textProperty().setValue(color.getHexValue());
    }

    private void updateSliderValue() {
        sliderRed.setValue(color.getRed());
        sliderGreen.setValue(color.getGreen());
        sliderBlue.setValue(color.getBlue());
    }
}
