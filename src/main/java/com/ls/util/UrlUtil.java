package com.ls.util;

import com.ls.config.GetConfigs;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class UrlUtil {

	public static String BASE_URL = GetConfigs.SERVICE_BASEURL;
	public static String TOTAL_URL;

	public static String getTotalUrl(String endpoint, List<BasicNameValuePair> params) {

		if (params == null) {
			TOTAL_URL = BASE_URL.trim() + endpoint;
		} else {
			String param = URLEncodedUtils.format(params, "UTF-8");
			System.out.println("param:------"+param);
			TOTAL_URL = BASE_URL.trim() + endpoint + "?" + param;
		}
		TOTAL_URL = TOTAL_URL.replaceAll("O","0");

		return TOTAL_URL;
	}
}
