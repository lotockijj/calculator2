package com.dominyuk.calculator;

/**
 * Created by Роман Лотоцький on 25.10.2016.
 */
public enum State implements OperationTwo {

    ZERO("0") {
        public double apply(double x, double y){ return 0;}
    },
    PLUS("+") {
        public double apply(double x, double y) { return x + y; }
    },
    MINUS("−") {
        public double apply(double x, double y) { return x - y; }
    },
    MULTIPLY("×") {
        public double apply(double x, double y) { return x * y; }
    },
    DIVIDE("/") {
        public double apply(double x, double y) { return x / y; }
    },
    EQUALS("="){
        public double apply(double x, double y) { return x; }
    },
    POWER("Xⁿ"){
        public double apply(double x, double y){
            double z = x;
            for (int i = 1; i < y; i++) {
                x = x*z;
            }
            return x; }
    };

    private final String symbol;

    State(String symbol) {
        this.symbol = symbol;
    }
    @Override
    public String toString() {
        return symbol;
    }

    public static State fromString(String text) {
        if (text != null) {
            for (State b : State.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    return b;
                }
            }
        }
        return null;
    }

}
