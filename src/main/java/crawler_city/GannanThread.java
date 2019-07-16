package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.GannanService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 甘南
 */
public class GannanThread implements Runnable {

    private GannanService service = new GannanService();

    @Override
    public void run() {
        System.out.println("甘南市");
        String url = "http://www.gnggzyjy.gov.cn/f/trade/annogoods/getAnnoList";
        for (int i = 1; i < 20; i++) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("pageNo", String.valueOf(i))
                    .add("pageSize", "20")
                    .add("prjpropertycode", "A01")
                    .add("tradeArea", "623000")
                    .add("tradeArea", "1")
                    .add("tradeArea", "2")
                    .add("tradeArea", "3")
                    .add("tradeArea", "4")
                    .add("tradeArea", "5")
                    .add("tradeArea", "6")
                    .add("tradeArea", "7")
                    .add("tradeArea", "8")
                    .add("projectname", "")
                    .add("tabType", "1");
            FormBody body = formBodyBuilder.build();
            List<Tender> list = service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
