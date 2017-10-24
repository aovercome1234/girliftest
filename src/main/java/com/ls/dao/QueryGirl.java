package com.ls.dao;

import com.ls.entity.Girl;
import java.util.List;

/**
 * Created by keke on 2017/10/24.
 */
public interface QueryGirl {
    public List<Girl> getAllGirls();

    public Girl findGirlByID(int id);

    public List<Girl> findGirlsByAge(int age);
}
