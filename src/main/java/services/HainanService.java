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

import java.util.ArrayList;
import java.util.List;

/**
 * 海南省
 */
public class HainanService {

    private static  final Logger LOGGER = Logger.getLogger(HainanService.class); //使用log4j记录日志


    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("newtable");
                Elements trs = elements.get(0).getElementsByTag("tr");
                trs.remove(0);
                trs.remove(trs.size()-1);
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.select("td").get(3).text();
                    if("".equals(daytime)) break;
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href, tender, "newsCon","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom("全国公共资源交易平台（海南省）");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFromurl(href);//正文链接
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    } else {
                        break;
                    }
                }

            } else {
                LOGGER.info("海南 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("海南：" + city+"列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

        return tenderList;
    }
}
