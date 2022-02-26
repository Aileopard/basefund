package com.leo.fundservice.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leo.fundservice.mapper.MovieMapper;
import com.leo.fundservice.model.Movie;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @author leo-zu
 * @create 2021-05-27 21:13
 */
public class FundPc {
    public static  void  main(String [] args) {
        // 定义配置文件路径
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            // 读取配置文件
            inputStream = Resources.getResourceAsStream(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 注册mybatis 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 得到连接对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 从mybatis中得到dao对象
        MovieMapper movieMapper = sqlSession.getMapper(MovieMapper.class);

        int start; //页数
        int total = 0;  //记录数
        int end = 10000; //总共9979条数据
        for (start  = 0; start <= 100; start++)  {
            try {

                String address = "https://Movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&page_limit=100&page_start=" + start;

                JSONObject dayLine = new GetJson().getHttpJson(address, 1);

                System.out.println("start:" + start);
                JSONArray json = dayLine.getJSONArray("subjects");
                List<Movie> list = JSON.parseArray(json.toString(), Movie.class);
                if (list.isEmpty()){
                    break;
                }
                for (Movie movie : list) {
                    movieMapper.insert(movie);
                    sqlSession.commit();
                }
                total += list.size();
                System.out.println("正在爬取中---共抓取:" + total + "条数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("已经爬取到底了");
        sqlSession.close();
    }
}
