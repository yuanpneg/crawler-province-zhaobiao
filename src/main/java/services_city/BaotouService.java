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
 * 包头市
 */
public class BaotouService {

    private static final Logger LOGGER = Logger.getLogger(BaotouService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("ewb-list");
                Elements trs = elements.get(0).getElementsByClass("ewb-list-node clearfix");
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.getElementsByClass("ewb-list-date").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.btggzyjy.cn" + tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.getElementsByClass("ewb-list-name ewb-otw").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "ewb-article-info");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("包头");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("包头市公共资源交易中心");
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
                LOGGER.info("包头市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("包头市 列表获取报错");
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
                    //elements.select("a").remove();
                    //elements.select("img").remove();
                    String content = elements.html().trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
