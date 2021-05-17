package com.br.gabrielotsuka;

import com.br.gabrielotsuka.service.ConverterService;

public class Main {

    public static void main(String[] args) throws Exception {

        ConverterService converterService = new ConverterService();
        System.out.println(converterService.convert("a++ASDFa m"));
    }
}
