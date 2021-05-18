package com.br.gabrielotsuka;

import com.br.gabrielotsuka.repository.RegexRepository;
import com.br.gabrielotsuka.view.InitialView;

public class Main {

    public static void main(String[] args) {
        new InitialView(new RegexRepository());
    }
}
