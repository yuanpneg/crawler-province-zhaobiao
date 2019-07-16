package crawler_city;

import bean.Tender;
import services_city.AnshunService;

import java.util.List;

/**
 * 安顺市
 */
public class AnshunThread implements Runnable{

    AnshunService service = new AnshunService();

    @Override
    public void run() {
        for(int i = 1; i < 20; i++){
            String url = "http://ggzy.anshun.gov.cn/jyxx/003001/003001001/"+i+".html";
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
