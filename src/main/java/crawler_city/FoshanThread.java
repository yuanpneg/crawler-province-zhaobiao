package crawler_city;

import bean.Tender;
import services_city.FoshanService;

import java.util.List;

/**
 * 佛山市
 */
public class FoshanThread implements Runnable{

    FoshanService service = new FoshanService();

    @Override
    public void run() {
        System.out.println("佛山市");
        for(int i = 0; i < 20; i++){
            String url = "";
            if( i == 0){
                url = "http://zgb.fspc.gov.cn/ywbl/gcjygg/index.html";
            }else {
                url = "http://zgb.fspc.gov.cn/ywbl/gcjygg/index_" + (i+1) + ".html";
            }
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
