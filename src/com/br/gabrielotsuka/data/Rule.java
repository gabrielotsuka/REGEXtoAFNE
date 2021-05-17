package com.br.gabrielotsuka.data;

import java.util.List;

public class Rule {
    private final String sourceState;
    private final char symbol;
    private final List<String> targetStates;

    public Rule(String sourceState, char symbol, List<String> targetStates) {
        this.sourceState = sourceState;
        this.symbol = symbol;
        this.targetStates = targetStates;
    }

    @Override
    public String toString() {
        return sourceState + " \u00D7 " + symbol + " \u2192 " + targetStates;
    }
}