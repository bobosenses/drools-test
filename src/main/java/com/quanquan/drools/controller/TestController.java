package com.quanquan.drools.controller;

import com.quanquan.drools.model.Address;
import com.quanquan.drools.model.fact.AddressCheckResult;
import com.quanquan.drools.service.ReloadDroolsRulesService;
import org.kie.api.runtime.KieSession;
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

    public static void main(String[] args) {
        String abc = "package plausibcheck.adress\n" +
                "\n" +
                "import Address;\n" +
                "import AddressCheckResult;\n" +
                "\n" +
                "rule \"Postcode should be filled with exactly 5 numbers\"\n" +
                "    when\n" +
                "        address : Address(postcode != null, postcode matches \"([0-9]{5})\")\n" +
                "        checkResult : AddressCheckResult();\n" +
                "    then\n" +
                "        checkResult.setPostCodeResult(true);\n" +
                "\t\tSystem.out.println(\"规则中打印日志：校验通过!\");\n" +
                "end";
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
