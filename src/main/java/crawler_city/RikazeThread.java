package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.RikazeService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 日喀则
 */
public class RikazeThread implements Runnable{


    private RikazeService service = new RikazeService();

    @Override
    public void run() {
        System.out.println("日喀则");
        for(int i = 1; i < 10; i++){
            String url = "http://ggzyjy.rkzszf.gov.cn/col/col910/index.html?uid=4015&pageNum=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
