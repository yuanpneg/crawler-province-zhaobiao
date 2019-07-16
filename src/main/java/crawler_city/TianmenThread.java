package crawler_city;

import bean.Tender;
import services_city.TianmenService;

import java.util.List;

/**
 * 天门市
 */
public class TianmenThread implements Runnable{

    private TianmenService service = new TianmenService();

    @Override
    public void run() {
        for(int i = 1; i < 20; i++ ){
            String url = "http://ztb.tianmen.gov.cn/Content/jsgc?pageIndex=" + i;
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
