package com.dominyuk.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 14.10.16.
 *
 * todo q : check grammar changes
 * todo: to use this gramar we need backtracking (+Stack, +StateMachine), avoid recursion
 * CALCULATOR with BODMAS rules GRAMMAR
 * expression -> bracket expression bracket
 * expression -> subExpression operator subExpression
 * expression -> subExpression operator
 * expression -> operator subExpression
 * subExpression -> constant
 * subExpression -> expression
 * constant -> digits "." digits
 * constant -> digits "."
 * constant -> "." digits
 * constant -> digits
 * digits -> digit digits
 * digit -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
 * operator -> '²' | '√' | '/' | '*' | '+' | '-' | '%'
 * bracket -> '(' | ')'
 */
public class CalculatorBodmas {

    public static final char dotSign = '.';

    public double calculate (String input) {
        return calculateParsedInput(parse(input));
    }

    //creates list of expressions and operators
    List<Object> parse(String inputLine) {
        List<Object> lo = new ArrayList<>();
        int lineLength = inputLine.length();
        for (int i = 0; i < lineLength; i++) {
            char c = inputLine.charAt(i);
            //creator of constant:
            if (isPartOfNumber(c)) {
                String constantValue = "";
                for (; i < lineLength; i++) {
                    c = inputLine.charAt(i);
                    if (!isPartOfNumber(c)) {
                        break;
                    }
                    constantValue += c;
                }
                //assign constant
                lo.add(new Constant(Double.parseDouble(constantValue)));
                if (i == lineLength) {
                    break;
                }
            }
            switch (c) {
                case '(':
                    lo.add(Brackets.OPENING);
                    break;
                case ')':
                    lo.add(Brackets.CLOSING);
                    break;
                case '²':
                    lo.add(Operators.SQUARE);
                    break;
                case '√':
                    lo.add(Operators.SQUARE_ROOT);
                    break;
                case '÷':
                    lo.add(Operators.DIVIDE);
                    break;
                case '×':
                    lo.add(Operators.MULTIPLY);
                    break;
                case '%':
                    lo.add(Operators.PERCENTAGE);
                    break;
                case '+':
                    lo.add(Operators.ADD);
                    break;
                case '−':
                    lo.add(Operators.SUBTRACT);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown symbol used as input string");
            }
        }
        return lo;
    }

    double calculateParsedInput(List<Object> lo) {
        if (lo.size() == 1) {
            return ((Constant) lo.get(0)).evaluate();
        }
        killBrackets(lo);
        killOrder(lo);
        //todo finish it
//        killPercentage(lo);
        killOperator(lo, Operators.MULTIPLY);
        killOperator(lo, Operators.DIVIDE);
        killOperator(lo, Operators.ADD);
        killOperator(lo, Operators.SUBTRACT);
//            todo q: I'm not using any class created in the grammar:
            //find top priority operators, evaluate created compounds, make this lo shorter
            //take position of operator, take lo member before it and after it...
            //if there is no such a level operators, we need to reduce level and continue
        Object resObj = lo.get(0);
        if (lo.size() != 1 || resObj.getClass() != Constant.class) {
            throw new IllegalArgumentException("Malformed expression: IS THIS CASE POSSIBLE?");
        }
        return ((Constant) resObj).evaluate();
//        throw new RuntimeException("not finished method");
    }

    private void killBrackets(List<Object> lo) {
        for (int i = 0; i < lo.size(); i++) {
            Object oi = lo.get(i);
            if (oi == Brackets.CLOSING) {
                throw new IllegalArgumentException("Malformed expression: single closing bracket");
            }
            if (oi == Brackets.OPENING) {
                calculateInBrackets(lo, i);
            }
        }
    }

    private void calculateInBrackets(List<Object> lo, int i) {
        //every properly used object should be deleted from the list
        lo.remove(i); //all object in the lift were shifted
        int openingBracketsCounter = 1;
        List<Object> newLo = new ArrayList<>();
        for (; i < lo.size();) {
            Object oi = lo.get(i);
            if (oi == Brackets.OPENING) {
                openingBracketsCounter++;
            }
            if (oi == Brackets.CLOSING) {
                openingBracketsCounter--;
            }
            if (openingBracketsCounter == 0) {
                break;
            }
            newLo.add(oi);
            lo.remove(i);
        }
        if (newLo.size() == 0 ) {
            throw new IllegalArgumentException("Malformed expression: nothing in brackets");
        } else if (openingBracketsCounter != 0) {
                throw new IllegalArgumentException("Malformed expression: there are not closed brackets");
        } else {
            //assign value to the place of closing bracket
            lo.set(i, new Constant(calculateParsedInput(newLo)));
        }
    }

