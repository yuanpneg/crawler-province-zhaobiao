package crawler_city;

import bean.Tender;

import okhttp3.FormBody;
import services_city.BoertalaService;

import java.util.List;

/**
 * 博尔塔拉
 */
public class BoertalaThread implements Runnable {

    private BoertalaService service = new BoertalaService();

    @Override
    public void run() {
        System.out.println("博尔塔拉市");
        String url = "http://xzfw.xjboz.gov.cn/btc_web/articleWeb!list.action";
        for (int i = 0; i <= 300; i = i + 15) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("startIndex", String.valueOf(i))
                    .add("resourceCode", "jszbgg")
                    .add("type", "js");
            FormBody body = formBodyBuilder.build();
            List<Tender>list =service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
