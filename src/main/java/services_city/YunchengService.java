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
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 运城市
 */
public class YunchengService {

    private static final Logger LOGGER = Logger.getLogger(YunchengService.class); //使用log4j记录日志

    public List<Tender>   getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("wb-data-item");
                Elements lis = elements.get(0).getElementsByClass("wb-data-list");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("wb-data-date").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.ycggfw.gov.cn" + li.getElementsByClass("wb-data-infor").select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.getElementsByClass("wb-data-infor").select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href, tender, "article-content","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("运城");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("运城市公共资源交易中心");
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
            }else{
                LOGGER.info("运城市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("运城市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
