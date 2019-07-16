package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.FushunService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 抚顺市
 */
public class FushunThread implements Runnable {

    private FushunService service = new FushunService();

    @Override
    public void run() {
        System.out.println("抚顺市");
        for (int i = 1; i < 20; i++) {
            String url = "http://fsggzy.fushun.gov.cn/fsggzy/jyxx/071001/071001001/?Paging=" + i;
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
