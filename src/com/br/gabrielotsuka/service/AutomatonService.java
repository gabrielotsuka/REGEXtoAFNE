package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Automaton;
import com.br.gabrielotsuka.data.Node;
import com.br.gabrielotsuka.data.Rule;

import java.util.Arrays;

import static java.util.Collections.singletonList;

public class AutomatonService {

    public Automaton buildLeaf(char value) {
        return new Automaton(
                Arrays.asList("q0", "q1"),
                String.valueOf(value),
                singletonList(new Rule("q0", value, singletonList("q1"))),
                "q0",
                singletonList("q1")
        );
    }

//    public Automaton buildUnion(Node A1, Node A2) {
//
//
//    }

//    public Automaton buildClosure(Node processNodeAutomaton) {
//
//    }
}
