package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ZhaoqingService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 肇庆市
 */
public class ZhaoqingThread implements  Runnable{

    private ZhaoqingService service = new ZhaoqingService();

    @Override
    public void run() {
        System.out.println("肇庆市");
        for(int i = 1; i < 20; i++ ){
            String url = "http://ggzy.zhaoqing.gov.cn/zqfront/showinfo/moreinfolist.aspx?categorynum=003001001&Paging=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
