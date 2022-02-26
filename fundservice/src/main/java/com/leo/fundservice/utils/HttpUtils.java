package com.leo.fundservice.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

/**
 * 功能描述：http工具类
 * @author leo-zu
 * @create 2021-05-27 21:54
 */
@Slf4j
public class HttpUtils {
    /**
     * 功能描述：过滤http://www.baidu.com开头的链接
     * @param url url地址
     * @return ture
     */
    private boolean filteLink(String url){
        if (url!=null && url.startsWith("http://www.baidu.com")){
            return true;
        }else {
            return false;
        }
    }

    private static GetMethod setHttpClientGetConfig(HttpClient httpClient, String url){
        // 1、设置 Http 连接超时 5s
        HttpConnectionManager httpConnectionManager = httpClient.getHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(5000);
        // 2、生成 GetMethod 对象并设置参数
        GetMethod getMethod = new GetMethod(url);
        // 设置get请求超时 5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        return getMethod;
    }

    /**
     * 功能描述：根据请求地址，返回html文件并解析
     * @param url 请求路径
     * @return 响应页面
     */
    public static ResponsePage sendRequestAndResponseHtml(HttpClient httpClient, String url){
        // 1、httpClient get请求配置
        GetMethod getMethod =setHttpClientGetConfig(httpClient, url);
        ResponsePage page = null;
        try{
            // 2、执行HTTP Get请求
            int code = httpClient.executeMethod(getMethod);
            // 判断访问状态码
            if (code != HttpStatus.SC_OK){
                log.info("Method failed：{}", getMethod.getStatusLine());
            }
            // 4、处理Http响应内容
            byte[] responseBody = getMethod.getResponseBody();
            // 得到当前返回类型
            String contentType = getMethod.getResponseHeader("Content-Type").getValue();
            //封装成为页面
            page = new ResponsePage(responseBody, url, contentType);
        }catch (HttpException e){
            // 协议不对或返回的内容有问题
            e.printStackTrace();
        } catch (IOException e) {
            // 网络异常
            e.printStackTrace();
        }finally {
            // 释放连接
            getMethod.releaseConnection();
        }
        return page;
    }

    /**
     * 功能描述：发送get请求，返回date数据
     * @param url 请求路径
     * @return json
     */
    public static JSONObject sendGetRequestAndResponseDate(HttpClient httpClient, String url){
        // 1、生成 GetMethod 对象并设置参数
        GetMethod getMethod = setHttpClientGetConfig(httpClient, url);
        JSONObject response = null;
        try{
            // 2、执行HTTP Get请求
            int code = httpClient.executeMethod(getMethod);
            // 判断访问状态码
            if (code != HttpStatus.SC_OK){
                log.info("Method failed：{}", getMethod.getStatusLine());
            }
            // 3、处理Http响应内容
            byte[] responseBodyByte = getMethod.getResponseBody();

            String responseBody = new String(responseBodyByte);
            log.info(responseBody);
            if(!StringUtils.isBlank(responseBody)){
                int indexOf = responseBody.indexOf("{");
                responseBody = responseBody.substring(indexOf);
            }
            // 4、转换为json
            response = JSONObject.parseObject(responseBody);
            log.info(response.toJSONString());
        }catch (HttpException e){
            // 协议不对或返回的内容有问题
            e.printStackTrace();
        } catch (IOException e) {
            // 网络异常
            e.printStackTrace();
        }finally {
            // 释放连接
            getMethod.releaseConnection();
        }
        return response;
    }


}
