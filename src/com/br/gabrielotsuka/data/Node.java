package com.br.gabrielotsuka.data;

public class Node {
    public char value;
    public Node left;
    public Node right;
    public Automaton automaton;

    public Node(char symbol) {
        value = symbol;
        right = left = null;
    }
}
