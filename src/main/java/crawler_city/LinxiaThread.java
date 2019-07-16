package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.LinxiaService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 临夏州
 */
public class LinxiaThread implements Runnable {

    private LinxiaService service = new LinxiaService();

    @Override
    public void run() {
        System.out.println("临夏州");
        String url = "http://www.lxggzyjy.com/f/trade/annogoods/getAnnoList";
        for (int i = 1; i < 20; i++) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("pageNo", String.valueOf(i))
                    .add("pageSize", "20")
                    .add("prjpropertycode", "A")
                    .add("tradeArea", "3025")
                    .add("tradeArea", "3026")
                    .add("tradeArea", "3027")
                    .add("tradeArea", "3028")
                    .add("tradeArea", "3029")
                    .add("tradeArea", "3030")
                    .add("tradeArea", "3031")
                    .add("tradeArea", "3032")
                    .add("projectname", "")
                    .add("tabType", "");
            FormBody body = formBodyBuilder.build();
            List<Tender>list = service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
