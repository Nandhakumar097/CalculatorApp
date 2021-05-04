package com.example.calculatorapp;

public class HistoryData {

    private String expression;
    private String total;

    public HistoryData(String expression, String total) {
        this.expression = expression;
        this.total = total;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "HistoryData{" +
                "expression='" + expression + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
