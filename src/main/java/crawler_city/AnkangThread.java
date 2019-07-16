package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import services_city.AnkangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 安康市
 */
public class AnkangThread implements Runnable{

    private AnkangService service = new AnkangService();

    @Override
    public void run() {
        try {
            System.out.println("安康市");
            String url = "http://zbjy.ankang.cn/GongCheng.aspx?Pid=2&Xid=5";
            String __VIEWSTATE = "";
            String __EVENTVALIDATION= "";
            FormBody body = null;
            String html = "";
            HttpUtils.ResponseWrap responseWrap = null;
            for (int i = 1; i < 20; i++) {
                if(i == 1){ //第一次get请求
                    Request request = new Request.Builder().headers(HttpUtils.getCommonHeaders())
                            .header("Referer","http://zbjy.ankang.cn/GongCheng.aspx?Pid=2&Xid=5").url(url).build();
                    responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
                }else { //post请求
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    formBodyBuilder.add("__VIEWSTATEGENERATOR", "EE1DB682")
                            .add("__EVENTARGUMENT", "" + i)
                            .add("__VIEWSTATE", __VIEWSTATE)
                            .add("__EVENTTARGET", "AspNetPager1")
                            .add("__EVENTVALIDATION", __EVENTVALIDATION);
                    body = formBodyBuilder.build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .headers(HttpUtils.getCommonHeaders())
                            .url(url)
                            .post(body)
                            .build();
                    responseWrap = HttpUtils.retryHttpNoProxy(request,"utf-8");

                }
                if(responseWrap.isSuccess()){
                    html = responseWrap.body;
                    Document doc = Jsoup.parse(html);
                    __VIEWSTATE = doc.getElementById("__VIEWSTATE").attr("value");
                    __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION").attr("value");
                }

                List<Tender>list=service.getList(html, body);
                if(list.size() == 0){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
