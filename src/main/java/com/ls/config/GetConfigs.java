package com.ls.config;

import com.ls.util.YmlUtil;

/**
 * Created by keke on 2017/10/24.
 */
public class GetConfigs {
    public static String ENV = YmlUtil.getYmlValueByKey("config.yml", "env");
    public static String FILENAME = "";
    public static String SERVICE_BASEURL = "";
    public static String DB_DRIVER = "";
    public static String DB_URL = "";
    public static String DB_USERNAME = "";
    public static String DB_PASSWORD = "";
    
    static{
        FILENAME = "config_"+ENV+".yml";
        SERVICE_BASEURL = YmlUtil.getYmlValueByKey(FILENAME, "service.baseurl");
        DB_DRIVER = YmlUtil.getYmlValueByKey(FILENAME, "db.driver");
        DB_URL = YmlUtil.getYmlValueByKey(FILENAME, "db.url");
        DB_USERNAME = YmlUtil.getYmlValueByKey(FILENAME, "db.username");
        DB_PASSWORD = YmlUtil.getYmlValueByKey(FILENAME, "db.password");
        
    }

}
