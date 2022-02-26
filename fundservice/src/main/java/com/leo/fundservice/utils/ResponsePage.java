package com.leo.fundservice.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;

/**
 * 功能描述：响应实体
 * @author leo-zu
 * @create 2021-05-27 22:07
 */
public class ResponsePage {
    /**
     * 响应内容
     */
    private byte[] content ;
    /**
     * 网页源码字符串
     */
    private String html ;
    /**
     * 网页Dom文档
     */
    private Document doc  ;
    /**
     * 字符编码
     */
    private String charset ;
    /**
     * url路径
     */
    private String url ;
    /**
     * 内容类型
     */
    private String contentType ;


    public ResponsePage(byte[] content , String url , String contentType){
        this.content = content ;
        this.url = url ;
        this.contentType = contentType ;
    }

    public String getCharset() {
        return charset;
    }

    public String getUrl(){
        return url;
    }

    public String getContentType(){
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }

    /**
     * 返回网页的源码字符串
     * @return 网页的源码字符串
     */
    public String getHtml() {
        if (html != null) {
            return html;
        }
        if (content == null) {
            return null;
        }
        if(charset==null){
            // 根据内容来猜测 字符编码
            charset = CharsetDetector.guessEncoding(content);
        }
        try {
            this.html = new String(content, charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 得到文档
     * @return document
     */
    public Document getDoc(){
        if (doc != null) {
            return doc;
        }
        try {
            this.doc = Jsoup.parse(getHtml(), url);
            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
