package com.quanquan.drools;

import com.quanquan.drools.enums.SearchOperator;
import com.quanquan.drools.model.Address;
import com.quanquan.drools.model.Message;
import com.quanquan.drools.model.OrderLine;
import com.quanquan.drools.model.Rule;
import com.quanquan.drools.utils.DrlUtils;
import com.quanquan.drools.utils.Model;
import com.quanquan.drools.utils.Result;
import org.drools.core.factmodel.Fact;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.boot.ansi.AnsiOutput;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class DdLoadTest {

	public static void main(String[] args) {
		//rule,rule2可以放在数据库中，有个唯一code和他们对于，代码要执行规则的时候，根据code从数据库获取出来就OK了，这样自己开发的规则管理系统那边对数据库里的规则进行维护就行了
//		String rule = "package com.neo.drools\r\n";
//		rule += "import Message;\r\n";
//		rule += "rule \"rule1\"\r\n";
//		rule += "\twhen\r\n";
//		rule += "Message( status == 1, myMessage : msg )";
//		rule += "\tthen\r\n";
//		rule += "\t\tSystem.out.println( 1+\":\"+myMessage );\r\n";
//		rule += "end\r\n";
//
//
//		String rule2 = "package com.neo.drools\r\n";
//		rule2 += "import Message;\r\n";
//
//		rule2 += "rule \"rule2\"\r\n";
//		rule2 += "\twhen\r\n";
//		rule2 += "Message( status == 2, myMessage : msg )";
//		rule2 += "\tthen\r\n";
//		rule2 += "\t\tSystem.out.println( 2+\":\"+myMessage );\r\n";
//		rule2 += "end\r\n";

		String rule3 = "package rules\n" +
				"\n" +
				"import com.quanquan.drools.model.OrderLine\n" +
				"import com.quanquan.drools.model.Rule\n" +
				"\n" +
				"rule \"my rule\"\n    no-loop true\n" +
				"    lock-on-active true\n" +
				"    salience 1\n" +
				"    when\n" +
				"        $s :OrderLine(status=='200')\n" +
				"    then\n" +
				"        $s.setNum(123);\n" +
				"        $s.setPass(false);\n" +
				"        $s.setInfo('123');\n" +
				"        update($s);\n" +
				"end";

		StatefulKnowledgeSession kSession = null;
		try {


			KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
			//装入规则，可以装入多个
//			kb.add(ResourceFactory.newByteArrayResource(rule.getBytes("utf-8")), ResourceType.DRL);
//			kb.add(ResourceFactory.newByteArrayResource(rule2.getBytes("utf-8")), ResourceType.DRL);
			kb.add(ResourceFactory.newByteArrayResource(rule3.getBytes("utf-8")), ResourceType.DRL);

			KnowledgeBuilderErrors errors = kb.getErrors();
			for (KnowledgeBuilderError error : errors) {
				System.out.println(error);
			}
			KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
			kBase.addKnowledgePackages(kb.getKnowledgePackages());

			kSession = kBase.newStatefulKnowledgeSession();


//			Message message1 = new Message();
//			message1.setStatus(1);
//			message1.setMsg("hello world!");
//
//			Message message2 = new Message();
//			message2.setStatus(2);
//			message2.setMsg("hi world!");

			OrderLine orderLine = new OrderLine();
			orderLine.setStatus("200");
			System.out.println("before : " + orderLine.toString());
//			kSession.insert(message1);
//			kSession.insert(message2);
			FactHandle fact = kSession.insert(orderLine);
			int count = kSession.fireAllRules();
			System.out.println("after : " + orderLine.toString());
			System.out.println("触发了 " + count + " 条规则！！！！！！！！！！！！");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (kSession != null)
				kSession.dispose();
		}

	}

	@Test
	public void test () {
		String ruleName = "my rule";
		List<Object> list = new ArrayList<Object>();
		OrderLine orderLine = new OrderLine();
		Rule rule = new Rule();
		list.add(orderLine);
		list.add(rule);
		List<Model> modelList = new ArrayList<Model>();
		Model model = new Model();
		model.setField("status");
		model.setSearchOperator(SearchOperator.gt.getSymbol());
		model.setValue("200");
		modelList.add(model);
		Result result = new Result();
		result.setField("pass");
		result.setValue("200");
		System.out.println(DrlUtils.toRrl(list, ruleName, orderLine, modelList, result));
	}
}

