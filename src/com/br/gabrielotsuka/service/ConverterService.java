package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.data.Node;

public class ConverterService {

    public AutomatonService automatonService = new AutomatonService();

    public Automaton convert(String infixRegex) {
        String postfixRegex = SuffixRegexService.infixToPostfix(infixRegex);
        Node expressionTree = ExpressionTreeService.buildExpressionTree(postfixRegex);
        expressionTree = processNodeAutomaton(expressionTree);

        return new Automaton();
    }

    private Node processNodeAutomaton(Node node) {
        if (node.right == null && node.left == null) {
            node.automaton = automatonService.buildLeaf(node.value);
            return node;
        } else {
//            if (node.value == '+') {
//                node.automaton = automatonService.buildUnion(processNodeAutomaton(node.left), processNodeAutomaton(node.right));
//                System.out.println(node.automaton.toString());
//                return node;
//            }
//            else if (node.value == '.') {
//                node.automaton = automatonService.buildConcatenation(processNodeAutomaton(node.left), processNodeAutomaton(node.right));
//                return node;
//            } else
            if (node.value == '*') {
//                node.automaton = automatonService.buildClosure(processNodeAutomaton(node.left));
                return node;
            }
        }
        return null;
    }
}
