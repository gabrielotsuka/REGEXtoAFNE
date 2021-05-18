package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.data.Node;
import com.br.gabrielotsuka.repository.RegexRepository;

import java.util.Arrays;

public class ConverterService {

    public AutomatonService automatonService = new AutomatonService();
    RegexRepository regexRepository;

    public ConverterService(RegexRepository regexRepository) {
        this.regexRepository = regexRepository;
    }

    public Automaton convert(String infixRegex) throws Exception {
        String postfixRegex = SuffixRegexService.infixToPostfix(infixRegex);
        Node expressionTree = ExpressionTreeService.buildExpressionTree(postfixRegex);
        regexRepository.setRegularExpressions(Arrays.asList(infixRegex, postfixRegex));
        return processNodeAutomaton(expressionTree).automaton;
    }

    private Node processNodeAutomaton(Node node) {
        if (node.right == null && node.left == null) {
            if (node.value == '_')
                node.automaton = automatonService.buildEpsilonLeaf();
            else
                node.automaton = automatonService.buildLeaf(node.value);
            return node;
        } else {
            if (node.value == '+') {
                node.automaton = automatonService.buildUnion(processNodeAutomaton(node.left), processNodeAutomaton(node.right));
                return node;
            } else if (node.value == '*') {
                node.automaton = automatonService.buildClosure(processNodeAutomaton(node.left));
                return node;
            } else if (node.value == '.') {
                node.automaton = automatonService.buildConcatenation(processNodeAutomaton(node.right), processNodeAutomaton(node.left));
                return node;
            }
        }
        return node;
    }
}
