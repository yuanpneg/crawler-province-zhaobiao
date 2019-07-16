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
import services.SichuanService;
import utils.CompressUtils;
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 咸阳市
 */
public class XianyangService {

    private static final Logger LOGGER = Logger.getLogger(XianyangService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList= new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("ewb-list");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                  //  Calendar date = Calendar.getInstance();
                  //  String daytime = date.get(Calendar.YEAR)+ "-" + li.getElementsByClass("ewb-list-date").text();
                  ///  if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://xy.sxggzyjy.cn" + li.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender,"epoint-article-content","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("咸阳");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("咸阳市公共资源交易中心");
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
                LOGGER.info("咸阳市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("咸阳市 列表获取报错");
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
    public static void getCont(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                String el = doc.getElementsByClass("info-source").text();
                //【信息来源：咸阳市】 【信息时间：2019-05-17】
                String daytime = el.split("信息时间：")[1].split("】")[0];
                if(GetContentUtil.DateDujge(tender, daytime) == false) {
                    Elements elements = doc.getElementsByClass(classStr);
                    if (elements != null) {
                        String content = elements.html().trim();
                        //压缩
                        tender.setContent(CompressUtils.compress(content));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
