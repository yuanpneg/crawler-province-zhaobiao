package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.XianyangService;
import sun.dc.pr.PRError;
import utils.GetContentUtil;

import java.util.List;

/**
 * 咸阳市
 */
public class XianyangThread implements  Runnable{

    private XianyangService service = new XianyangService();

    @Override
    public void run() {
        System.out.println("咸阳市");
        for(int i = 1; i < 3; i++){
            String url = "http://xy.sxggzyjy.cn/jydt/001001/001001001/001001001001/" +
                    i +".html";
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }

    }
}
