package com.quanquan.drools.controller;

import com.quanquan.drools.model.OrderLine;
import com.quanquan.drools.model.Rule;
import com.quanquan.drools.service.ReloadDroolsRulesService;
import com.quanquan.drools.utils.DrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/my")
@Controller
public class MyTestController {

    @Resource
    private ReloadDroolsRulesService rules;

    public String addRules (OrderLine orderLine) {
        if (orderLine == null) {
            return "对象为空";
        }
        Rule rule = new Rule();
        rule.setRuleKey("orderLine_" + generateRandom(4));
        rule.setContent("");
        return "新增成功";
    }

    /**
     * 生成随机数
     * @param num
     * @return
     */
    public static String generateRandom(int num) {
        String chars = "0123456789";
        StringBuffer number=new StringBuffer();
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            number=number.append(chars.charAt(rand));
        }
        return number.toString();
    }

}
