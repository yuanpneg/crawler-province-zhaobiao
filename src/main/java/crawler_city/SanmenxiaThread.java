package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.SanmenxiaService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 三门峡市
 */
public class SanmenxiaThread implements Runnable{

    private SanmenxiaService service = new SanmenxiaService();


    @Override
    public void run() {
        System.out.println("三门峡市");
        for(int i = 1; i < 20; i++){
            String url = "http://www.smxgzjy.org/gjsgc/index_"+i+".jhtml";
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
