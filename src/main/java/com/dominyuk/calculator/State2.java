package com.dominyuk.calculator;

/**
 * Created by Роман Лотоцький on 02.11.2016.
 */
public enum State2 implements OperatorOne {

    ZERO("0") {
        public double apply(double x) {
            return 0;
        }
    },
    SQRT("√") {
        public double apply(double x) {
            return Math.sqrt(x);
        }
    },
    ONEDIVIDE("1/x") {
        public double apply(double x) {
            return 1/x;
        }
    },
    SQUAREROOT("X²") {
        public double apply(double x) {
            return x*x;
        }
    },
    FACTORIAL("X!"){
        public double apply(double x){
            double z = 1;
            for (int i = 1; i <= x; i++) {
                z = z*i;
            }
            x = z;
            return x; }
    },
    E("e"){
        public double apply(double x){
            return 2.718281828459045;
        }
    };
    private final String symbol;

    State2(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static State2 fromString(String text) {
        if (text != null) {
            for (State2 b : State2.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    return b;
                }
            }
        }
        return null;
    }
}
