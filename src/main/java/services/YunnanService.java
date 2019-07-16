package services;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 云南省
 */
public class YunnanService {

    private static  final Logger LOGGER = Logger.getLogger(YunnanService.class); //使用log4j记录日志


    public List<Tender> getList(String url, FormBody body, String city) {
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
                Element element = doc.getElementById("data_tab");
                Elements elements = element.select("tr");
                elements.remove(0);
                for (Element el : elements) {
                    Tender tender = new Tender();
                    String daytime = el.select("td").get(3).text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "https://www.ynggzyxx.gov.cn" + el.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = el.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href, tender, "detail_contect","utf-8"); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setFrom("云南省公共资源交易信息网");
                        tender.setFromurl(href);
                        tender.setAddress(city);
                        tender.setCatid(1);
                        tender.setStatus(1);
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
            }else {
                LOGGER.info("云南 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("云南：" + city + "列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
