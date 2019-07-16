package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import services.AnhuiService;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 中山市
 */
public class ZhongshanService {

    private static final Logger LOGGER = Logger.getLogger(ZhongshanService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();

        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttp(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("nav_list");
                Elements lis = elements.get(0).getElementsByClass("clear");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByTag("span").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.zsjyzx.gov.cn/Application/NewPage/ggnr.jsp?" + li.getElementsByTag("a").attr("href").split("&")[1];
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href, tender, "articalDiv","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress("中山");
                        tender.setFrom("中山市公共资源交易中心");
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
                LOGGER.info("中山市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("中山市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;

    }
}
