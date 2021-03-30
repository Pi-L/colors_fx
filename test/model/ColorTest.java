package model;

import colors.model.Color;
import junit.framework.TestCase;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.custom.combined.CombinedParameters;
import junitparams.naming.TestCaseName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;
import java.util.stream.Stream;

@RunWith(JUnitParamsRunner.class)
public class ColorTest extends TestCase {

    private class InnerColor {
        Color color;
        Color hexColor;

        String hexValue;
        int red;
        int green;
        int blue;

        public InnerColor(String hexValue, int red, int green, int blue) {
            this.hexValue = hexValue;
            this.red = red;
            this.green = green;
            this.blue = blue;

            color = new Color(red, green, blue);
            hexColor = new Color(hexValue);
        }
    }

    private Object[] colorParamsToTest() {
        return new Object[] {
                new Color(120,121,122),
                new Color("#78797A")
        };
    }

    private Object[] rgbHexGetToTest() {
        ArrayList<InnerColor> arrayList = new ArrayList<>();

        arrayList.add(new InnerColor("#78797A", 120, 121, 122));
        arrayList.add(new InnerColor("#000000", 0, 0, 0));
        arrayList.add(new InnerColor("#FFFFFF", 255, 255, 255));
        arrayList.add(new InnerColor("#0A64FF", 10, 100, 255));
        arrayList.add(new InnerColor("#003264", 0, 50, 100));

        Stream<Object> rgbStream = arrayList.stream()
                                                 .map(color ->
                                                        new Object[] {color.color, color.hexValue, color.red, color.green, color.blue});

        Stream<Object> hexArray = arrayList.stream()
                                                .map(color ->
                                                        new Object[] {color.hexColor, color.hexValue, color.red, color.green, color.blue});

        Object[] concatArray = Stream.concat(rgbStream, hexArray).toArray(Object[]::new);

        return concatArray;
    }

    @Test
    @Parameters(method = "rgbHexGetToTest")
    @TestCaseName("[{index}] {method}: {params}")
    public void testGetRed(Color color, String hexExpected, int redExpected, int greenExpected, int blueExpected) {
        System.out.println();
        assertEquals( redExpected, color.getRed());

        // on verifie que le getter n'a pas modifié la valeur hex
        assertEquals( hexExpected, color.getHexValue());

        // on verifie que les autres valeurs rgb ne sont pas touchées
        assertEquals( greenExpected, color.getGreen());
        assertEquals( blueExpected, color.getBlue());
    }

    @Test
    @Parameters(method = "rgbHexGetToTest")
    public void testGetGreen(Color color, String hexExpected, int redExpected, int greenExpected, int blueExpected) {
        assertEquals( greenExpected, color.getGreen());

        // on verifie que le getter n'a pas modifié la valeur hex
        assertEquals( hexExpected, color.getHexValue());

        // on verifie que les autres valeurs rgb ne sont pas touchées
        assertEquals( redExpected, color.getRed());
        assertEquals( blueExpected, color.getBlue());
    }

    @Test
    @Parameters(method = "rgbHexGetToTest")
    public void testGetBlue(Color color, String hexExpected, int redExpected, int greenExpected, int blueExpected) {
        assertEquals( blueExpected, color.getBlue());

        // on verifie que le getter n'a pas modifié la valeur hex
        assertEquals( hexExpected, color.getHexValue());

        // on verifie que les autres valeurs rgb ne sont pas touchées
        assertEquals( redExpected, color.getRed());
        assertEquals( greenExpected, color.getGreen());
    }

    @Test
    @Parameters(method = "colorParamsToTest")
    public void testSetRed(Color color) {
        color.setRed(14);
        assertEquals( 14, color.getRed());

        // on verifie que la valeur hex est bien mise à jour
        assertEquals( "#0E797A", color.getHexValue());

        // on verifie que les autres valeurs rgb ne sont pas touchées
        assertEquals( 121, color.getGreen());
        assertEquals( 122, color.getBlue());
    }

    @Test
    @Parameters(method = "colorParamsToTest")
    public void testSetGreen(Color color) {
        color.setGreen(12);
        assertEquals( 12, color.getGreen());

        // on verifie que la valeur hex est bien mise à jour
        assertEquals( "#780C7A", color.getHexValue());

        // on verifie que les autres valeurs rgb ne sont pas touchées
        assertEquals( 120, color.getRed());
        assertEquals( 122, color.getBlue());
    }

    @Test
    @Parameters(method = "colorParamsToTest")
    public void testSetBlue(Color color) {
        color.setBlue(13);
        assertEquals( 13, color.getBlue());

        // on verifie que la valeur hex est bien mise à jour
        assertEquals( "#78790D", color.getHexValue());

        // on verifie que les autres valeurs rgb ne sont pas touchées
        assertEquals( 120, color.getRed());
        assertEquals( 121, color.getGreen());
    }

    @Test
    @Parameters(method = "colorParamsToTest")
    public void testGetHexValue(Color color) {
        assertEquals( "#78797A", color.getHexValue());

        // on verifie que le getter n'a pas modifié les valeurs rgb
        assertEquals( 120, color.getRed());
        assertEquals( 121, color.getGreen());
        assertEquals( 122, color.getBlue());
    }

    @Test
    @Parameters(method = "colorParamsToTest")
    public void testSetHexValue(Color color) {
        color.setHexValue("#77777F");
        assertEquals( "#77777F", color.getHexValue());

        // on verifie que les valeurs rgb sont bien mise à jour
        assertEquals( 119, color.getRed());
        assertEquals( 119, color.getGreen());
        assertEquals( 127, color.getBlue());
    }

    @Test
    @Parameters(method = "colorParamsToTest")
    public void testToString(Color color) {
        assertEquals( "[value=#78797A, r=120, g=121, b=122]", color.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    @CombinedParameters({
            "0",
            "-1|256",
            "-1|256"
            })
    public void testWrongRGBArgsRedValid(int red, int green, int blue) {
        new Color(red, green, blue);
    }

    @Test(expected = IllegalArgumentException.class)
    @CombinedParameters({
            "-1|256",
            "0",
            "-1|256"
    })
    public void testWrongRGBArgsGreenValid(int red, int green, int blue) {
        new Color(red, green, blue);
    }

    @Test(expected = IllegalArgumentException.class)
    @CombinedParameters({
            "-1|256",
            "-1|256",
            "0"
    })
    public void testWrongRGBArgsBlueValid(int red, int green, int blue) {
        new Color(red, green, blue);
    }

    @Test(expected = IllegalArgumentException.class)
    @CombinedParameters({
            "-1|256",
            "-1|256",
            "-1|256"
    })
    public void testWrongRGBArgsNoneValid(int red, int green, int blue) {
        new Color(red, green, blue);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
            "#12345",
            "AA00AA",
            "#G23456",
            "@123456",
            "#1234567",
            "#12 3456",
            "123456#",
            "123#456",
            ""})
    public void testWrongHexArg(String hexString) {
        new Color(hexString);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "colorParamsToTest")
    public void testSetHexNullValue(Color color) {
        color.setHexValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullHexArg() {
        new Color(null);
    }


}
