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
 * 汉中市
 */
public class HanzhongService {

    private static final Logger LOGGER = Logger.getLogger(HanzhongService.class); //使用log4j记录日志

    public List<Tender> getList(String url, String category, int catid) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByTag("table");
                Element trs = elements.get(23).getElementsByTag("table").get(0).parent();
                Elements spans= trs.getElementsByTag("table").get(7).getElementsByTag("tr");
                for (Element tr : spans) {
                    Tender tender = new Tender();
                    String daytime = tr.select("td").get(2).text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.hzzbb.com/" + tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.select("a").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href,tender);
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType(category);
                        tender.setAddress("汉中");
                        tender.setCatid(catid);
                        tender.setStatus(1);
                        tender.setFrom("汉中市建设工程招投标信息网");
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
            }else {
                LOGGER.info("汉中市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("汉中市 列表获取报错");
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
    public static void getCont(String url, Tender tender) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByTag("table");
                Element element = elements.get(23);
                if (element != null) {
                    String content = element.html().trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        }catch (Exception e) {
            LOGGER.info("汉中市：正文获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }
}
