package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.YulinService;
import services_city.YunchengService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 运城市
 */
public class YunchengThread implements  Runnable{

    private YunchengService service = new YunchengService();


    @Override
    public void run() {
        System.out.println("运城市");
        for(int i =1; i < 20; i++){

            String url = "http://www.ycggfw.gov.cn/TPFront/jyxx/005001/005001001/?Paging=" + i;

            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
