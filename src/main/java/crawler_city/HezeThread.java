package crawler_city;


import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import services_city.HezeService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 菏泽
 */
public class HezeThread implements Runnable{

    private HezeService service = new HezeService();

    @Override
    public void run() {
        System.out.println("菏泽市");
        String url = "http://www.hzsggzyjy.gov.cn/cityInfoList.aspx?s=1&t=1";
        try {
            String __VIEWSTATE = "";
            String __EVENTVALIDATION= "";
            FormBody body = null;
            String html = "";
            HttpUtils.ResponseWrap responseWrap = null;
            for (int i = 1; i < 20; i++) {
                if(i == 1){ //第一次get请求
                    Request request = new Request.Builder().headers(HttpUtils.getCommonHeaders()).url(url).build();
                    responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
                }else { //post请求
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    formBodyBuilder.add("__EVENTTARGET", "ctl00$ContentPlaceHolder1$lbtnDown")
                            .add("__EVENTARGUMENT", "")
                            .add("__VIEWSTATE", __VIEWSTATE)
                            .add("__EVENTVALIDATION", __EVENTVALIDATION);
                    body = formBodyBuilder.build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .headers(HttpUtils.getCommonHeaders())
                            .url(url)
                            .post(body)
                            .build();
                    responseWrap = HttpUtils.retryHttpNoProxy(request);

                }
                if(responseWrap.isSuccess()){
                    html = responseWrap.body;
                    Document doc = Jsoup.parse(html);
                    __VIEWSTATE = doc.getElementById("__VIEWSTATE").attr("value");
                    __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION").attr("value");
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
