package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.HegangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 鹤岗市
 */
public class HegangThread  implements  Runnable{

    private HegangService service = new HegangService();

    @Override
    public void run() {
        System.out.println("鹤岗市");
        for(int i = 1; i < 40; i++){
            String url = "http://www.hgggzyjyw.org.cn/gcjs/014001/"+i+".html";
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
