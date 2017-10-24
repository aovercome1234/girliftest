package com.ls.http;

import com.ls.config.GetConfigs;
import com.ls.util.UrlUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiClient {
	private static int httpCode = -1;
	private static HttpPost post;
	private static HttpGet get;
	private static HttpResponse response;
	private static HttpEntity entity;
	static String result="";
	public static HashMap<String, String> cookieMap = new HashMap<String, String>();
	public static String cookie = "";
	private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

	private final static ThreadLocal<CloseableHttpClient> CLIENT_CACHE = new InheritableThreadLocal<CloseableHttpClient>() {
		@Override
		protected CloseableHttpClient initialValue() {

			SSLContext sslContext = null;
			try {
				sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HttpConstants.Connection_Timeout)
					.setConnectTimeout(HttpConstants.Connection_Timeout)
					.setConnectionRequestTimeout(HttpConstants.Connection_Timeout).build();

			CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig)
					.setSSLSocketFactory(sslsf).build();

//			ConnectionConfig connectionConfig = ConnectionConfig.custom()
//					.setBufferSize(4128)
//					.build();
//			CloseableHttpClient client = HttpClients.custom()
//					.setDefaultConnectionConfig(connectionConfig)
//					.build();

			return client;

		}
	};

	private static Header JSON_TYPE_HEADER = new BasicHeader(HttpConstants.Content_Type, HttpConstants.JSON_MEDIA_TYPE);

	public static void closeConnection(){
		try {
			CLIENT_CACHE.get().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getHttpCode() {
		return httpCode;
	}

	public static String Get(String url){

		get = new HttpGet(UrlUtil.getTotalUrl(url, null));
		get.setHeader(JSON_TYPE_HEADER);

		try {
			response = CLIENT_CACHE.get().execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Url={}", UrlUtil.getTotalUrl(url, null));
		logger.info("result={}", result);
		return result;
	}

	public static String Get(String url,List<BasicNameValuePair> params){

		logger.info("Url={}", UrlUtil.getTotalUrl(url, null));

		get = new HttpGet(UrlUtil.getTotalUrl(url, params));
		get.setHeader(JSON_TYPE_HEADER);

		try {
			response = CLIENT_CACHE.get().execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Url={}", UrlUtil.getTotalUrl(url, params));
		logger.info("result={}", result);
		return result;
	}

	public static String Post(String url, String httpbody) {

		post = new HttpPost( UrlUtil.getTotalUrl(url, null));
		post.setHeader(JSON_TYPE_HEADER);

		StringEntity s;
		s = new StringEntity(httpbody,"utf-8");
		try {
			System.out.println("================"+s.getContent().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.setContentType("application/json");
		post.setEntity(s);
		try {
			response = CLIENT_CACHE.get().execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Url={}", UrlUtil.getTotalUrl(url, null));
		logger.info("params={}", httpbody);
		logger.info("result={}", result);
		return result;
	}

	public static String Post(String url, List<NameValuePair> params){
		post = new HttpPost(UrlUtil.getTotalUrl(url, null));
		try{
			post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			response = CLIENT_CACHE.get().execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Url={}", UrlUtil.getTotalUrl(url, null));
		logger.info("params={}", params);
		logger.info("result={}", result);
		return result;
	}
}
