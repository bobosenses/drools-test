package com.quanquan.drools.utils;

import java.util.List;

public class DrlUtils {
    private final static String tab = "\t";
    private final static String newLine = "\\n";
    private final static String mark = "\"";

    /**
     * @param entities 实体集合
     * @param ruleName 规则名称
     * @param obj 规则对象
     * @param modelList 参数集合
     * @param result 结果对象
     * @return
     */
    public static String toRrl (List<Object> entities, String ruleName, Object obj, List<Model> modelList, Result result) {
        StringBuffer sb = new StringBuffer();
        //package
        sb.append("package rules" + newLine + newLine);
        //import
        for (Object o : entities) {
            sb.append("import "  + o.getClass().getName() + newLine);
        }
        sb.append(newLine);
        //ruleName
        sb.append("rule " + mark + ruleName + mark);
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
        if (modelList.size() > 1) {
            sb.append(tab + tab + "$s :" + obj.getClass().getSimpleName() + "(" + modelList.get(0).getField() + modelList.get(0).getSearchOperator() + addMarks(modelList.get(0).getValue()) + ")" + newLine);
        } else {
            for (int j = 0; j < modelList.size(); j++) {
                if (j == 0) {
                    sb.append(tab + tab + "$s :" + obj.getClass().getSimpleName() + "(" + modelList.get(0).getField() + modelList.get(0).getSearchOperator() + addMarks(modelList.get(0).getValue()));
                } else {
                    sb.append("&&" + modelList.get(j).getField() + modelList.get(j).getSearchOperator() + addMarks(modelList.get(j).getValue()));
                }
            }
            sb.append(")" + newLine);
        }
        //then
        sb.append(tab + "then" + newLine);
        sb.append(tab + tab + "$s." + "set" + result.getField().substring(0,1).toUpperCase()+ result.getField().substring(1) + "(" + result.getPass() + ");" + newLine);
        sb.append(tab + tab + "update($s);" + newLine);
        //end
        sb.append("end");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String addMarks (String text) {
        return "\\'" + text + "\\'";
    }
}
