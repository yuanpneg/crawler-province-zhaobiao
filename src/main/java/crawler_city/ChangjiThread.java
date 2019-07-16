package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ChangjiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 昌吉市
 */
public class ChangjiThread implements Runnable {

    private ChangjiService service = new ChangjiService();

    @Override
    public void run() {
        System.out.println("昌吉市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.cjggzy.cn/main/004/004001/004001001/" + i + ".html";
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }

    }
}
