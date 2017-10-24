package com.ls.apiservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ls.dao.QueryGirl;
import com.ls.dao.QueryGirlImpl;
import com.ls.entity.Girl;
import com.ls.util.CGLIBProxyFactory;
import com.ls.util.assertion.Assertion;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by keke on 2017/10/24.
 */
@Listeners({com.ls.util.assertion.AssertionListener.class})
public class GetAllGirlsTest {

    private CGLIBProxyFactory cglibProxyFactory = null;
    private GirlService girlService = null;
    private QueryGirl queryGirl = null;

    @BeforeClass
    public void beforeClass() throws Exception {
        cglibProxyFactory = CGLIBProxyFactory.getInstance();
        girlService = (GirlService) cglibProxyFactory.getProxyClass(GirlService.class);
        queryGirl = new QueryGirlImpl();
    }

    @Test
    public void testGetAllGirls1(){
        String jsonResult = girlService.getAllGirls();
        List<Girl> girlsFromIf = JSON.parseObject(jsonResult, new TypeReference<List<Girl>>() {});
        List<Girl> girlsFromDB = queryGirl.getAllGirls();

        Assertion.verifyEquals(girlsFromIf.size(), girlsFromDB.size()+1, "判断查询所有女孩接口，结果是否返回正确");
        Assertion.verifyEquals(girlsFromIf.size(), girlsFromDB.size(), "判断查询所有女孩接口，结果是否返回正确");
    }

    @Test
    public void testGetAllGirls2(){
        String jsonResult = girlService.getAllGirls();
        List<Girl> girlsFromIf = JSON.parseObject(jsonResult, new TypeReference<List<Girl>>() {});
        List<Girl> girlsFromDB = queryGirl.getAllGirls();

        Assertion.verifyEquals(girlsFromIf.size(), girlsFromDB.size(), "判断查询所有女孩接口，结果是否返回正确");
    }

    @AfterClass
    public void afterClass(){
        cglibProxyFactory = null;
        girlService = null;
        queryGirl = null;
    }

}
