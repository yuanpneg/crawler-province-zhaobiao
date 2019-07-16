package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import services_city.KashiService;
import utils.GetContentUtil;
import utils.JHttpUtils;
import utils.YHttpUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 喀什市
 */
public class KashiThread implements Runnable {

    private KashiService service = new KashiService();

    @Override
    public void run() {
        System.out.println("喀什市");
        String url = "http://ztb.xjjs.gov.cn/xjweb_KS/jyxx/004001/004001001/MoreInfo.aspx?CategoryNum=004001001";
        try {
            String __VIEWSTATE = "";
            FormBody body = null;
            String html = "";
            HttpUtils.ResponseWrap responseWrap = null;
            for (int i = 1; i < 20; i++) {
                if (i == 1) { //第一次get请求
                    Request request = new Request.Builder().headers(HttpUtils.getCommonHeaders()).url(url).build();
                    responseWrap = HttpUtils.retryHttpNoProxy(request, "gb2312");
                } else { //post请求
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    formBodyBuilder.add("__EVENTTARGET", "MoreInfoList1$Pager")
                            .add("__EVENTARGUMENT", String.valueOf(i))
                            .add("__VIEWSTATE", __VIEWSTATE);

                    body = formBodyBuilder.build();


                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .headers(HttpUtils.getCommonHeaders())
                            .header("Cookie","ASP.NET_SessionId=ptcuhj5551cejwno3stp2355")
                            .header("Referer","http://ztb.xjjs.gov.cn/xjweb_KS/jyxx/004001/004001001/MoreInfo.aspx?CategoryNum=004001001")
                            .url(url)
                            .post(body)
                            .build();

                    //responseWrap = HttpUtils.retryHttpNoProxy(request);

                }
                if (responseWrap.isSuccess()) {
                    html = responseWrap.body;
                    Document doc = Jsoup.parse(html);
                    __VIEWSTATE = doc.getElementById("__VIEWSTATE").attr("value");
                }

                List<Tender> list = service.getList(html);
                if(list.size() == 0){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
