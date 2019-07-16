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
import services_city.DazhouService;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 天津市
 */
public class TianjinService {

    private static final Logger LOGGER = Logger.getLogger(DazhouService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("article-list3-t");
                for (Element element : elements) {
                    Tender tender = new Tender();
                    String daytime = element.getElementsByClass("list-times").text();
                    if(GetContentUtil.DateDujge(tender,daytime))  break;
                    String href = element.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = element.select("a").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href, tender, "content"); //获取正文
                    }else{
                        break;
                    }
                }
            }else {
                LOGGER.info("天津 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("天津市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    public void getContById(String url, Tender tender,String classStr) {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setDownloadImages(false);


        try {
            HtmlPage page = webClient.getPage(url);
            String html = page.asXml();
            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByClass(classStr);
            if (elements != null) {
                String title = doc.getElementsByClass("ewb-main-h2").get(0).text();
                if (new BaseDao().selectTitle(title) == 0) {
                    tender.setTitle(title);
                    //elements.select("a").remove();
                    //elements.select("img").remove();
                    String content = elements.html().trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        } catch (Exception e) {
            LOGGER.info("天津：获取正文报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }


}
