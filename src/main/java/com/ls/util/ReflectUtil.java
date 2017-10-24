package com.ls.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;

/**
 * Created by keke on 2017/10/24.
 */
public class ReflectUtil {
    /**
     * 得到该类的属性名称并set值，
     * 想实现在程序运行时把SERVICE_URL获取到，并解析为service.url,然后利用YmlUtil去读取对应的值，并把这个值set给SERVICE_URL，但是能力有限~~
     */
    public static String getFieldName(Object obj){
        String result = "";
        Class clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        String fieldName = "";
        for (Field field:fields) {
            System.out.println(field.getName());
            field.setAccessible(true);
            try {
                System.out.println("======"+YmlUtil.getYmlValueByKey("config_prod.yml", "service.baseurl"));
                field.set(obj, YmlUtil.getYmlValueByKey("config_prod.yml", "service.baseurl"));
                result = (String) field.get(obj);
                System.out.println(result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String[] getClassNameAndCurrentMethodName(){
        Throwable t=new Throwable();
        StackTraceElement[] st=t.getStackTrace();

        String[] strs = new String[2];
        System.out.println("执行的类名："+st[2].getClassName()+";方法名："+st[2].getMethodName());
        strs[0] = st[2].getClassName();
        strs[1] = st[2].getMethodName();
        return strs;
    }

    /**获取当前运行方法名的方法的方法参数*/
    public LinkedHashMap<String, Object> getFieldName(){
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        String[] strs = getClassNameAndCurrentMethodName();
        Class clz = null;
        try {
            clz = Class.forName(strs[0]);
            System.out.println(clz.getName());
            Method[] methods = clz.getMethods();
            for(Method method: methods){
                if(method.getName().equalsIgnoreCase(strs[1])){
                    //获取方法的所有参数
                    Parameter[] parameters=method.getParameters();
                    //获取第一个参数的名字
//                    System.out.println(parameter[0].getName());
                    for(Parameter parameter: parameters){
                        map.put(parameter.getName(), "");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

}
