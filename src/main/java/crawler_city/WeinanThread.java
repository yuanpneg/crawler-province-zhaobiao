package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.WeinanService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 渭南市
 */
public class WeinanThread implements Runnable {

    private WeinanService service = new WeinanService();

    @Override
    public void run() {
        System.out.println("渭南市");
        for (int i = 2001006; i <= 2001008; i++) {
            String category = "";
            int catid = 0;
            for (int j = 1; j < 20; j++) {
                if (2001006 == i) {
                    category = "建设工程";
                    catid = 1;
                }else if(2001007 == i){
                    category = "交通工程";
                    catid = 8;
                }else if(2001008 == i){
                    category = "水利工程";
                    catid = 2;
                }
                String url = "http://www.wnggzy.com/wnggzyweb/jyxx/002001/00" + i + "/00" + i + "001/?Paging=" + j;
                List<Tender>list = service.getList(url,category,catid);
                if(list.size() == 0){
                    break;
                }
            }
        }

    }
}