    private void killOrder(List<Object> lo) {
        for (int i = 0; i < lo.size(); i++) {
            Object oi = lo.get(i);
            if (oi == Operators.SQUARE) {
                if (i == 0) {
                    throw new IllegalArgumentException("Malformed expression: square sign should be after operand");
                }
                Object oPrevious = lo.get(i - 1);
                if (oPrevious.getClass() != (new Constant(0)).getClass()) {
                    throw new IllegalArgumentException("Malformed expression: square sign should be after operand");
                } else {
                    lo.set(i - 1, new Constant(Math.pow(((Constant) oPrevious).evaluate(), 2)));
                    lo.remove(i--);
                }
            }
            if (oi == Operators.SQUARE_ROOT) {
                if (i == lo.size() - 1) {
                    throw new IllegalArgumentException("Malformed expression: square root sign should be before " +
                            "operand");
                }
                Object oNext = lo.get(i + 1);
                if (oNext.getClass() != (new Constant(0)).getClass()) {
                    throw new IllegalArgumentException("Malformed expression: square root sign should be before " +
                            "operand");
                } else {
                    lo.set(i, new Constant(Math.sqrt(((Constant) oNext).evaluate())));
                    lo.remove(1 + i--);
                }
            }
        }
    }

    private void killOperator(List<Object> lo, Operators op) {
        for (int i = 0; i < lo.size(); i++) {
            Object oi = lo.get(i);
            if (oi == op) {
                if (i == 0 || i == lo.size() - 1) {
                    throw new IllegalArgumentException("Malformed expression: " + op.signName + " sign should be" +
                            " between operands");
                }
                Object oPrevious = lo.get(i-1);
                Object oNext = lo.get(i + 1);
                if (oPrevious.getClass() != (new Constant(0)).getClass()
                        || oNext.getClass() != (new Constant(0)).getClass()) {
                    throw new IllegalArgumentException("Malformed expression: " + op.signName + " sign should be" +
                            " between operands");
                } else {
                    double result = 0;
                    double a = ((Constant) oPrevious).evaluate();
                    double b = ((Constant) oNext).evaluate();
                    switch (op) {
                        case MULTIPLY:
                            result = a * b;
                            break;
                        case DIVIDE:
                            result = a / b;
                            break;
                        case ADD:
                            result = a + b;
                            break;
                        case SUBTRACT:
                            result = a - b;
                            break;
                        default:
                            throw new IllegalArgumentException("This Java method can be called with \"DMAS\" " +
                                    "operators only");
                    }
                    lo.set(i - 1, new Constant(result));
                    lo.remove(i);
                    lo.remove(i);
                    i--;
                }
            }
        }
    }

//    private void killPercentage()



    static abstract class Expression {
        abstract double evaluate();
    }

    //todo create hash, equals methods
    static class Constant extends Exception {
        final double value;

        Constant(double value) {
            this.value = value;
        }

        double evaluate() {return value;}

        @Override
        public int hashCode() {
            return Double.valueOf(value).hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            }
            Constant that = (Constant) o;
            return this.value == that.value;
        }
    }

    enum Operators {
        SQUARE,
        SQUARE_ROOT,
        DIVIDE("divide"),
        MULTIPLY("multiply"),
        PERCENTAGE,
        ADD("add"),
        SUBTRACT("subtract") {
            @Override
            public Operators next() {
                return null; // see below for options for this line
            }
        };

        public String signName;

        Operators() {}

        Operators(String signName) {
            this.signName = signName;
        }

        public Operators next() {
            return values()[ordinal() + 1];
        }
    }

    enum Brackets {OPENING, CLOSING}

    class Compound extends Exception {
        Expression arg1;
        Expression arg2;
        Operators op;

        public Compound(Expression arg1, Expression arg2, Operators op) {
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.op = op;
        }

        double evaluate() {
            switch (op) {
                case ADD:
                    return arg1.evaluate() + arg2.evaluate();
                case SUBTRACT:
                    return arg1.evaluate() - arg2.evaluate();
                case MULTIPLY:
                    return arg1.evaluate() * arg2.evaluate();
                case DIVIDE:
                    return arg1.evaluate() / arg2.evaluate();
                case PERCENTAGE:
                    return arg1.evaluate() % arg2.evaluate();
                default:
                    throw new RuntimeException("some problem with operators");
            }
        }
    }


    private boolean isPartOfNumber(char c) {
        return Character.isDigit(c) || c == dotSign;
    }

    //todo
    //here is good example of the same program
    //    http://stuffjava.blogspot.com/2013/04/bodmos-example-in-java.html

	/*
primary_expression
	: IDENTIFIER
	| CONSTANT
	| STRING_LITERAL
	| '(' expression ')'
	;

multiplicative_expression
	: cast_expression
	| multiplicative_expression '*' cast_expression
	| multiplicative_expression '/' cast_expression
	| multiplicative_expression '%' cast_expression
	;


additive_expression
	: multiplicative_expression
	| additive_expression '+' multiplicative_expression
	| additive_expression '-' multiplicative_expression
	;
	*/

	//todo q: what is bellow?
    //parse input
    //build tree
    //message malformed expression
}
