package com.br.gabrielotsuka.repository;

import java.util.Collections;
import java.util.List;

public class RegexRepository {
    // 0 - infix
    // 1 - postfix
    List<String> regularExpressions = Collections.emptyList();

    public void setRegularExpressions(List<String> regularExpressions) {
        this.regularExpressions = regularExpressions;
    }

    public List<String> getRegularExpressions() {
        return regularExpressions;
    }
}
