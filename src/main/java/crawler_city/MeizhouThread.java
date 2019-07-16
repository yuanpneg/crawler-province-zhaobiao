package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.MeizhouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 梅州市
 */
public class MeizhouThread implements  Runnable{

    private MeizhouService service = new MeizhouService();

    @Override
    public void run() {
        System.out.println("梅州市");
        for(int i = 1; i < 20; i++){
            String url = "http://mzggzy.meizhou.gov.cn/TPFront/jsgc/004001/Default.aspx?Paging=" + i;
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
