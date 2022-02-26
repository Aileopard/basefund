package com.leo.fundservice.handle.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leo.fundservice.mapper.FundHisInfoMapper;
import com.leo.fundservice.model.FundHisInfo;
import com.leo.fundservice.service.FundHisInfoService;
import com.leo.fundservice.utils.HttpUtils;
import com.leo.fundservice.utils.PageParserUtils;
import com.leo.fundservice.utils.ResponsePage;
import com.leo.fundservice.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 功能描述：基金爬虫
 * @author leo-zu
 * @date 2021-05-28 14:21
 */
@Service
@Slf4j
public class FundCrawler {
    @Autowired
    private FundHisInfoService fundHisInfoService;
    @Autowired
    private FundHisInfoMapper fundHisInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void crawlerFund(){
        HttpClient httpClient = new HttpClient();
        // 1,200
        String prefix = "http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=zdf,desc&page=";
        String suffix = "&dt=1622187025947&atfc=&onlySale=0";
        // 页数
        int page = 1;
        // 当前页大小
        int pageSize = 200;
        int total = 0;
        List<FundHisInfo> list = new ArrayList<>();
        while (page <= 56){
//            log.info("当前页：{}，第{}条开始", page, page * pageSize);
            try {
                JSONObject response = HttpUtils.sendGetRequestAndResponseDate(httpClient, prefix + page + "," + pageSize + suffix);
                JSONArray datas = response.getJSONArray("datas");
                // 每天早上更新历史净值
                for (int i=0; i<datas.size(); i++){
                    JSONArray singleFund = datas.getJSONArray(i);
                    String fundCode = (String) singleFund.get(0);
                    String fundName = (String) singleFund.get(1);
                    String fundNetValue = (String) singleFund.get(3);
                    String fundTotalNetValue = (String) singleFund.get(4);
                    String dayIncreaseValue = (String) singleFund.get(7);
                    String dayIncreaseRate = (String) singleFund.get(8);
                    FundHisInfo fundHisInfo = FundHisInfo.builder().fundCode(fundCode)
                            .fundName(fundName).entroDate(new Date()).build();
                    if (StringUtils.isBlank(fundNetValue)){
                        fundHisInfo.setFundNetValue(BigDecimal.valueOf(0));
                    }else {
                        fundHisInfo.setFundNetValue(new BigDecimal(fundNetValue));
                    }

                    if (StringUtils.isBlank(fundTotalNetValue)){
                        fundHisInfo.setFundTotalNetValue(BigDecimal.valueOf(0));
                    }else {
                        fundHisInfo.setFundTotalNetValue(new BigDecimal(fundTotalNetValue));
                    }

                    if (StringUtils.isBlank(dayIncreaseValue)){
                        fundHisInfo.setDayIncreaseValue(BigDecimal.valueOf(0));
                    }else {
                        fundHisInfo.setDayIncreaseValue(new BigDecimal(dayIncreaseValue));
                    }

                    if (StringUtils.isBlank(dayIncreaseRate)){
                        fundHisInfo.setDayIncreaseRate(BigDecimal.valueOf(0));
                    }else {
                        fundHisInfo.setDayIncreaseRate(new BigDecimal(dayIncreaseRate));
                    }
                    list.add(fundHisInfo);
                }
                total = total + list.size();
//                log.info("正在爬取中---共抓取:{}条数据",total);
                // 批量插入
                fundHisInfoService.insertBatchFundHisInfo(list);
                list.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
            page++;
        }
//        log.info("完成{}基金信息的更新", new Date());
    }

    /**
     * 功能描述：爬取seed(url)地址的基金信息
     * @param seed seed
     */
    public void crawlerFund(String seed){
        HttpClient httpClient = new HttpClient();
        // 1、初始化 url 队列
        Seeds.addUnvisitedUrlQueue(seed);
        // 2、循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!Seeds.unVisitedUrlQueueIsEmpty()
                && Seeds.getVisitedUrlNum() <= 1000){
            // 2.1、先从待访问的序列中取出第一个
            String url = (String) Seeds.removeHeadOfUnVisitedUrlQueue();
            if (url == null){
                continue;
            }
            // 2.2、根据 url 得到page
            ResponsePage page = HttpUtils.sendRequestAndResponseHtml(httpClient, url);
            Elements tr = page.getDoc().select("tr");
            // 2.3、解析每一个tr元素
            parseElements(tr);

            // 2.4、将已经访问过的链接放入已访问链接中
            Seeds.addVisitedUrlSet(url);
            // 2.5、得到超链接
            Set<String> seeds = PageParserUtils.getSeeds(page, "a");
            for (String s : seeds) {
                Seeds.addUnvisitedUrlQueue(s);
//                log.info("新增爬取路径: {}", s);
            }
        }
    }

    public void parseElements(Elements tr){
        // 遍历每一个tr的元素
        for (Element element : tr) {
            // 获取爬取到的tr元素下的 td子元素
            Elements td = element.select("td");
            // 基金代码
            String fundCode = null;
            // 基金名称
            String fundName = null;
            // 昨日单位净值
            String lastFundDwjz = null;
            // 昨日累计净值
            String lastFundLjjz = null;
            // 今日单位净值
            String fundDwjz = null;
            // 今日累计净值
            String fundLjjz = null;
            for (int i=0; i<td.size(); i++) {
                Element next = td.get(i);
                switch (i){
                    case 3:
                        fundCode = getValueByClassName(next, "bzdm");
                        break;
                    case 4:
                        fundName = getValueByClassName(next, "tol");
                        break;
                    case 5:
                        lastFundDwjz = getValueByClassName(next, "dwjz");
                        break;
                    case 6:
                        lastFundLjjz = getValueByClassName(next, "ljjz");
                        break;
                    case 7:
                        fundDwjz = getValueByClassName(next, "dwjz");
                        break;
                    case 8:
                        fundLjjz = getValueByClassName(next, "ljjz");
                        break;
                    default:
                        break;
                }
            }
            if (!StringUtils.isBlank(fundCode)){
                // log.info("基金{}--{}--{}--{}--{}--{}",fundCode, fundName, lastFundDwjz, lastFundLjjz, fundDwjz, fundLjjz);
            }
        }
    }

    /**
     * 功能描述：根据类选择器名称获取元素值
     * @param element 元素
     * @param className 类选择器名称
     */
    public String getValueByClassName(Element element, String className){
        Elements elements = element.getElementsByClass(className);
        if ("tol".equals(className)){
            elements = elements.select("a");
        }
        if (elements != null && elements.size() != 0
                && elements.get(0).childNodes() != null
                && elements.get(0).childNodes().size() != 0) {
            Node node = elements.get(0).childNodes().get(0);
            if (!StringUtils.isBlank(node.toString())){
                return node.toString();
            }
        }
        return null;
    }
}
