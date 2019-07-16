package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.LinfenService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 临汾市
 */
public class LinfenThread implements Runnable{

    private LinfenService service = new LinfenService();

    @Override
    public void run() {
        System.out.println("临汾");
        for(int i = 1; i < 20; i++){
            String url = "http://www.lfggzyjy.gov.cn/notice/list/1-0-1-"+i+".html";
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
