package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.YulinService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 玉林市
 */
public class YulinThread implements Runnable {

    private YulinService service = new YulinService();

    @Override
    public void run() {
        System.out.println("玉林市");
        for (int i = 1; i < 20; i++) {
            String url = "http://www.ylctc.com/zhaob.asp?pageNO=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
