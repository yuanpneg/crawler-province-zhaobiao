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
 * 喀什市
 */
public class KashiService {

    private static final Logger LOGGER = Logger.getLogger(KashiService.class); //使用log4j记录日志

    public List<Tender> getList(String html) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(html);
            Element element = doc.getElementById("MoreInfoList1_tdcontent");
            Elements elements = element.getElementsByTag("tr");
            for (Element el : elements) {
                Tender tender = new Tender();
                String daytime = el.getElementsByTag("td").get(2).text().replace("[","").replace("]","");
                if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                String href = "http://ztb.xjjs.gov.cn" + el.getElementsByTag("a").attr("href");
                int tit = new BaseDao().selectUrl(href);
                if (tit == 0) {
                    String title = el.getElementsByTag("a").attr("title");
                    if (new BaseDao().selectTitle(title) != 0) continue;
                    GetContentUtil.getContById(href,tender,"TDContent","gbk");
                    if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                    tender.setTitle(title);
                    tender.setFromurl(href);
                    tender.setCatid(1);
                    tender.setStatus(1);
                    tender.setYeWuType("建设工程");
                    tender.setAddress("喀什");
                    tender.setFrom("喀什地区建设工程信息网");
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
        } catch (Exception e) {
            LOGGER.info("喀什市 列表获取报错");
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
