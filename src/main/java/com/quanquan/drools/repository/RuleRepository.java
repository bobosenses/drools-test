package com.quanquan.drools.repository;

import com.quanquan.drools.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    Rule findByRuleKey(String ruleKey);
}