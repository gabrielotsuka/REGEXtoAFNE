package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.data.Node;
import com.br.gabrielotsuka.data.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class AutomatonService {

    int counter = 0;

    public Automaton buildLeaf(char value) {
        String init = "q" + counter++;
        String fin = "q" + counter++;
        return new Automaton(
                Arrays.asList(init, fin),
                String.valueOf(value),
                singletonList(new Rule(init, value, singletonList(fin))),
                init,
                singletonList(fin)
        );
    }

    public Automaton buildClosure(Node node) {
        String init = "q" + counter++;
        String fin = "q" + counter++;
        Automaton oldAutomaton = node.automaton;

        List<String> newStates = new ArrayList<>();
        newStates.add(init);
        newStates.addAll(oldAutomaton.getStates());
        newStates.add(fin);

        String newAlphabet = oldAutomaton.getAlphabet();
        List<Rule> newRules = new ArrayList<>();
        newRules.add(new Rule(init, 'ε', Arrays.asList(fin, oldAutomaton.getInitialState())));
        newRules.addAll(oldAutomaton.getRules());
        newRules.add(new Rule(oldAutomaton.getFinalStates().get(0), 'ε', Arrays.asList(fin, oldAutomaton.getInitialState())));

        return new Automaton(newStates, newAlphabet, newRules, init, singletonList(fin));
    }

    public Automaton buildUnion(Node leftNode, Node rightNode) {
        String init = "q" + counter++;
        String fin = "q" + counter++;

        List<String> newStates = new ArrayList<>();
        newStates.add(init);
        newStates.addAll(leftNode.automaton.getStates());
        newStates.addAll(rightNode.automaton.getStates());
        newStates.add(fin);

        String concatAlphabet = leftNode.automaton.getAlphabet();
        concatAlphabet += rightNode.automaton.getAlphabet();
        String newAlphabet = Arrays.stream(concatAlphabet.split(""))
                .distinct()
                .collect(Collectors.joining());

        List<Rule> newRules = new ArrayList<>();
        newRules.add(new Rule(init, 'ε', Arrays.asList(leftNode.automaton.getInitialState(), rightNode.automaton.getInitialState())));
        newRules.addAll(leftNode.automaton.getRules());
        newRules.addAll(rightNode.automaton.getRules());
        newRules.add(new Rule(leftNode.automaton.getFinalStates().get(0), 'ε', singletonList(fin)));
        newRules.add(new Rule(rightNode.automaton.getFinalStates().get(0), 'ε', singletonList(fin)));

        return new Automaton(newStates, newAlphabet, newRules, init, singletonList(fin));
    }

    public Automaton buildConcatenation(Node leftNode, Node rightNode) {
        List<String> newStates = new ArrayList<>(leftNode.automaton.getStates());
        newStates.addAll(rightNode.automaton.getStates());

        String concatAlphabet = leftNode.automaton.getAlphabet();
        concatAlphabet += rightNode.automaton.getAlphabet();
        String newAlphabet = Arrays.stream(concatAlphabet.split(""))
                .distinct()
                .collect(Collectors.joining());

        List<Rule> newRules = new ArrayList<>(leftNode.automaton.getRules());
        newRules.add(new Rule(leftNode.automaton.getFinalStates().get(0), 'ε', singletonList(rightNode.automaton.getInitialState())));
        newRules.addAll(rightNode.automaton.getRules());

        return new Automaton(newStates, newAlphabet, newRules, leftNode.automaton.getInitialState(), singletonList(rightNode.automaton.getFinalStates().get(0)));
    }
}
