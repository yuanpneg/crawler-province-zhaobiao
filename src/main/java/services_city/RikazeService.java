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
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 日喀则
 */
public class RikazeService {

    private static final Logger LOGGER = Logger.getLogger(RikazeService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                String element = doc.getElementById("4015").html();
                String[] arr = element.split("href=\"/art");
                List<String> list = new ArrayList<>();
                for (String s : arr) {
                    if (s.indexOf("html") != -1) {
                        String aa = s.split("html")[0];
                        list.add("http://ggzyjy.rkzszf.gov.cn/art" + s.split("html")[0] + "html");
                    }
                }

                for (String href : list) {
                    Tender tender = new Tender();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getCont(href, tender, "zoom");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("日喀则市");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("日喀则公共资源交易中心");
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
                LOGGER.info("日喀则 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("日喀则 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    /**
     * 获取正文
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
                String title = doc.getElementsByClass("title").text();
                Elements elements = doc.getElementsByClass("lm-list").select("table").get(1).getElementsByTag("td");
                String daytime = elements.get(0).text().replace("发布日期: ", "");
                if (GetContentUtil.DateDujge(tender, daytime) == false) {
                    if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
                        tender.setTitle(title);
                        Element element = doc.getElementById(classStr);
                        if (element != null) {
                            element.select("a").remove();
                            element.select("img").remove();
                            String content = element.html().trim();
                            tender.setContent(CompressUtils.compress(content)); //压缩
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
