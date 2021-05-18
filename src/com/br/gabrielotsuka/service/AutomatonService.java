package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.data.Node;
import com.br.gabrielotsuka.data.Rule;
import com.br.gabrielotsuka.data.RuleDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class AutomatonService {

    int counter = 0;
    private boolean sequenceValidate = false;
    private final RuleService ruleService = new RuleService();


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
        List<Character> sequenceRequest = new ArrayList<>();

        for (char item: sequence.toCharArray()) {
            sequenceRequest.add(item);
        }

        if(sequenceRequest.isEmpty()){
            List<String> initialState = new ArrayList<>();
            initialState.add(automaton.getInitialState());
            sequenceValidate = isAcceptable(initialState, automaton.getFinalStates());
            if(!sequenceValidate){
                Rule applicableRule = ruleService.getApplicableRuleEmpty(automaton.getRules(), automaton.getInitialState());
                sequenceValidate = isAcceptable(applicableRule.getTargetStates(), automaton.getFinalStates());
            }

        }else{
            processSequence(sequenceRequest,
                    automaton.getInitialState(),
                    automaton.getRules(),
                    automaton.getFinalStates());
        }
    }

    public List<RuleDTO> getStackSequence(){
        return ruleService.ruleRepository.coveredRules;
    }

    public Boolean getSequenceValidate(){
        return sequenceValidate;
    }

    private void processSequence(List<Character> sequence,
                                 String currentState,
                                 List<Rule> rules,
                                 List<String> finalStates) throws Exception {
        int aux = 0;

        for (char currentSymbol : sequence) {
            aux = aux + 1;

            List<Rule> applicableRule = ruleService.getApplicableRule(rules, currentState, currentSymbol);

            List<String> targetStates = new ArrayList<>();
            for (Rule afndRule: applicableRule) {
                targetStates.addAll(afndRule.getTargetStates());
            }

            ruleService.addCoveredRule(dtoRuleDTO(applicableRule, currentSymbol));

            if(targetStates.size() > 1) {
                for(Rule afndRule: applicableRule){
                    for(String targetState : afndRule.getTargetStates()){
                        if(afndRule.getSymbol() == 'ε'){
                            if(sequence.size() != 0)
                                processSequence(
                                        sequence,
                                        targetState,
                                        rules,
                                        finalStates); //recursão
                        }else{
                            if(sequence.subList(aux, sequence.size()).size() != 0)
                                processSequence(
                                        sequence.subList(aux, sequence.size()),
                                        targetState,
                                        rules,
                                        finalStates); //recursão
                        }
                    }
                }
            }else{
                if(targetStates.size() == 1) {
                    currentState = targetStates.get(0);
                }else {
                    break;
                }
            }
        }
        isAcceptableState(finalStates);
        System.out.println("------------------------------");
        System.out.println(sequenceValidate);
        System.out.println("------------------------------");
    }

    private void isAcceptableState(List<String> finalStates){
        if(!sequenceValidate)
            sequenceValidate = isAcceptable(getStackSequence().get(getStackSequence().size()-1).getTargetStates(), finalStates);
    }

    private boolean isAcceptable(List<String> state, List<String> acceptableStates) {
        for (String item: state) {
            return acceptableStates.contains(item);
        }
        return false;
    }

    private RuleDTO dtoRuleDTO(List<Rule> applicableRules, char currentSymbol){
        boolean emptyStateRule = false;
        List<String> targetState = new ArrayList<>();
        String sourceState = null;

        for (Rule afndRule : applicableRules) {
            if(afndRule.getSymbol() == 'ε' && !afndRule.getTargetStates().isEmpty()){
                emptyStateRule = true;
            }
            targetState.addAll(afndRule.getTargetStates());
            sourceState = afndRule.getSourceState();
        }
        RuleDTO applicableRule = new RuleDTO(sourceState, currentSymbol, targetState, emptyStateRule);
        System.out.println("-------");
        System.out.println(applicableRule.getSourceState());
        System.out.println(applicableRule.getSymbol());
        System.out.println(applicableRule.getTargetStates());
        System.out.println(applicableRule.getEmptyStateRule());
        System.out.println("-------");
        return applicableRule;
    }
}
