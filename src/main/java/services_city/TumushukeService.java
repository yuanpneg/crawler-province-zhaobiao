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
 * 图木舒克市
 */
public class TumushukeService {

    private static final Logger LOGGER = Logger.getLogger(TumushukeService.class); //使用log4j记录日志

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
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "UTF-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("top10").get(1).getElementsByTag("tbody");
                Elements trs = elements.get(1).getElementsByTag("tr");
                for (int i = 0; i < trs.size(); i += 2) {
                    Tender tender = new Tender();
                    String daytime = trs.get(i).select("td").get(2).text().replace("[","").replace("]","");
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://ggzy.xjbt.gov.cn"+trs.get(i).getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = trs.get(i).getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getContById(href,tender,"TDContent","UTF-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress("图木舒克");
                        tender.setFrom("兵团公共资源交易中心第三分中心");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFromurl(href);//正文链接
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    }else{
                        break;
                    }
                }
            }else{
                LOGGER.info("图木舒克市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("图木舒克市 请求列表报错");
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
//    public static void getContById(String url, Tender tender, String classStr) {
//        try {
//            Request request = new Request.Builder().url(url).build();
//            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
//            if (responseWrap.isSuccess()) {
//                String html = responseWrap.body;
//                Document doc = Jsoup.parse(html);
//
//                Element elements = doc.getElementById(classStr);
//                if (elements != null) {
//                    elements.select("a").remove();
//                    elements.select("img").remove();
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
