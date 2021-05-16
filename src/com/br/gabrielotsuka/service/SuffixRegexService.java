package com.br.gabrielotsuka.service;

import java.util.HashMap;
import java.util.Stack;

public class SuffixRegexService {

    public static String infixToPostfix(String infix) {
        HashMap<Character, Integer> precedence = new HashMap<>();
        precedence.put('*', 3);
        precedence.put('.', 2);
        precedence.put('+', 1);
        precedence.put('(', 0);

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();
        for (int i = 0; i < infix.length(); i++) {
            char token = infix.charAt(i);
            if (token == '(') {
                operatorStack.push(token);
            } else if (token == ')') {
                char topToken = operatorStack.pop();
                while (topToken != '(') {
                    postfix.append(topToken);
                    topToken = operatorStack.pop();
                }
            } else if (precedence.containsKey(token)){
                while ((!operatorStack.isEmpty()) && (precedence.get(operatorStack.peek()) > precedence.get(token)))
                    postfix.append(operatorStack.pop());
                operatorStack.push(token);
            } else {
                postfix.append(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

}
