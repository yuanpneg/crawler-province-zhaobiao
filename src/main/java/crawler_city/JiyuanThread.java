package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.JiyuanService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 济源市
 */
public class JiyuanThread implements Runnable {

    private JiyuanService service = new JiyuanService();

    @Override
    public void run() {
        System.out.println("济源市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.jyggjy.cn/TPFront/jyxx/005002/005002001/?Paging=" + i;
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
