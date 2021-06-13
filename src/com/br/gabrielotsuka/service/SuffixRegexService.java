package com.br.gabrielotsuka.service;

import java.util.HashMap;
import java.util.Stack;

public class SuffixRegexService {

    public static String infixToPostfix(String infix) throws Exception {
        
        validateParentheses(infix);
        validateEmptyRegex(infix);
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

    private static void validateEmptyRegex(String chain) throws Exception {
        int counter = 0;
        for (int i = 0; i < chain.length(); i++) {
            if (chain.charAt(i) == '#'){
                counter++;
            }
        }
        if (counter > 0 && chain.length() > 1) {
            throw new Exception("# representa conjunto vazio, e não pode ter outros caracteres!");
        }
    }

    private static void validateParentheses(String infix) throws Exception {
        int open, close;
        open = close = 0;
        for (int i = 0; i < infix.length(); i++) {
            if (infix.charAt(i) == '(') open++;
            else if (infix.charAt(i) == ')') close++;
        }
        if (open != close)
            throw new Exception("Parênteses da expressão estão inválidos");
    }

}
