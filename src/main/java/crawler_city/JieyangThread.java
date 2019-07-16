package crawler_city;

import bean.Tender;
import services_city.JieyangService;

import java.util.List;

/**
 * 揭阳市
 */
public class JieyangThread implements Runnable{

    private JieyangService service = new JieyangService();

    @Override
    public void run() {
        for(int i = 1; i < 20; i++ ){
            String url = "http://www.jysggzy.com/TPFront/jsgc/004001/?pageing=" + i;
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
