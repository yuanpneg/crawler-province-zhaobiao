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
 * 和田市
 */
public class HetianService {

    private static final Logger LOGGER = Logger.getLogger(HetianService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("news-list-l f_l");
                Elements uls = elements.get(0).getElementsByClass("news-list-con");
                for (Element ul : uls) {
                    Elements lis = ul.getElementsByTag("li");
                    for (Element li : lis) {
                        Tender tender = new Tender();
                        String daytime = li.getElementsByTag("span").text();
                        if (GetContentUtil.DateDujge(tender, daytime)) break;
                        String href = li.getElementsByTag("a").attr("href");
                        int tit = new BaseDao().selectUrl(href);
                        if (tit == 0) {
                            String title = li.getElementsByTag("a").attr("title");
                            if (new BaseDao().selectTitle(title) != 0) continue;
                            GetContentUtil.getCont(href, tender, "content","utf-8"); //获取正文
                            if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                            tender.setTitle(title);
                            tender.setFromurl(href);
                            tender.setCatid(1);
                            tender.setStatus(1);
                            tender.setYeWuType("建设工程");
                            tender.setAddress("和田");
                            tender.setFrom("和田市公共资源交易中心");
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
                }
            }else {
                LOGGER.info("和田市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("和田市 列表获取报错");
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
//    public static void getCont(String url, Tender tender, String classStr) {
//        try {
//            Request request = new Request.Builder().url(url).build();
//            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
//            if (responseWrap.isSuccess()) {
//                String html = responseWrap.body;
//                Document doc = Jsoup.parse(html);
//
//                Elements elements = doc.getElementsByClass(classStr);
//                if (elements != null) {
//                    String content = elements.html().trim();
//                    tender.setContent(CompressUtils.compress(content)); //压缩
//                }
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
