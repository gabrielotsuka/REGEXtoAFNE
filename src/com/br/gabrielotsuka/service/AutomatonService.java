package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.data.EFechoIndex;
import com.br.gabrielotsuka.data.Node;
import com.br.gabrielotsuka.data.Rule;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.*;

public class AutomatonService {

    int counter = 0;
    Automaton automaton;
    Stack<EFechoIndex> eFechoIndices = new Stack<>();

    public Automaton buildEpsilonLeaf() {
        String state = "q" + counter++;
        return new Automaton(
                singletonList(state),
                "",
                emptyList(),
                state,
                singletonList(state)
        );
    }

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
        newRules.add(new Rule(init, '_', Arrays.asList(fin, oldAutomaton.getInitialState())));
        newRules.addAll(oldAutomaton.getRules());
        newRules.add(new Rule(oldAutomaton.getFinalStates().get(0), '_', Arrays.asList(fin, oldAutomaton.getInitialState())));

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
        newRules.add(new Rule(init, '_', Arrays.asList(leftNode.automaton.getInitialState(), rightNode.automaton.getInitialState())));
        newRules.addAll(leftNode.automaton.getRules());
        newRules.addAll(rightNode.automaton.getRules());
        newRules.add(new Rule(leftNode.automaton.getFinalStates().get(0), '_', singletonList(fin)));
        newRules.add(new Rule(rightNode.automaton.getFinalStates().get(0), '_', singletonList(fin)));

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
        newRules.add(new Rule(leftNode.automaton.getFinalStates().get(0), '_', singletonList(rightNode.automaton.getInitialState())));
        newRules.addAll(rightNode.automaton.getRules());

        return new Automaton(newStates, newAlphabet, newRules, leftNode.automaton.getInitialState(), singletonList(rightNode.automaton.getFinalStates().get(0)));
    }

    public void belongsToLanguage(String sequence, Automaton automaton) throws Exception {
        this.automaton = automaton;
        Set<String> firstEFecho = calculateEfecho(automaton.getInitialState(), emptySet());
        for (String state: firstEFecho) {
            eFechoIndices.add(new EFechoIndex(state, 0));
        }
        processSequence(sequence);

    }

    private Set<String> calculateEfecho(String state, Set<Object> eFechoRequest) {
        Set<String> eFechoResponse = new HashSet<>(emptySet());
        eFechoResponse.add(state);
        for (Rule rule: automaton.getRules()) {
            if (rule.getSourceState().equals(state) && rule.getSymbol() == '_') {
                for (String target: rule.getTargetStates()) {
                    if (!eFechoRequest.contains(target)) {
                        eFechoResponse.addAll(calculateEfecho(target, eFechoRequest));
                    }
                }
            }
        }
        return eFechoResponse;
    }

    private void processSequence(String sequence) throws Exception {
        if (eFechoIndices.isEmpty()){
            throw new Exception("n pertence");
        }

        EFechoIndex eFechoIndex = eFechoIndices.pop();

        Set<String> eFecho = new HashSet<>(emptySet());
        if (eFechoIndex.getPosition() == sequence.length()) {
            if (automaton.getFinalStates().contains(eFechoIndex.getState())) {
                throw new Exception("pertence");
            } else {
                processSequence(sequence);
            }
        } else if (eFechoIndex.getPosition() != sequence.length()) {
            char symbol = sequence.charAt(eFechoIndex.getPosition());
            for (Rule rule: automaton.getRules()) {
                if (rule.getSourceState().equals(eFechoIndex.getState()) && rule.getSymbol() == symbol) {
                    for (String targetState : rule.getTargetStates()) {
                        eFecho.addAll(calculateEfecho(targetState, emptySet()));
                    }
                    for (String eFechoState: eFecho) {
                        eFechoIndices.add(new EFechoIndex(eFechoState, eFechoIndex.getPosition()+1));
                    }
                }
            }
            processSequence(sequence);
        }


    }
}
