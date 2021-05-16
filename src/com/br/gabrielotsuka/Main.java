package com.br.gabrielotsuka;

import com.br.gabrielotsuka.service.ConverterService;

public class Main {

    public static void main(String[] args) {

        ConverterService converterService = new ConverterService();
        converterService.convert("a*");
    }
}
