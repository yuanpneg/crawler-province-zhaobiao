package crawler_city;


import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ShantouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 汕头市
 */
public class ShantouThread implements  Runnable{

    private ShantouService service = new ShantouService();

    @Override
    public void run() {
        System.out.println("汕头市");
        String url = "";
        for (int i = 1; i < 20; i++){
            if(i == 1){
                url = "http://www.shantou.gov.cn/ggzyjy/jsbggg/list.shtml";
            }else {
                url = "http://www.shantou.gov.cn/ggzyjy/jsbggg/list_" + i + ".shtml";
            }
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
