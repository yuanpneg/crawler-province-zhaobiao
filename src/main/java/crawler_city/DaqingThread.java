package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.DaqingService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 大庆市
 */
public class DaqingThread implements Runnable {

    private DaqingService service = new DaqingService();

    @Override
    public void run() {
        System.out.println("大庆市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.dqgpc.gov.cn/jyxxJsgcZbgg/index_" + i + ".htm";
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
