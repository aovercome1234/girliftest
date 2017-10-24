package com.ls.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keke on 2017/10/24.
 */
public class CGLIBProxyFactory implements MethodInterceptor {

    private Object target;

    private CGLIBProxyFactory(){}

    public static CGLIBProxyFactory getInstance(){
        return new CGLIBProxyFactory();
    }
//    public LinkedHashMap<String, Object> map = new LinkedHashMap<>();
    public List<NameValuePair> params = new ArrayList<NameValuePair>();

    public Object getProxyClass(Class clazz) throws Exception{
        target = clazz.newInstance();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object proxy, Method method,
                            Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        // 输出一些方法的调用信息
//        System.out.println("---------------");
//        System.out.println("Method: " + method.toGenericString());
//        for(int i = 0; i < args.length; i++) {
//            System.out.println("args[" + i + "] = " + args[i]);
//        }
//        System.out.println("---------------");

        Parameter[] parameters=method.getParameters();
//        for(int i=0; i< args.length; i++){
//            map.put(parameters[i].getName(), args[i]);
//        }

        for(int i=0; i< args.length; i++){
            params.add(new BasicNameValuePair(parameters[i].getName(), args[i]+""));
        }

        // 调用方法
        return method.invoke(target, args);

    }
}
