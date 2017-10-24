package com.ls.apiservice;

import com.ls.entity.Girl;
import com.ls.http.ApiClient;
import com.ls.util.CGLIBProxyFactory;
import com.ls.util.JsonUtil;
import com.ls.util.ReflectUtil;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by keke on 2017/10/24.
 */
public class GirlService {

    private ApiClient apiClient = new ApiClient();
    private ReflectUtil reflectUtil = new ReflectUtil();
    private static final Logger logger = LoggerFactory.getLogger(GirlService.class);
    private String jsonResult = "";
    static CGLIBProxyFactory proxyFactory = CGLIBProxyFactory.getInstance();

    /** 查询所有女孩  */
    public String getAllGirls(){
        jsonResult = ApiClient.Get(GirlServiceURL.GIRLS_URL);
        logger.info("getAllGirls jsonResult={}", jsonResult);
        return jsonResult;
    }

    /** 新增一个女孩 */
    public String addOneGirl(String cupSize, int age){
//        LinkedHashMap<String, Object> map = proxyFactory.map;
        List<NameValuePair> params = proxyFactory.params;
        jsonResult = ApiClient.Post(GirlServiceURL.GIRLS_URL, params);
        logger.info("getAllGirls jsonResult={}", jsonResult);
        return jsonResult;
    }


    public static void main(String[] args) throws Exception {
//        GirlService girlService = new GirlService();
//        System.out.println(girlService.addOneGirl("C", 22));

        GirlService girlService = (GirlService)proxyFactory.getProxyClass(GirlService.class);
        girlService.addOneGirl("c", 22);

    }

}
