package crawler_city;


import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.BaotouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 包头市
 */
public class BaotouThread implements Runnable {

    private BaotouService service = new BaotouService();

    @Override
    public void run() {
        System.out.println("包头市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.btggzyjy.cn/jygk/007001/007001001/" + i + ".html";
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
