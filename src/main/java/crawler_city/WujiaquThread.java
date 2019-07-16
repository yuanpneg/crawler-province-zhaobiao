package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.WujiaquService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 五家渠市
 */
public class WujiaquThread implements Runnable{

    private WujiaquService service = new WujiaquService();

    @Override
    public void run() {
        System.out.println("五家渠");
        for(int i = 1; i < 20; i++){
            String url = "http://ggzy.xjbt.gov.cn/TPFront/bt6/084003/084003001/084003001001/?Paging=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
