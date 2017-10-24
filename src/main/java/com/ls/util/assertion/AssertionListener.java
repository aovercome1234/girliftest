package com.ls.util.assertion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liushu on 2016/10/26.
 */
public class AssertionListener extends TestListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AssertionListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("*****************  {}测试类开始执行测试用例：{}  ***************** ",result.getInstance().getClass().getName(), result.getMethod().getMethodName());
        System.out.println(result.getStatus());
        Assertion.flag = true;
        Assertion.errors.clear();
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        logger.info("*****************  {}测试类 {}测试用例Fail：  ***************** ",tr.getInstance().getClass().getName(), tr.getMethod().getMethodName());
        System.out.println(tr.getStatus());
        this.handleAssertion(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {

        logger.info("*****************  {}测试类 {}测试用例Skipped：  ***************** ",tr.getInstance().getClass().getName(), tr.getMethod().getMethodName());
        System.out.println(tr.getStatus());
        this.handleAssertion(tr);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        logger.info("*****************  {}测试类 {}测试用例Success：  ***************** ",tr.getInstance().getClass().getName(), tr.getMethod().getMethodName());
        System.out.println(tr.getStatus());
        this.handleAssertion(tr);
    }

    private int index = 0;

    private void handleAssertion(ITestResult tr){
        System.out.println("Assertion.flag"+Assertion.flag);
        if(!Assertion.flag){
            System.out.println("!Assertion.flag"+!Assertion.flag);
            Throwable throwable = tr.getThrowable();
            if(throwable==null){
                throwable = new Throwable();
            }
            StackTraceElement[] traces = throwable.getStackTrace();
            StackTraceElement[] alltrace = new StackTraceElement[0];
            for (Error e : Assertion.errors) {
                StackTraceElement[] errorTraces = e.getStackTrace();
                StackTraceElement[] et = this.getKeyStackTrace(tr, errorTraces);
                StackTraceElement[] message = new StackTraceElement[]{new StackTraceElement("message : "+e.getMessage()+" in method : ", tr.getMethod().getMethodName(), tr.getTestClass().getRealClass().getSimpleName(), index)};
                index = 0;
                alltrace = this.merge(alltrace, message);
                alltrace = this.merge(alltrace, et);
            }
            if(traces!=null){
                traces = this.getKeyStackTrace(tr, traces);
                alltrace = this.merge(alltrace, traces);
            }
            throwable.setStackTrace(alltrace);
            tr.setThrowable(throwable);
            Assertion.flag = true;
            Assertion.errors.clear();
            tr.setStatus(ITestResult.FAILURE);
        }
    }

    private StackTraceElement[] getKeyStackTrace(ITestResult tr, StackTraceElement[] stackTraceElements){
        List<StackTraceElement> ets = new ArrayList<StackTraceElement>();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if(stackTraceElement.getClassName().equals(tr.getTestClass().getName())){
                ets.add(stackTraceElement);
                index = stackTraceElement.getLineNumber();
            }
        }
        StackTraceElement[] et = new StackTraceElement[ets.size()];
        for (int i = 0; i < et.length; i++) {
            et[i] = ets.get(i);
        }
        return et;
    }

    private StackTraceElement[] merge(StackTraceElement[] traces1, StackTraceElement[] traces2){
        StackTraceElement[] ste = new StackTraceElement[traces1.length+traces2.length];
        for (int i = 0; i < traces1.length; i++) {
            ste[i] = traces1[i];
        }
        for (int i = 0; i < traces2.length; i++) {
            ste[traces1.length+i] = traces2[i];
        }
        return ste;
    }
}
