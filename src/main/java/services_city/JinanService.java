package services_city;

import bean.JinanJson;
import bean.Tender;
import com.google.gson.Gson;
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
 * 济南市
 */
public class JinanService {

    private static final Logger LOGGER = Logger.getLogger(JinanService.class); //使用log4j记录日志

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
                Gson gson = new Gson();
                JinanJson jinanJson = gson.fromJson(html, JinanJson.class);
                String htmls = jinanJson.getParams().getStr().replace("//", "");
                Document doc = Jsoup.parse(htmls);
                Elements lis = doc.getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("span2").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) ;
                    String num = li.select("a").attr("onclick").replace("showview(", "").replace(")", "");
                    String href = "http://jnggzy.jinan.gov.cn/jnggzyztb/front/showNotice.do?iid=" + num + "&xuanxiang=%E6%8B%9B%E6%A0%87%E5%85%AC%E5%91%8A";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "list");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setFrom("济南公共资源交易中心");
                        tender.setFromurl(href);
                        tender.setTitle(title);
                        tender.setAddress("济南");
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
                LOGGER.info("济南市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("济南市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    /**
     * 获取正文 class
     *
     * @param url
     * @return
     */
    public static void getCont(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);

                Elements elements = doc.getElementsByClass(classStr);
                if (elements != null) {
                    elements.select("a").remove();
                    elements.select("img").remove();
                    elements.get(0).getElementsByClass("close").remove();
                    String content = elements.html().trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
