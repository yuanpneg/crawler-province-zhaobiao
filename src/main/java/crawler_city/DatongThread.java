package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.DatongService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 大同市
 */
public class DatongThread implements Runnable {

    private DatongService service = new DatongService();

    @Override
    public void run() {
        System.out.println("大同市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.dtggzy.gov.cn/zyjyPortal/portal/noticelist?category=" +
                    "%E5%BB%BA%E8%AE%BE%E5%B7%A5%E7%A8%8B&type&other&isFb=9&page=" +
                    i + "&rows=10";
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
