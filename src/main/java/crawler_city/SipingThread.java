package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.SipingService;
import utils.GetContentUtil;

import java.util.List;


/**
 * 四平市
 */
public class SipingThread implements Runnable {

    private SipingService service = new SipingService();

    @Override
    public void run() {
        System.out.println("四平市");
        for (int i = 1; i < 20; i++) {
            String url = "http://ggzy.siping.gov.cn/jyxx/004001/004001001/004001001001/"+i+".html";
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
