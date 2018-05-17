package com.quanquan.drools.controller;

import com.quanquan.drools.model.Address;
import com.quanquan.drools.model.OrderLine;
import com.quanquan.drools.model.fact.AddressCheckResult;
import com.quanquan.drools.service.ReloadDroolsRulesService;
import org.drools.core.marshalling.impl.ProtobufMessages;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;


@RequestMapping("/test")
@Controller
public class TestController {

    @Resource
    private ReloadDroolsRulesService rules;

    @ResponseBody
    @RequestMapping("/address")
    public void test(){
        Address address = new Address();
        address.setPostcode("994251");
        KieSession kieSession = ReloadDroolsRulesService.kieContainer.newKieSession();

        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if(result.isPostCodeResult()){
            System.out.println("规则校验通过");
        }

    }

    @ResponseBody
    @RequestMapping("/orderLine")
    public String orderLine(String status, Integer num){
        OrderLine orderLine = new OrderLine();
        orderLine.setStatus(status);
        orderLine.setNum(num);
        KieSession kieSession = ReloadDroolsRulesService.kieContainer.newKieSession();

//        AddressCheckResult result = new AddressCheckResult();
        FactHandle fh = kieSession.insert(orderLine);
//        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");
        if (ruleFiredCount > 0 && orderLine.getPass()) {
            System.out.println("规则校验成功！");
        }
//        if(result.isPostCodeResult()){
//            System.out.println("规则校验通过");
//        }
        return "触发了" + ruleFiredCount + "条规则";

    }

    @ResponseBody
    @RequestMapping("/orderLine2")
    public String orderLine2(){
        OrderLine orderLine = new OrderLine();
        orderLine.setStatus("500");
        KieSession kieSession = ReloadDroolsRulesService.kieContainer.newKieSession();

//        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(orderLine);
//        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");
        if (orderLine.getPass()) {
            System.out.println("规则校验成功！");
        }
//        if(result.isPostCodeResult()){
//            System.out.println("规则校验通过");
//        }
        return "";

    }


    /**
     * 从数据加载最新规则
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/reload")
    public String reload() throws IOException {
        rules.reload();
        return "ok";
    }

    /**
     * 生成随机数
     * @param num
     * @return
     */
    public String generateRandom(int num) {
        String chars = "0123456789";
        StringBuffer number=new StringBuffer();
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            number=number.append(chars.charAt(rand));
        }
        return number.toString();
    }
}
