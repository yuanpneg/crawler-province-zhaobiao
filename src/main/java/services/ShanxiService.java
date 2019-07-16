package services;


import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.GetContentUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 陕西省
 */
public class ShanxiService {

    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(ShanxiService.class);

    public List<Tender> getList(String url, String city, String urlFirst) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Element elements = doc.getElementById("categorypagingcontent");
                Elements lis = elements.getElementsByClass("ewb-list-node clearfix");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = "";
                    //获取当前年份
                    if ("榆林".equals(city)) {
                        String year = li.getElementsByTag("a").attr("href").split("/")[5].substring(0, 4);
                        daytime = year + "-" + li.getElementsByClass("ewb-list-date").text();
                    } else {
                        daytime = li.getElementsByClass("ewb-list-date").text();
                    }
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = urlFirst + li.getElementsByTag("a").attr("href");
                    //int tit = new BaseDao().selectUrl(href);
                    //城市内存放省级数据，三个城市的数据相同
                    if (true) {
                        String title = li.getElementsByTag("a").attr("title");
                        //if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getContById(href, tender, "mainContent", "utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom("陕西省公共资源交易中心");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        //正文链接
                        tender.setFromurl(href);
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if (null == tender.getLongitude() || "".equals(tender.getLongitude())) {
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    } else {
                        break;
                    }
                }
            } else {
                LOGGER.info("陕西 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("陕西省 列表获取错误");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

}