package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services.FujianggzyService;
import utils.GetContentUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 福建省
 */
public class FujianggzyThread implements Runnable {



    private FujianggzyService service = new FujianggzyService();

    String cityJson = "[{\"id\":\"350100\",\"name\":\"福州市\"},{\"id\":\"350200\",\"name\":\"厦门市\"}," +
            "{\"id\":\"350300\",\"name\":\"莆田市\"},{\"id\":\"350400\",\"name\":\"三明市\"}," +
            "{\"id\":\"350500\",\"name\":\"泉州市\"},{\"id\":\"350600\",\"name\":\"漳州市\"}," +
            "{\"id\":\"350700\",\"name\":\"南平市\"},{\"id\":\"350800\",\"name\":\"龙岩市\"}," +
            "{\"id\":\"350900\",\"name\":\"宁德市\"}]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        String url = "https://www.fjggfw.gov.cn/Website/AjaxHandler/BuilderHandler.ashx";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();

        String nowDate = sdf.format(calendar.getTime());

        calendar.add(Calendar.MONTH, -3);

        String startDate = sdf.format(calendar.getTime());

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setDownloadImages(false);

        webClient.waitForBackgroundJavaScript(1000);

        try {
            HtmlPage page = webClient.getPage("https://www.fjggfw.gov.cn/Website/JYXXNew.aspx");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cookie = getCookieString(webClient); //获取cookie

        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name"));
            for (int j = 1; j < 20; j++) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("OPtype", "GetListNew")
                        .add("pageNo", String.valueOf(j))
                        .add("pageSize", "10")
                        .add("proArea", job.get("id").toString())
                        .add("category", "GCJS")
                        .add("announcementType","1")
                        .add("ProType","-1")
                        .add("xmlx","-1")
                        .add("projectName","")
                        .add("TopTime",startDate+" 00:00:00")
                        .add("EndTime",nowDate+" 23:59:59")
                        .add("rrr", String.valueOf(Math.random()));
                FormBody body = formBodyBuilder.build();
                List<Tender> list=service.getList(url, body, cookie, job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }
    }


    private String getCookieString(WebClient webClient) {

        webClient.waitForBackgroundJavaScript(1000);

        Iterator<Cookie> cookieIterator = webClient.getCookieManager().getCookies().iterator();

        Map<String, String> cookiesMaps = new HashMap<>();


        while (cookieIterator.hasNext()) {
            Cookie cookie = cookieIterator.next();

            String cName = cookie.getName();

            String cValue = cookie.getValue();

            cookiesMaps.put(cName, cValue);
        }

        String result = "";

        for (String cookieKey : cookiesMaps.keySet()) {
            result += " " + cookieKey + "=" + cookiesMaps.get(cookieKey) + ";";
        }

        return result;
    }

}
