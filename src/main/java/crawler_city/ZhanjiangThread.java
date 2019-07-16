package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ZhanjiangService;
import services_city.ZhaoqingService;
import utils.GetContentUtil;

import java.util.Date;
import java.util.List;

/**
 * 湛江市
 */
public class ZhanjiangThread implements Runnable {

    private ZhanjiangService service = new ZhanjiangService();

    @Override
    public void run() {
        System.out.println("湛江市");
        for (int i = 1; i < 20; i++) {
            long time = new Date().getTime();
            String url = "http://ggfw.zjprtc.com/jsgc_Message/queryPaginationBysearchText.do?areaType=9&baoJianLeiXin=&page=" + i +
                    "&rows=15&_=" + (time / 1000);
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
