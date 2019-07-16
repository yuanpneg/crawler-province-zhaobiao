package crawler_city;

import bean.Tender;
import services_city.ZaozhuangService;

import java.util.List;

/**
 * 枣庄市
 */
public class ZaozhuangThread implements Runnable {

    ZaozhuangService service = new ZaozhuangService();

    @Override
    public void run() {
        System.out.println("枣庄市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.zzggzy.com/TPFront/jyxx/070001/070001001/?Paging=" + i;
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
