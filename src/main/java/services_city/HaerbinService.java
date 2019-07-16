package services_city;

import bean.Tender;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import dao.BaseDao;
import javafx.scene.input.InputMethodTextRun;
import okhttp3.FormBody;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * 哈尔滨
 */
public class HaerbinService {

    private static final Logger LOGGER = Logger.getLogger(HaerbinService.class); //使用log4j记录日志

    public List<Tender> getList(HtmlPage page) {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setDownloadImages(false);

        List<Tender> tenderList = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(page.asXml());
            Element elements = doc.getElementById("GV_Data");
            Elements trs = elements.getElementsByTag("tr");
            trs.remove(trs.size() - 1);
            trs.remove(trs.size() - 1);
            for (Element tr : trs) {
                Tender tender = new Tender();
                String daytime = tr.select("td").get(2).text();
                if (GetContentUtil.DateDujge(tender, daytime)) break;
                String href = tr.select("a").attr("id");

                HtmlAnchor anchor = (HtmlAnchor) page.getByXPath("//*[@id=\"" + href + "\"]").get(0);
                HtmlPage contentPage = anchor.click(); //正文
                Thread.sleep(1000);
                Document document = Jsoup.parse(contentPage.asXml());
                Element el = document.getElementById("main_box");
                Elements tables = el.select("table");
                if (tables.size() >= 9) {
                    String content = tables.get(6).html();
                    String title = tables.get(6).getElementById("lbl_Title").text();
                    if (new BaseDao().selectTitle(title) != 0) continue;
                    if ("".equals(content) || null == content || content.length() < 50) continue;
                    tender.setTitle(title);
                    tender.setFromurl(href);
                    tender.setContent(content);
                    tender.setCatid(1);
                    tender.setStatus(1);
                    tender.setYeWuType("建设工程");
                    tender.setAddress("哈尔滨");
                    tender.setFrom("哈尔滨公共资源交易中心");
                    GetContentUtil.updateTenderRegionLngLat(tender);
                    System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                    if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                        continue;
                    }
                    //GetContentUtil.insertThreadData(tender);
                    tenderList.add(tender);
                }
            }

        } catch (Exception e) {
            LOGGER.info("哈尔滨" + "列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

        return tenderList;
    }
}
