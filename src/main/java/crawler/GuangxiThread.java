package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import dao.BaseDao;

import dao.FormalDao;
import services.GuangxiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 广西省
 */
public class GuangxiThread implements Runnable {

    private GuangxiService service = new GuangxiService();

    String cityJson = "[" +
            "{\"id\":\"450100\",\"name\":\"南宁\"},{\"id\":\"450200\",\"name\":\"柳州\"}," +
            "{\"id\":\"450300\",\"name\":\"桂林\"},{\"id\":\"450400\",\"name\":\"梧州\"}," +
            "{\"id\":\"450500\",\"name\":\"北海\"},{\"id\":\"450600\",\"name\":\"防城港\"}," +
            "{\"id\":\"450700\",\"name\":\"钦州\"},{\"id\":\"450800\",\"name\":\"贵港\"}," +
            "{\"id\":\"450900\",\"name\":\"玉林\"},{\"id\":\"451000\",\"name\":\"百色\"}," +
            "{\"id\":\"451100\",\"name\":\"贺州\"},{\"id\":\"451200\",\"name\":\"河池\"}," +
            "{\"id\":\"451300\",\"name\":\"来宾\"},{\"id\":\"451400\",\"name\":\"崇左\"}" +
            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setDownloadImages(false);
        try {
            for (int i = 0; i < cityJsonArr.size(); i++) {
                JSONObject job = cityJsonArr.getJSONObject(i);
                System.out.println(job.get("name"));
                String url = "http://gxggzy.gxzf.gov.cn/gxzbw/showinfo/MoreInfo.aspx?QuYu=" +
                        job.get("id")
                        + "&categoryNum=001001001";
                HtmlPage page = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(1000*2);
                for (int j = 1; j < 20; j++) {
                    DomElement firstPageHtml = page.getElementById("MoreInfoList1_Pager");
                    List list = firstPageHtml.getElementsByTagName("a");
                    HtmlAnchor anchor = (HtmlAnchor)list.get(list.size()-2);
                    if(j != 1){
                        page = anchor.click();
                    }
                    List<Tender> tenderList= service.getList(page, job.get("name").toString());
                    if(tenderList.size() == 0){
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
