package crawler;

import bean.Tender;
import services.BeijingService;

import java.util.List;

/**
 * 北京市
 */
public class BeijingThread implements Runnable {

    private BeijingService service = new BeijingService();

    @Override
    public void run() {
        for (int i = 1; i < 20; i++) {
            String url = "";
            if(i == 1) {
                url = "https://www.bjggzyfw.gov.cn/cmsbj/jylcgcjs/index.html";
            }else {
                url = "https://www.bjggzyfw.gov.cn/cmsbj/jylcgcjs/index_" + i + ".html";
            }
            List<Tender> list =service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
