package crawler_city;


import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.LinyiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 临沂市
 */
public class LinyiThread implements Runnable {

    private LinyiService service = new LinyiService();

    @Override
    public void run() {
        for (int i = 1; i <= 25; i++) {
            String url = "http://ggzyjy.linyi.gov.cn/TPFront/jyxx/074001/074001001/074001001001/?Paging=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
