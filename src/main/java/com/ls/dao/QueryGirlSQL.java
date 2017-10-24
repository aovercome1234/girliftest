package com.ls.dao;

/**
 * Created by keke on 2017/10/24.
 */
public class QueryGirlSQL {

    public static final String getAllGirlsSQL = "select * from girl;";

    public static final String findGirlByIDSQL = "select * from girl where id = ?;";

    public static final String findGirlsByAgeSQL = "select * from girl where age = ?;";


}
