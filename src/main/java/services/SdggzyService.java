package services;

import bean.Tender;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.GetContentUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 山东省
 */
public class SdggzyService {


    //使用log4j记录日志
    private static final Logger LOGGER = Logger.getLogger(SdggzyService.class);

    public List<Tender> getList(String url, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("article-content");
                Elements divs = elements.get(0).getElementsByClass("article-list3-t");
                for (Element div : divs) {
                    Tender tender = new Tender();
                    String href = div.select("a").attr("href");
                    String daytime = div.getElementsByClass("list-times").get(0).text().split(" ")[0];
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = div.select("a").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        //获取正文
                        getCont(href, tender, "gycq-table");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setFrom("全国公共资源交易平台（山东省）");
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
            }
        } catch (Exception e) {
            LOGGER.info("山东：" + city + "列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    public static void getCont(String url, Tender tender,String str){
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setDownloadImages(false);

        try {
            HtmlPage page = webClient.getPage(url);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
