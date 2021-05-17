package com.br.gabrielotsuka.data;

import java.util.List;

import static java.util.Collections.emptyList;

public class Automaton {
    private List<String> states;
    private String alphabet;
    private List<Rule> rules;
    private String initialState;
    private List<String> finalStates;

    public Automaton() {
        states = emptyList();
        alphabet = "";
        rules = emptyList();
        initialState = "";
        finalStates = emptyList();
    }

    public Automaton(List<String> states, String alphabet, List<Rule> rules, String initialState, List<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.rules = rules;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<String> getStates() {
        return states;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public void setFinalStates(List<String> finalStates) {
        this.finalStates = finalStates;
    }

    @Override
    public String toString() {
        StringBuilder outputRules = new StringBuilder();
        outputRules.append("[\n");
        for (int i = 0; i < rules.size(); i ++) {
            outputRules.append("    ").append(rules.get(i).toString());
            if (i != rules.size()-1) {
                outputRules.append(",");
            }
            outputRules.append("\n");
        }
        outputRules.append("]");

        StringBuilder outputAlphabet = new StringBuilder();
        outputAlphabet.append("[");
        for(int i = 0; i < alphabet.length(); i++) {
            outputAlphabet.append(alphabet.charAt(i));
            if (i != alphabet.length()-1) {
                outputAlphabet.append(", ");
            }
        }
        outputAlphabet.append("]");

        return "Q: " + states + ",\n" +
                "\u03A3: " + outputAlphabet + ",\n" +
                "\u03B4: " + outputRules + ",\n" +
                "q\u2080: " + initialState + ",\n" +
                "F: " + finalStates + "\n";
    }
}
