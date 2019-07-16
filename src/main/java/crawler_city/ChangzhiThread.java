package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ChangzhiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 长治市
 */
public class ChangzhiThread implements Runnable {

    private ChangzhiService service = new ChangzhiService();

    @Override
    public void run() {
        System.out.println("长治市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.czsggzy.gov.cn/engconstTender/index_" + i + ".htm";
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
