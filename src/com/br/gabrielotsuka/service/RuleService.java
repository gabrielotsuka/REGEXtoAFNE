package com.br.gabrielotsuka.service;

import com.br.gabrielotsuka.data.Rule;
import com.br.gabrielotsuka.data.RuleDTO;
import com.br.gabrielotsuka.repository.RuleRepository;

import java.util.ArrayList;
import java.util.List;

public class RuleService {

    RuleRepository ruleRepository = new RuleRepository();

    public List<Rule> getApplicableRule(List<Rule> rules, String currentState, char currentSymbol) throws Exception {

        Rule afndRule =  rules.stream()
                .filter(rule -> isRuleApplicable(rule, currentState, currentSymbol))
                .findFirst()
                .orElseThrow(() -> new Exception("Regra não encontrada"));

        List<Rule> afndRules = new ArrayList<>();
        afndRules.add(getApplicableRuleEmpty(rules, currentState));
        afndRules.add(afndRule);

        return afndRules;
    }

    public Rule getApplicableRuleEmpty(List<Rule> rules, String currentState) throws Exception {
        return rules.stream()
                .filter(rule -> isRuleApplicable(rule, currentState, 'ε'))
                .findFirst()
                .orElseThrow(() -> new Exception("Regra não encontrada"));
    }

    private boolean isRuleApplicable(Rule rule, String currentState, char currentSymbol) {
        return rule.getSourceState().equals(currentState) && rule.getSymbol() == currentSymbol;
    }

    public void addCoveredRule(RuleDTO ruleDTO) {
        ruleRepository.coveredRules.add(ruleDTO);
    }

    public void cleanCoveredRules() {
        ruleRepository.coveredRules = new ArrayList<>();
    }
}
