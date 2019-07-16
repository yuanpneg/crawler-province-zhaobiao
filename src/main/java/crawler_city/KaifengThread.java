package crawler_city;

import bean.Tender;
import services_city.KaifengService;

import java.util.List;

/**
 * 开封市
 */
public class KaifengThread implements Runnable{

    private KaifengService service = new KaifengService();

    @Override
    public void run() {
        System.out.println("开封市");
        for(int i = 1; i < 20; i++){
            String url = "http://www.kfsggzyjyw.cn/jzbgg/index_"+i+".jhtml";
            List<Tender> list =service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
