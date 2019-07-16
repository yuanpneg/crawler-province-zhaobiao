package services;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑龙江省
 */
public class HeilongjiangService {

    private static final Logger LOGGER = Logger.getLogger(HeilongjiangService.class); //使用log4j记录日志

    public List<Tender> getList(String url,String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("right_box");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("date").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://hljggzyjyw.gov.cn" + li.getElementsByTag("a").attr("href");
                    //int tit = new BaseDao().selectUrl(href);
                    if (true) {
                        String title = li.getElementsByTag("a").attr("title");
                        //if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getContById(href, tender,"contentdiv","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom("黑龙江公共资源交易网");
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
            } else {
                LOGGER.info("黑龙江省 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("黑龙江省 获取列表报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
