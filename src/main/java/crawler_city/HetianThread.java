package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.HetianService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 和田市
 */
public class HetianThread implements Runnable{

    private HetianService service = new HetianService();

    @Override
    public void run() {
        System.out.println("和田市");
        for(int i = 1; i < 20; i++){
            String url = "https://www.xjht.gov.cn/article/list.php?catid=25&page=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
