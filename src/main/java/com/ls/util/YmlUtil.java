package com.ls.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by keke on 2017/10/24.
 */
public class YmlUtil {
    public static HashMap ymlMap = new HashMap();
    private static Logger logger = LoggerFactory.getLogger(YmlUtil.class);

    public static String getYmlValueByKey(String file,String key) {
        load(file);
        return JsonUtil.getStringValByJsonPath(key, JsonUtil.mapToJsonStr(ymlMap));
    }

    public static void load(String fileName) {
        //初始化Yaml解析器public class DBUtil {

        Yaml yaml = new Yaml();
//        File f=new File( System.getProperty("user.dir")+filePath);
        URL url = YmlUtil.class.getClassLoader().getResource(fileName);
//        logger.info(url.toString());
        try {
            //获取test.yaml文件中的配置数据，然后转换为obj，
            Object obj = yaml.load(new FileInputStream(url.getFile()));
            //也可以将值转换为Map
            ymlMap =(HashMap)yaml.load(new FileInputStream(url.getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
