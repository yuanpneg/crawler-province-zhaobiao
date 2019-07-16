package crawler;

import bean.Tender;
import services.TianjinService;

import java.util.List;

/**
 * 天津市
 */
public class TianjinThread implements Runnable{

    TianjinService service = new TianjinService();

    @Override
    public void run() {
        for(int i = 1; i < 20 ; i++){
            String url = "http://ggzy.xzsp.tj.gov.cn/queryContent_"+i+
                    "-jyxx.jspx?title=&inDates=&ext=&ext1=&origin=&channelId=81&beginTime=&endTime=";
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
