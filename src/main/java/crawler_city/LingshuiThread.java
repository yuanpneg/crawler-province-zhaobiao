package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.LingshuiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 陵水县
 */
public class LingshuiThread implements Runnable{

    private LingshuiService service = new LingshuiService();

    @Override
    public void run() {
        System.out.println("陵水县");
        for(int i = 1; i < 20; i++){
            String url = "http://zw.hainan.gov.cn/ggzy/lsggzy/GGjxzbgs1/index_"+i+".jhtml";
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }

    }
}
