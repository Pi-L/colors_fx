package colors.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {

    private int red;
    private int green;
    private int blue;
    private String hexValue;

    public Color(int rouge, int vert, int bleu) throws IllegalArgumentException {
        checkRGBValueValidity(rouge, vert, bleu);

        red = rouge;
        green = vert;
        blue = bleu;

        initHexValue(rouge, vert, bleu);
    }

    public Color(String couleurHexa) throws IllegalArgumentException {
        setHexValue(couleurHexa);

        initRGB(couleurHexa);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public String getHexValue() {
        return hexValue;
    }

    public void setRed(int red) throws IllegalArgumentException {
        checkRGBValueValidity(red);

        this.red = red;
        initHexValue(this.red, this.green, this.blue);
    }

    public void setGreen(int green) throws IllegalArgumentException {
        checkRGBValueValidity(green);

        this.green = green;
        initHexValue(this.red, this.green, this.blue);
    }

    public void setBlue(int blue) throws IllegalArgumentException {
        checkRGBValueValidity(blue);

        this.blue = blue;
        initHexValue(this.red, this.green, this.blue);
    }

    public void setHexValue(String hexValue) throws IllegalArgumentException {
        checkHexValueValidity(hexValue);

        this.hexValue = hexValue;

        initRGB(this.hexValue);
    }

    private void initRGB(String hexValue) {
        String cleanHexString = hexValue.replace("#", "");
        String redString = cleanHexString.substring(0, 2);
        String greenString = cleanHexString.substring(2, 4);
        String blueString = cleanHexString.substring(4, 6);

        red = Integer.parseInt(redString, 16);
        green = Integer.parseInt(greenString, 16);
        blue = Integer.parseInt(blueString, 16);
    }

    private void initHexValue(int red, int green, int blue) {
        String hexString = "#"+getSingleHexValuePaddedUpperCase(red)+getSingleHexValuePaddedUpperCase(green)+getSingleHexValuePaddedUpperCase(blue);

        setHexValue(hexString);
    }

    private void checkRGBValueValidity(int ...rgbValues) throws IllegalArgumentException {
        for (int rgbValue: rgbValues) {
            if(rgbValue < 0 || rgbValue > 255) throw new IllegalArgumentException();
        }
    }

    private void checkHexValueValidity(String hexStringValue) throws IllegalArgumentException {

        if(hexStringValue == null) throw new IllegalArgumentException();

        Pattern hexPattern = Pattern.compile("^#[0-9A-F]{6}$");
        Matcher matcher = hexPattern.matcher(hexStringValue);

        if(hexStringValue.length() != 7 || !matcher.matches()) throw new IllegalArgumentException();
    }

    private String getSingleHexValuePaddedUpperCase(int value) {
        String hexStringValue = Integer.toHexString(value).toUpperCase();

        if(hexStringValue.length() == 1) {
            hexStringValue = "0"+hexStringValue;
        }

        return hexStringValue;
    }

    @Override
    public String toString() { // [value=#78797A, r=120, g=121, b=122]
        return "[value=" + hexValue + ", r=" + red + ", g=" + green + ", b=" + blue + "]";
    }
}
