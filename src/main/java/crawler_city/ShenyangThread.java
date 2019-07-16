package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.ShenyangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 沈阳市
 */
public class ShenyangThread implements Runnable {

    private ShenyangService service = new ShenyangService();

    @Override
    public void run() {
        System.out.println("沈阳市");
        for (int i = 1; i < 10; i++) {
            String url = "http://www.syjy.gov.cn/queryContent_"+i+"-jyxx.jspx";
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("title","")
                    .add("inDates","")
                    .add("origin","")
                    .add("channelId","82");
            FormBody body = formBodyBuilder.build();
            List<Tender>list = service.getList(url,body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
