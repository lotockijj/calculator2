package com.dominyuk.calculator;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by roman on 13.10.16.
 */
public class CalculatorBodmasTest {
    CalculatorBodmas c = new CalculatorBodmas();
    String inputLine = "12+1²−(2+3)×²√3√√1";
    String inputLine2 = "12/2−(2+3)×31";
    String inputLine3 = "12+1²−(2+3)×√31";
    String inputLine4 = "12/2−2+3×31^3";

    @Test
    public void testParse() {
        List<Object> expectedLo = new ArrayList<>();
        //1
        String checkingString = "2+3";
        expectedLo.add(new CalculatorBodmas.Constant(2));
        expectedLo.add(CalculatorBodmas.Operators.ADD);
        expectedLo.add(new CalculatorBodmas.Constant(3));
        Assert.assertEquals(expectedLo, c.parse(checkingString));
        //2
        expectedLo = new ArrayList<>();
        checkingString = ".88−384.";
        expectedLo.add(new CalculatorBodmas.Constant(0.88));
        expectedLo.add(CalculatorBodmas.Operators.SUBTRACT);
        expectedLo.add(new CalculatorBodmas.Constant(384));
        Assert.assertEquals(expectedLo, c.parse(checkingString));
    }

    @Test
    public void testCalculateParsedInput () {
        //todo create code for such a cases
        String checkingString = "(3)"; //valid
        Assert.assertEquals(3, c.calculate(checkingString), 0.1);
        checkingString = "((3))"; //valid
        Assert.assertEquals(3, c.calculate(checkingString), 0.1);
        checkingString = "(((3)))"; //valid
        Assert.assertEquals(3, c.calculate(checkingString), 0.1);
        checkingString = "1+2+(3)"; //valid
        Assert.assertEquals(6, c.calculate(checkingString), 0.1);
        checkingString = "1+2+((3))"; //valid
        Assert.assertEquals(6, c.calculate(checkingString), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException() {
            Assert.assertEquals(6, c.calculate("1+2+()"), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException2() {
        Assert.assertEquals(6, c.calculate("1+2+("), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException3() {
        Assert.assertEquals(6, c.calculate("1+2+)"), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException4() {
        Assert.assertEquals(6, c.calculate("1+2+((3)"), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException5() {
        Assert.assertEquals(6, c.calculate("1+²2"), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException7() {
        Assert.assertEquals(6, c.calculate("²2"), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException8() {
        Assert.assertEquals(6, c.calculate("2√"), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsException9() {
        Assert.assertEquals(6, c.calculate("2√+2"), 0.1);
    }

    @Test
    public void testCalculate() {
        Assert.assertEquals(5.0, c.calculate("2+3"), 0.1);
        Assert.assertEquals(4.0, c.calculate("2²"), 0.1);
        Assert.assertEquals(4.0, c.calculate("√16"), 0.1);
        Assert.assertEquals(6.0, c.calculate("2×3"), 0.1);
        Assert.assertEquals(-1, c.calculate("2−3"), 0.1);
        Assert.assertEquals(26, c.calculate("2+3×8"), 0.1);
        Assert.assertEquals(40, c.calculate("(2+3)×8"), 0.1);
        Assert.assertEquals(64, c.calculate("(2²+√16)×8"), 0.1);
        Assert.assertEquals(1, c.calculate("1×1×1"), 0.1);
        Assert.assertEquals(3.6, c.calculate("1.1+1.2+1.3"), 0.1);
    }
}
