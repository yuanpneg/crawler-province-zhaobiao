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
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 抚顺市
 * @author yp
 */
public class FushunService {

    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(FushunService.class);

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("data");
                if(elements.size() > 0) {
                    Elements trs = elements.get(0).getElementsByTag("table").get(1).getElementsByTag("tr");
                    trs.remove(19);
                    trs.remove(18);
                    for (Element tr : trs) {
                        Tender tender = new Tender();
                        String daytime = tr.getElementsByTag("td").get(2).text();
                        if (GetContentUtil.DateDujge(tender, daytime)) {
                            break; //判断时间
                        }
                        String href = "http://fsggzy.fushun.gov.cn" + tr.getElementsByClass("list-item").get(0).select("a").attr("href");
                        int tit = new BaseDao().selectUrl(href);
                        if (tit == 0) {
                            String title = tr.getElementsByTag("td").get(1).select("a").attr("title");
                            if (new BaseDao().selectTitle(title) != 0) {
                                continue;
                            }
                            GetContentUtil.getCont(href, tender, "contentclass","utf-8");
                            if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) {
                                continue;
                            }
                            tender.setFromurl(href);
                            tender.setTitle(title);
                            tender.setYeWuType("建设工程");
                            tender.setAddress("抚顺");
                            tender.setCatid(1);
                            tender.setStatus(1);
                            tender.setFrom("抚顺市公共资源交易网");
                            GetContentUtil.updateTenderRegionLngLat(tender);
                            System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                            if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                                continue;
                            }
                            GetContentUtil.insertThreadData(tender);
                            tenderList.add(tender);
                        } else {
                            break;
                        }
                    }
                }

            }else {
                LOGGER.info("抚顺市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("抚顺市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
