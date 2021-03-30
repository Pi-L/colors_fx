package colors.controller;

import colors.model.Color;
import javafx.embed.swing.SwingFXUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.FileChooser;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorController implements Initializable {

    private Color color;

    @FXML
    private AnchorPane anchorPaneMain;

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


    @FXML
    private CheckBox checkBoxEraser;

    @FXML
    private TextField textFieldBrushWidth;

    @FXML
    private Canvas canvasWhiteBoard;


    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemSave;


    private GraphicsContext gc;

    private boolean isEraser = false;

    private double brushWidth = 12;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        color = new Color(0, 0, 0);

        updateColorPane();

        initSliderListeners();
        initTextFieldListener();
        initCanvasListener();
        initMenuListener();

        gc = canvasWhiteBoard.getGraphicsContext2D();

        gc.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gc.setLineCap( StrokeLineCap.ROUND );
        gc.setLineJoin( StrokeLineJoin.ROUND);

        BoxBlur blur = new BoxBlur();
        blur.setWidth(1);
        blur.setHeight(1);
        blur.setIterations(1);
        gc.setEffect(blur);

        updateCanvasPaint();

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

        setupMouseType(sliderRed, Cursor.HAND);
        setupMouseType(sliderGreen, Cursor.HAND);
        setupMouseType(sliderBlue, Cursor.HAND);
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

    private void initCanvasListener() {

        canvasWhiteBoard.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.stroke();
                    });

        canvasWhiteBoard.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                        gc.lineTo(event.getX(), event.getY());
                        gc.stroke();
                    });

        checkBoxEraser.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

            isEraser = newValue;
            updateCanvasPaint();
        });

        setupMouseType(checkBoxEraser, Cursor.HAND);

        setupMouseType(canvasWhiteBoard, Cursor.CROSSHAIR);

        textFieldBrushWidth.textProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue == null || newValue.equals(oldValue))  return;

            try {
                Pattern pattern = Pattern.compile("^\\d{1,2}$");
                Matcher matcher = pattern.matcher(newValue);

                if(!matcher.matches()) throw new IllegalArgumentException();

                int width = Integer.parseInt(newValue, 10);


                if(width <= 0 || width > 99) throw new IllegalArgumentException();

                brushWidth = width;

                update();

            } catch (IllegalArgumentException e) {
                textFieldBrushWidth.setStyle("-fx-border-color: #ff0000");
            }

        });
    }

    private void initMenuListener() {
        menuItemClose.setOnAction(event -> {
            System.exit(0);
        });

        menuItemSave.setOnAction(event -> {

            WritableImage image = canvasWhiteBoard.snapshot(new SnapshotParameters(), null);

            //Set extension filter
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(anchorPaneMain.getScene().getWindow());

            try
            {
                if(file == null) return;

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

            } catch (IOException ex)
            {
                System.out.println(ex.toString());
            }
        });

    }

    private void setupMouseType(Node el, Cursor cursor) {

        el.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            Scene scene = anchorPaneMain.getScene();
            scene.setCursor(cursor);
        });

        el.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            Scene scene = anchorPaneMain.getScene();
            scene.setCursor(Cursor.DEFAULT);
        });
    }

    private void update() {
        updateColorPane();
        updateTextFieldValue();
        updateSliderValue();
        updateCanvasPaint();
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

    private void updateCanvasPaint() {

        double paintColorRed;
        double paintColorGreen;
        double paintColorBlue;

        if(isEraser) {
            paintColorRed = 1;
            paintColorGreen = 1;
            paintColorBlue = 1;

        } else {
            paintColorRed = color.getRed() / 255D;
            paintColorGreen = color.getGreen() / 255D;
            paintColorBlue = color.getBlue() / 255D;
        }

        javafx.scene.paint.Color colorCanvas = new javafx.scene.paint.Color(paintColorRed, paintColorGreen, paintColorBlue, 1);

        gc.setStroke(colorCanvas);
        gc.setLineWidth(brushWidth);

    }
}
