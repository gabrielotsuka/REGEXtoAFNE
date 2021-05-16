package com.br.gabrielotsuka.data;

import java.util.List;

public class Rule {
    private String sourceState;
    private char symbol;
    private List<String> targetStates;

    public Rule(String sourceState, char symbol, List<String> targetStates) {
        this.sourceState = sourceState;
        this.symbol = symbol;
        this.targetStates = targetStates;
    }

    public String getSourceState() {
        return sourceState;
    }

    public char getSymbol() {
        return symbol;
    }

    public List<String> getTargetStates() {
        return targetStates;
    }

    public void setSourceState(String sourceState) {
        this.sourceState = sourceState;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setTargetStates(List<String> targetStates) {
        this.targetStates = targetStates;
    }

    @Override
    public String toString() {
        return sourceState + " \u00D7 " + symbol + " \u2192 " + targetStates;
    }
}