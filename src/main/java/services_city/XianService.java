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
 * 西安市
 */
public class XianService {

    private static final Logger LOGGER = Logger.getLogger(XianService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("list_style");
                Elements trs = elements.get(0).getElementsByClass("dg_liststyle1");
                trs.addAll(elements.get(0).getElementsByClass("dg_liststyle2"));
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.select("td").get(1).text().replace(".","-");
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.xacin.com.cn/XianGcjy/web/tender/" + tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        if (title.indexOf("采购") != -1) {
                            continue;
                        }
                        GetContentUtil.getContById(href, tender,"gctable","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("西安");
                        tender.setStatus(1);
                        tender.setFrom("西安市公共资源交易中心");
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
                LOGGER.info("西安市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("西安市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return  tenderList;
    }


    /**
     * 获取正文
     *
     * @param url
     * @return
     */
//    public static void getCont(String url, Tender tender) {
//        try {
//            Request request = new Request.Builder().url(url).build();
//            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
//            if (responseWrap.isSuccess()) {
//                String html = responseWrap.body;
//                Document doc = Jsoup.parse(html);
//                Element elements = doc.getElementById("gctable");
//                if (elements != null) {
//                    elements.select("a").remove();
//                    elements.select("img").remove();
//                    String content = elements.html().trim();
//                    tender.setContent(content); //压缩
//                }
//            }
//        }catch (Exception e) {
//            LOGGER.info("西安市：获取正文错误");
//            LOGGER.info(e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
}
