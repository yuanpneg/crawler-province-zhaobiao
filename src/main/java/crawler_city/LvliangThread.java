package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.LvliangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 吕梁市
 */
public class LvliangThread implements Runnable{

    private LvliangService service = new LvliangService();

    @Override
    public void run() {
        System.out.println("吕梁市");
        for(int i= 0; i < 20; i++){
            String url = "http://ggzyjyzx.lvliang.gov.cn/gcjsZbgg/index_"+i+".htm";
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
