package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.ZhejiangService;
import services_city.ZhuhaiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 珠海市
 */
public class ZhuhaiThread implements Runnable{

    private ZhuhaiService service = new ZhuhaiService();

    @Override
    public void run() {
        System.out.println("珠海市");
        for(int i = 1; i < 20; i++){
            String url = "http://ggzy.zhuhai.gov.cn/zbgg/index_"+i+".jhtml";
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
