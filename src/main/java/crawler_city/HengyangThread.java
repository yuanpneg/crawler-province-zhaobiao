package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.HengyangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 衡阳市
 */
public class HengyangThread implements Runnable{

    private HengyangService service = new HengyangService();

    @Override
    public void run() {
        System.out.println("衡阳市");
        for(int i = 0; i < 20; i++ ){
            String url = "";
            if(i == 0) {
                url = "http://ggzy.hengyang.gov.cn/jyxx/jsgc/zbgg_64796/index.html";
            }else{
                url = "http://ggzy.hengyang.gov.cn/jyxx/jsgc/zbgg_64796/index_"+i+".html";
            }
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
