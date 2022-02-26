package com.leo.fundservice.utils;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * 功能描述：网页解析工具类
 * @author leo-zu
 * @date 2021-05-28 8:40
 */
public class PageParserUtils {
    /**
     * 通过选择器来选取页面
     */
    public static Elements select(ResponsePage page, String cssSelector){
        return page.getDoc().select(cssSelector);
    }

    /**
     * 通过css选择器来得到指定元素
     * @param page
     * @param cssSelector
     * @param index
     * @return
     */
    public static Element select(ResponsePage page, String cssSelector, int index){
        Elements elements = select(page, cssSelector);
        int realIndex = index;
        if (index < 0){
            realIndex = elements.size() + index;
        }
        return elements.get(realIndex);
    }

    /**
     * 功能描述：获取页面中，指定选择器下的所有超链接
     * @param page
     * @param cssSelector
     * @return
     */
    public static Set<String> getSeeds(ResponsePage page, String cssSelector){
        Set<String> seeds = new HashSet<>();
        Elements elements = select(page, cssSelector);
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element next = iterator.next();
            if (next.hasAttr("href")){
                seeds.add(next.attr("abs:href"));
            }else if (next.hasAttr("src")){
                seeds.add(next.attr("abs:src"));
            }
        }
        return seeds;
    }

    /**
     * 功能描述：获取网页中满足指定css选择器的所有元素的指定属性的集合
     * 例如通过getAttrs("img[src]","abs:src")可获取网页中所有图片的链接
     * @param page
     * @param cssSelector
     * @param attrName
     * @return
     */
    public static List<String> getAttrs(ResponsePage page, String cssSelector, String attrName){
        List<String> result = new ArrayList<>();
        Elements elements = select(page, cssSelector);

        for (Element element : elements) {
            if (element.hasAttr(attrName)){
                result.add(element.attr(attrName));
            }
        }
        return result;
    }


}
