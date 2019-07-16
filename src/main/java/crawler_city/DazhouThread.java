package crawler_city;

import bean.Tender;
import services_city.DazhouService;

import java.util.List;

/**
 * 达州市
 */
public class DazhouThread implements Runnable{

    DazhouService service = new DazhouService();

    @Override
    public void run() {
        for(int i = 1; i < 20; i++){
            String url = "http://www.dzggzy.cn/dzsggzy/jyxx/025001/025001001/?Paging=" + i;
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
