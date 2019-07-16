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
import java.util.Stack;

/**
 * 延安市
 */
public class YananService {

    private static final Logger LOGGER = Logger.getLogger(YananService.class); //使用log4j记录日志

    public List<Tender> getList(String url, String category, int catid) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("ewb-right-item clearfix");
                for (Element li : elements) {
                    Tender tender = new Tender();
                    String daytime = li.select("span").text().replace("\"","");
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.yaggzyjy.cn" + li.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getCont(href,tender,"mainContent");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setYeWuType(category);
                        tender.setAddress("延安");
                        tender.setCatid(catid);
                        tender.setStatus(1);
                        tender.setFrom("延安市公共资源交易中心");
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
                LOGGER.info("延安市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("延安市 列表获取报错");
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
                String title = doc.getElementsByClass("article-title").text();
                if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
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
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
