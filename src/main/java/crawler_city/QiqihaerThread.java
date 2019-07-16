package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.QiqihaerService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 齐齐哈尔市
 */
public class QiqihaerThread implements Runnable {

    private QiqihaerService service = new QiqihaerService();

    @Override
    public void run() {
        System.out.println("齐齐哈尔");
        for (int i = 1; i < 40; i++) {
            String url = "";
            if (i == 1) {
                url = "http://ggzy.qqhr.gov.cn/jyxx/003001/003001001/about.html";

            } else {
                url = "http://ggzy.qqhrggzy.cn/jyxx/003001/003001001/" + i + ".html";
            }
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
