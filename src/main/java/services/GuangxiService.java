package services;

import bean.Tender;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
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
 * 广西省
 */
public class GuangxiService {

    private static  final Logger LOGGER = Logger.getLogger(GuangxiService.class); //使用log4j记录日志

    public List<Tender> getList(HtmlPage page, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            String html = page.asXml();
            Document doc = Jsoup.parse(html);
            Element element = doc.getElementById("MoreInfoList1_DataGrid1");
            Elements trs = element.select("tr");
            trs.remove(0);
            for (Element tr : trs) {
                Tender tender = new Tender();
                String href = "http://gxggzy.gxzf.gov.cn" + tr.select("a").attr("href");
                String daytime = tr.select("td").get(2).text();
                //判断时间
                if (GetContentUtil.DateDujge(tender, daytime)) break;
                int tit = new BaseDao().selectUrl(href);
                if (tit == 0) {
                    String title = tr.select("a").attr("title");
                    if (new BaseDao().selectTitle(title) != 0) continue;
                    GetContentUtil.getCont(href, tender, "infodetail","utf-8");
                    if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                        continue;
                    tender.setYeWuType("建设工程");
                    tender.setFrom("全国公共资源交易平台（广西壮族自治区）");
                    tender.setFromurl(href);
                    tender.setTitle(title);
                    tender.setAddress(city);
                    tender.setCatid(1);
                    tender.setStatus(1);
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


        } catch ( Exception e) {
            LOGGER.info("广西：" + city);
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return  tenderList;
    }
}
