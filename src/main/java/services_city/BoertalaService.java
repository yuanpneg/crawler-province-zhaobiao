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
import utils.CompressUtils;
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * 博尔塔拉
 */
public class BoertalaService {

    private static  final Logger LOGGER = Logger.getLogger(BoertalaService.class); //使用log4j记录日志


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
                Elements elements = doc.getElementsByClass("in_newslist");
                Elements trs = elements.get(0).getElementsByTag("tr");
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.getElementsByTag("td").get(1).text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = tr.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getContById(href,tender,"TDContent"); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setYeWuType("建设工程");
                        tender.setFrom("博州公共资源交易中心");
                        tender.setFromurl(href);
                        tender.setAddress("博尔塔拉");
                        tender.setCatid(1);
                        tender.setStatus(1);
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
                LOGGER.info("博尔塔拉 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("博尔塔拉 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    /**
     * 获取正文 id
     *
     * @param url
     * @return
     */
    public static void getContById(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                String title = doc.getElementById("lblTitle").text();
                if(new BaseDao().selectTitle(title) == 0) {
                    tender.setTitle(title);
                    Element elements = doc.getElementById(classStr);
                    if (elements != null) {
                        elements.select("a").remove();
                        elements.select("img").remove();
                        String content = elements.html().trim();
                        tender.setContent(CompressUtils.compress(content)); //压缩
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
