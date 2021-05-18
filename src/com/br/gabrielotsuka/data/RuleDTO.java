package com.br.gabrielotsuka.data;

import java.util.List;

public class RuleDTO {
        private final String sourceState;
        private final char symbol;
        private final List<String> targetState;
        private final boolean emptyStateRule;

    public RuleDTO(String sourceState, char symbol, List<String> targetState, boolean emptyStateRule) {
            this.sourceState = sourceState;
            this.symbol = symbol;
            this.targetState = targetState;
            this.emptyStateRule = emptyStateRule;
        }

        public String getSourceState() {
            return sourceState;
        }

        public char getSymbol() {
            return symbol;
        }

        public List<String> getTargetStates() {
            return targetState;
        }

    public boolean getEmptyStateRule() {
        return emptyStateRule;
    }

    @Override
    public String toString() {
        return "RuleDTO{" +
                "sourceState='" + sourceState + '\'' +
                ", symbol=" + symbol +
                ", targetState=" + targetState +
                ", emptyStateRule=" + emptyStateRule +
                '}';
    }
}
