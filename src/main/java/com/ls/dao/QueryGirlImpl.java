package com.ls.dao;

import com.ls.entity.Girl;
import com.ls.util.DBUtil;

import java.util.List;

/**
 * Created by keke on 2017/10/24.
 */
public class QueryGirlImpl implements QueryGirl {

    private DBUtil dbUtil = new DBUtil();

    public List<Girl> getAllGirls() {
        List<Girl> girls = dbUtil.getAll(QueryGirlSQL.getAllGirlsSQL, Girl.class);
        return girls;
    }

    public Girl findGirlByID(int id) {
        Object[] params = {id};
        Girl girl = (Girl) dbUtil.findBy(QueryGirlSQL.findGirlByIDSQL, Girl.class);
        return girl;
    }

    public List<Girl> findGirlsByAge(int age) {
        Object[] params = {age};
        List<Girl> girls = (List<Girl>) dbUtil.findBy(QueryGirlSQL.findGirlsByAgeSQL, Girl.class, params);
        return girls;
    }

}
