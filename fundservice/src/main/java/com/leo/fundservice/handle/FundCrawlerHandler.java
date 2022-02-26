package com.leo.fundservice.handle;

import com.leo.fundservice.handle.crawler.FundCrawler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能描述：基金爬虫定时任务
 * @author leo-zu
 * @date 2021-05-28 22:53
 */
@Slf4j
@Component
public class FundCrawlerHandler extends IJobHandler {
    @Autowired
    private FundCrawler fundCrawler;

    @Override
    @XxlJob("fundCrawlerHandler")
    public ReturnT<String> execute(String s) throws Exception {
        fundCrawler.crawlerFund();
        return ReturnT.SUCCESS;
    }
}
