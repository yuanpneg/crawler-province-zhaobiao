package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.YichunService;
import utils.GetContentUtil;

import java.util.List;

/**
 *伊春市
 */
public class YichunThread implements Runnable{

    private YichunService service = new YichunService();

    @Override
    public void run() {
        System.out.println("伊春市");
        for(int i = 1; i < 20; i++){
            String url = "http://ggzy.yc.gov.cn/docweb/docList.action?channelId=2117&parentChannelId=-1&pageNo="+i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
