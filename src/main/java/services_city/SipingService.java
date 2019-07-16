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
import services.ShanghaiService;
import utils.GetContentUtil;


import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

/**
 * 四平市
 */
public class SipingService {

    private static final Logger LOGGER = Logger.getLogger(SipingService.class); //使用log4j记录日志

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
                Elements elements = doc.getElementsByClass("wb-data-item");
                Elements lis = elements.get(0).getElementsByClass("wb-data-list");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("wb-data-date").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://ggzy.siping.gov.cn"+li.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href,tender,"ewb-article-info","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress("四平");
                        tender.setFrom("四平市公共资源平台");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        //正文链接
                        tender.setFromurl(href);
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
                LOGGER.info("四平市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("四平 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
