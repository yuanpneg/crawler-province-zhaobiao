package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.LoudiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 娄底市
 */
public class LoudiThread implements Runnable{

    private LoudiService service = new LoudiService();

    @Override
    public void run() {
        System.out.println("娄底市");
        for(int i= 1; i < 20; i++){
            String url = "http://ldggzy.hnloudi.gov.cn/Trading/main/GetListJson.aspx";
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("page", String.valueOf(i))
                    .add("limit", "10")
                    .add("id", "1")
                    .add("type", "");
            FormBody body = formBodyBuilder.build();
            List<Tender>list = service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
