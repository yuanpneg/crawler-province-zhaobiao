package crawler_city;

import bean.Tender;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import dao.BaseDao;
import dao.FormalDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import services_city.HaerbinService;
import utils.GetContentUtil;


import java.io.IOException;
import java.util.List;

/**
 * 哈尔滨
 */
public class HaerbinThread implements Runnable {

    private HaerbinService service = new HaerbinService();

    @Override
    public void run() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setDownloadImages(false);
        System.out.println("哈尔滨");
        String url = "http://www.hrbjjzx.cn/Bid_Front/ZBMore.aspx?t=%e5%85%a8%e9%83%a8";
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);
            List<Tender> list = service.getList(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
