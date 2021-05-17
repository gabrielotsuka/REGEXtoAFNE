package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Node;

import java.util.EmptyStackException;
import java.util.Stack;

public class ExpressionTreeService {

    public static Node buildExpressionTree(String postfix) throws Exception {
        Stack<Node> nodeStack = new Stack<>();
        Node node, node1, node2;
        try {
            for (int i = 0; i < postfix.length(); i++) {
                char token = postfix.charAt(i);

                if (!isOperator(token)) {
                    node = new Node(token);
                    nodeStack.push(node);
                } else {
                    node = new Node(token);

                    node1 = nodeStack.pop();
                    node.left = node1;

                    if (token != '*') {
                        node2 = nodeStack.pop();
                        node.right = node2;
                    }
                    nodeStack.push(node);
                }
            }
            Node root = nodeStack.pop();
            if (!nodeStack.isEmpty()) {
                throw new Exception("Expressão com falta de operadores");
            }
            return root;
        } catch (EmptyStackException exception) {
            throw new Exception("Expressão regular inválida");
        }
    }

    static boolean isOperator(char symbol) {
        return symbol == '*' || symbol == '+' || symbol == '.';
    }
}

