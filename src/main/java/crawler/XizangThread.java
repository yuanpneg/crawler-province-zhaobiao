package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.XizangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 西藏省
 */
public class XizangThread implements Runnable{


    private XizangService service = new XizangService();

    @Override
    public void run() {
        for(int i = 1; i < 20; i++){
            String url = "http://www.xzggzy.gov.cn:9090/queryContent_"+i+"-jyxx.jspx?title=&origin=&inDates=&channelId=85&ext=&beginTime=&endTime=";
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
