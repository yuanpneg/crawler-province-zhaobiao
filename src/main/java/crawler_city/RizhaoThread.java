package crawler_city;

import bean.Tender;
import services_city.RizhaoService;

import java.util.List;

/**
 * 日照市
 */
public class RizhaoThread implements Runnable {

    RizhaoService service = new RizhaoService();

    @Override
    public void run() {
        System.out.println("日照市");
        for(int i = 1; i < 20; i++){
            String url = "http://www.rzggzyjy.gov.cn/rzwz/ShowInfo/MoreJyxxList.aspx?categoryNum=071001001&Paging=" + i;
            List<Tender> list = service.getList(url);
            if (list.size() == 0) {
                break;
            }
        }
    }
}
