package com.dominyuk.calculator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Calculator implements ActionListener{

    public JPanel pane = new JPanel();

    public JButton button0, button1, button2, button3, button4, button5,
            button6,  button7, button8, button9, buttonPoint, buttonEqual,
            buttonPlus, buttonMinus, buttonDivide, buttonMultiply, buttonBackSpace,
            buttonCE, buttonSQRT, buttonPercent, buttonOneDevide,
            buttonMC, buttonMR, buttonMPlus, buttonMMinus, buttonClear,
            buttonSquareRoot, buttonFactorial, buttonPower, buttonE;

    List<JButton> listButtonOperation;
    List<JButton> listButtonOperation2;
    List<JButton> listButtonNumber;
    public JTextField displayField;

    Object src;
    Double number1 = 0.0, suma = 0.0, result = 0.0;
    boolean function;
    State state = State.ZERO;
    State2 state2 = State2.ZERO;
    private enum TwoOperatorsAtOnce{ONCE, TWO_OPERATORS_AT_ONCE};
    TwoOperatorsAtOnce numberOperators = TwoOperatorsAtOnce.ONCE;

    public Calculator() {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        listButtonOperation = new ArrayList<>();
        listButtonOperation2 = new ArrayList<>();
        listButtonNumber = new ArrayList<>();

        displayField = new JTextField("");
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.gridwidth = 7;
        c.fill = c.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = c.CENTER;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(displayField, c);

        button0 = new JButton("0");
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.fill = c.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = c.CENTER;
        pane.add(button0, c);
        listButtonNumber.add(button0);

        button1 = new JButton("1");
        c.gridx = 2;
        c.gridy = 2;
        pane.add(button1, c);
        listButtonNumber.add(button1);

        button2 = new JButton("2");
        c.gridx = 3;
        c.gridy = 2;
        pane.add(button2, c);
        listButtonNumber.add(button2);

        button3 = new JButton("3");
        c.gridx = 1;
        c.gridy = 3;
        pane.add(button3, c);
        listButtonNumber.add(button3);

        button4 = new JButton("4");
        c.gridx = 2;
        c.gridy = 3;
        pane.add(button4, c);
        listButtonNumber.add(button4);

        button5 = new JButton("5");
        c.gridx = 3;
        c.gridy = 3;
        pane.add(button5, c);
        listButtonNumber.add(button5);

        button6 = new JButton("6");
        c.gridx = 1;
        c.gridy = 4;
        pane.add(button6, c);
        listButtonNumber.add(button6);

        button7 = new JButton("7");
        c.gridx = 2;
        c.gridy = 4;
        pane.add(button7, c);
        listButtonNumber.add(button7);

        button8 = new JButton("8");
        c.gridx = 3;
        c.gridy = 4;
        pane.add(button8, c);
        listButtonNumber.add(button8);

        button9 = new JButton("9");
        c.gridx = 1;
        c.gridy = 5;
        pane.add(button9, c);
        listButtonNumber.add(button9);

        buttonPoint = new JButton(".");
        c.gridx = 2;
        c.gridy = 5;
        pane.add(buttonPoint, c);

        buttonEqual = new JButton("=");
        c.gridx = 4;
        c.gridy = 2;
        pane.add(buttonEqual, c);

        buttonPlus = new JButton("+");
        c.gridx = 3;
        c.gridy = 5;
        pane.add(buttonPlus, c);
        listButtonOperation.add(buttonPlus);

        buttonMinus = new JButton("−");
        c.gridx = 1;
        c.gridy = 6;
        pane.add(buttonMinus, c);
        listButtonOperation.add(buttonMinus);

        buttonMultiply = new JButton("×");
        c.gridx = 2;
        c.gridy = 6;
        pane.add(buttonMultiply, c);
        listButtonOperation.add(buttonMultiply);

        buttonDivide = new JButton("/");
        c.gridx = 3;
        c.gridy = 6;
        pane.add(buttonDivide, c);
        listButtonOperation.add(buttonDivide);

        buttonBackSpace = new JButton("BackSpace");
        c.gridx = 4;
        c.gridy = 3;
        pane.add(buttonBackSpace, c);

        buttonPercent = new JButton("%");
        c.gridx = 4;
        c.gridy = 6;
        pane.add(buttonPercent, c);

        buttonCE = new JButton("CE");
        c.gridx = 5;
        c.gridy = 2;
        pane.add(buttonCE, c);

        buttonSQRT = new JButton("√");
        c.gridx = 4;
        c.gridy = 4;
        pane.add(buttonSQRT, c);
        listButtonOperation2.add(buttonSQRT);

        buttonOneDevide = new JButton("1/x");
        c.gridx = 4;
        c.gridy = 5;
        pane.add(buttonOneDevide, c);
        listButtonOperation2.add(buttonOneDevide);

        buttonMMinus = new JButton("M-");
        c.gridx = 5;
        c.gridy = 3;
        pane.add(buttonMMinus, c);

        buttonMR = new JButton("MR");
        c.gridx = 5;
        c.gridy = 5;
        pane.add(buttonMR, c);

        buttonMPlus = new JButton("M+");
        c.gridx = 5;
        c.gridy = 4;
        pane.add(buttonMPlus, c);

        buttonMC = new JButton("MC");
        c.gridx = 5;
        c.gridy = 6;
        pane.add(buttonMC, c);

        buttonClear = new JButton("Clear");
        c.gridx = 6;
        c.gridy = 2;
        pane.add(buttonClear, c);

        buttonSquareRoot = new JButton("X²");
        c.gridx = 6;
        c.gridy = 3;
        pane.add(buttonSquareRoot, c);
        listButtonOperation2.add(buttonSquareRoot);

        buttonFactorial = new JButton("X!");
        c.gridx = 6;
        c.gridy = 4;
        pane.add(buttonFactorial, c);
        listButtonOperation2.add(buttonFactorial);

        buttonPower = new JButton("Xⁿ");
        c.gridx = 6;
        c.gridy = 5;
        pane.add(buttonPower, c);
        listButtonOperation.add(buttonPower);

        buttonE = new JButton("e");
        c.gridx = 6;
        c.gridy = 6;
        pane.add(buttonE, c);
        listButtonOperation2.add(buttonE);


        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(pane);
        // Set the size of the window big enough to accomodate all controls
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        for (JButton button: listButtonNumber) {
            button.addActionListener(this);
        }
        buttonBackSpace.addActionListener(this);
        buttonCE.addActionListener(this);
        buttonEqual.addActionListener(this);
        buttonMC.addActionListener(this);
        buttonMPlus.addActionListener(this);
        buttonMMinus.addActionListener(this);
        buttonMR.addActionListener(this);
        buttonPercent.addActionListener(this);
        buttonPoint.addActionListener(this);
        buttonClear.addActionListener(this);

        for (JButton button : listButtonOperation) {
            button.addActionListener(this);
        }
        for(JButton button: listButtonOperation2){
            button.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        src = e.getSource();
        try{
            while (true) {
                if (src == buttonMC) {
                    displayField.setText("");
                    suma = 0.0;
                    break;
                }
                if (src == buttonMPlus) {
                    suma += Double.parseDouble(displayField.getText());
                    break;
                } else if (src == buttonMMinus) {
                    suma -= Double.parseDouble(displayField.getText());
                    break;
                } else if (src == buttonMR) {
                    displayField.setText(suma.toString());
                    function = true;
                    break;
                }
                if(numberOperators == TwoOperatorsAtOnce.TWO_OPERATORS_AT_ONCE &&
                        isFunction(src)){    // when user pushed one operator after another
                    state = State.fromString(((JButton) src).getText());
                    function = true;
                    break;
                }
                if (isFunction2(src)) {
                    state2 = State2.fromString(((JButton) src).getText());
                    result = state2.apply(result);
                    displayField.setText(result.toString());
                    state2 = State2.ZERO;
                    break;
                }
                if(src == buttonEqual && state == State.EQUALS){ //if user pushed to times button "=" one times after another... line 352
                    break;                                       // the last operator is used after that...
                }
                if(state == State.EQUALS && (isNumber(src) || src == buttonPoint)){
                    function = true;
                }

                if (function) {
                    displayField.setText("");
                    function = false;
                }
                if (isNumber(src)) {
                    JButton b = (JButton) src;
                    displayField.setText(displayField.getText() + b.getText());
                    result = numberReader();
                    numberOperators = TwoOperatorsAtOnce.ONCE;
                    break;
                }
                if(src == buttonPoint && displayField.getText().indexOf('.') < 0){
                    displayField.setText(displayField.getText() + ".");
                    result = numberReader();
                    numberOperators = TwoOperatorsAtOnce.ONCE;
                    break;
                }

                if (src == buttonBackSpace && displayField.getText().length() > 0) {
                    displayField.setText(displayField.getText().substring(0, displayField.getText().length() - 1));
                }
                if (src == buttonClear || src == buttonCE) {
                    displayField.setText("");
                    defaultData();
                }

                if(src == buttonPercent){
                    if(state == State.PLUS || state == State.MINUS) result = number1*result/100;
                    if(state == State.MULTIPLY || state == State.DIVIDE) result = result/100;
                }

                if (isFunction(src)) {
                    function = true;
                    if(isStateEqualsToOperation()){
                        result = state.apply(number1, result);
                        displayField.setText(result.toString());
                        state = State.ZERO;
                    }
                    if (src == buttonEqual) {
                        displayField.setText(result.toString());
                        number1 = Double.parseDouble(displayField.getText());
                        defaultData();
                    }
                    state = State.fromString(((JButton) src).getText());
                    number1 = Double.parseDouble(displayField.getText());
                    numberOperators = TwoOperatorsAtOnce.TWO_OPERATORS_AT_ONCE;
                }
                break;
            }
        } catch (NumberFormatException e1) {
            System.out.println(e1);
        }

    }

    private double numberReader(){
        return Double.valueOf(displayField.getText());
    }

    private boolean isNumber(Object src){
        function = false;
        for (JButton button: listButtonNumber) {
            if(src == button){
                return true;
            }
        }
        return  false;
    }

    private boolean isFunction(Object src){
        for (JButton button : listButtonOperation) {
            if (src == button || src == buttonEqual || src == buttonPercent) {
                return true;
            }
        }
        return false;
    }

    private boolean isFunction2(Object src){
        for(JButton button : listButtonOperation2){
            if(src == button){
                return true;
            }
        }
        return false;
    }

    private boolean isStateEqualsToOperation(){
        for (JButton button: listButtonOperation) {
            if(state == State.fromString(button.getText())){
                return true;
            }
        }
        return false;
    }

    private void defaultData(){
        result = 0.0;
        function = false;
        state = State.ZERO;
        if(src == buttonEqual){
            state = State.EQUALS; // when user pushed two times button "=" one times after another...  line 251
        }
        number1 = 0.0;
    }

      public static void main(String[] args){
        Calculator calc = new Calculator();
      }
}