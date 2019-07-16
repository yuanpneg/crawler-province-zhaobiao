package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 珠海市
 */
public class ZhuhaiService {
    private static final Logger LOGGER = Logger.getLogger(ZhuhaiService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();

        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("rl-box-right");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByTag("span").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = li.getElementsByTag("a").get(0).attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.getElementsByTag("a").get(0).attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href,tender,"newsCon","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("珠海");
                        tender.setFrom("珠海市公共资源交易中心");
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    }else {
                        break;
                    }
                }
            } else {
                LOGGER.info("珠海市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("珠海市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;

    }
}
