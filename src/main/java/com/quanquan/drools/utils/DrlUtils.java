package com.quanquan.drools.utils;

import com.quanquan.drools.model.Rule;

import java.util.List;

public class DrlUtils {
    private final static String tab = "\t";
    private final static String newLine = "\\n";
    private final static String mark = "\"";

    /**
     * @param ruleName 规则名称
     * @param obj 规则对象
     * @param modelList 参数集合
     * @param result 结果对象
     * @return
     */
    public static String toRrl (String ruleName, Object obj, List<Model> modelList, Result result) {
        StringBuffer sb = new StringBuffer();
        //package
        sb.append("package rules" + newLine + newLine);
        //import
        sb.append("import "  + Rule.class.getName() + newLine);
        sb.append("import "  + obj.getClass().getName() + newLine);
        sb.append(newLine);
        //ruleName
        sb.append("rule " + mark + ruleName + mark + newLine);
        //some description
        sb.append(tab + "no-loop true" + newLine);
        sb.append(tab + "lock-on-active true" + newLine);
        sb.append(tab + "salience 1" + newLine);
//        //字段名集合
//        List<String> fields = new ArrayList<String>();
//        for (int i = 0; i < obj.getClass().getDeclaredFields().length; i++) {
//            fields.add(obj.getClass().getDeclaredFields()[i].getName());
//        }
//        System.out.println(JSONObject.toJSON(fields));
        //when
        sb.append(tab + "when" + newLine);
        //content
        sb.append(tab + tab + "$s :" + obj.getClass().getSimpleName() + "(");
        if (modelList.size() < 2) {
//            sb.append( modelList.get(0).getField() + modelList.get(0).getSearchOperator() + addMarks(modelList.get(0).getValue()) + ")" + newLine);
            sb.append(addMarks(modelList.get(0)) + newLine);
        } else {
            for (int j = 0; j < modelList.size(); j++) {
                if (j == 0) {
                    sb.append(addMarks(modelList.get(0)));
                } else {
                    sb.append(" && " + addMarks(modelList.get(j)));
                }
            }
        }
        sb.append(")" + newLine);
        //then
        sb.append(tab + "then" + newLine);
        sb.append(tab + tab + "$s." + "set" + result.getField().substring(0,1).toUpperCase()+ result.getField().substring(1) + "(" + result.getPass() + ");" + newLine);
        sb.append(tab + tab + "update($s);" + newLine);
        //end
        sb.append("end");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String addMarks (Model model) {

        if ("String".equals(model.getFieldType())) {
            String equal = "=";
            return model.getField() + (model.getSearchOperator().equals(equal) ? "==" : model.getSearchOperator()) + "\\'" + model.getValue() + "\\'";
        }
        return model.getField() + model.getSearchOperator() + model.getValue();
    }
}
