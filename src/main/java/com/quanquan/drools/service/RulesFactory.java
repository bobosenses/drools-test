package com.quanquan.drools.service;

import com.quanquan.drools.model.Rule;
import com.quanquan.drools.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class RulesFactory {

    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private ReloadDroolsRulesService reloadService;

    public final static List<String> rules = new ArrayList<String>();

    @PostConstruct
    private void init() {
        List<Rule> ruleList = reloadService.loadRules();
        for (Rule rule : ruleList) {
            rules.add(rule.getContent());
        }
    }


}

