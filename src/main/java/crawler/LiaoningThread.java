package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import dao.BaseDao;

import dao.FormalDao;
import services.LiaoningService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 辽宁省
 */
public class LiaoningThread implements Runnable {


    private LiaoningService service = new LiaoningService();

    String cityJson = "[" +
            "{\"id\":\"005002\",\"name\":\"沈阳\"},{\"id\":\"005003\",\"name\":\"大连\"},{\"id\":\"005004\",\"name\":\"鞍山\"}," +
            "{\"id\":\"005005\",\"name\":\"抚顺\"},{\"id\":\"005006\",\"name\":\"本溪\"},{\"id\":\"005007\",\"name\":\"丹东\"}," +
            "{\"id\":\"005008\",\"name\":\"锦州\"},{\"id\":\"005009\",\"name\":\"营口\"},{\"id\":\"005010\",\"name\":\"阜新\"}," +
            "{\"id\":\"005011\",\"name\":\"辽阳\"},{\"id\":\"005012\",\"name\":\"盘锦\"},{\"id\":\"005013\",\"name\":\"铁岭\"}" +
            ",{\"id\":\"005014\",\"name\":\"朝阳\"},{\"id\":\"005015\",\"name\":\"葫芦岛\"}]";

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
                String url = "http://www.lnggzy.gov.cn/lnggzy/showinfo/Morejyxx.aspx?timebegin=2018-07-08&timeend=2018-08-08&timetype=04&num1=002&num2=002001&jyly=" +
                        job.get("id") + "&word=";
                HtmlPage page = webClient.getPage(url);
                System.out.println(job.get("name"));
                for (int j = 1; j < 20; j++) {
                    HtmlTextInput input = (HtmlTextInput) page.getElementByName("MoreInfoListjyxx1$Pager_input");
                    input.setValueAttribute(String.valueOf(j));
                    // HtmlAnchor anchor = (HtmlAnchor) page.getByXPath(
                    //       "//*[@class=\"gobutton\"]").get(0);
                    HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementByName("MoreInfoListjyxx1$Pager");
                    page = submit.click();
                    List<Tender> list =service.getList(page, job.get("name").toString());
                    if(list.size() == 0){
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
