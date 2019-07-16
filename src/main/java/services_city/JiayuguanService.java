package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
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
 * 嘉峪关
 */
public class JiayuguanService {

    private static final Logger LOGGER = Logger.getLogger(JiayuguanService.class); //使用log4j记录日志

    public List<Tender> getList(String url, FormBody body) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .url(url)
                    .post(body)
                    .build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("gsPropertyBox");
                for (Element element : elements) {
                    Tender tender = new Tender();
                    String daytime = element.getElementsByClass("gsPropertyLeftTime").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.jygzyjy.cn" + element.attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = element.getElementsByClass("gsPropertyCon").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        tender.setTitle(title);
                        GetContentUtil.getCont(href, tender, "jxTradingPublic","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("嘉峪关");
                        tender.setFrom("嘉峪关市公共资源交易中心");
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
            }else {
                LOGGER.info("嘉峪关市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("嘉峪关市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
