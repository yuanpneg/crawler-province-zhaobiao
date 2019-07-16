package crawler_city;

import bean.Tender;
import services_city.QingdaoService;

import java.util.List;

/**
 * 青岛市
 */
public class QingdaoThread implements Runnable{

    private QingdaoService service = new QingdaoService();


    @Override
    public void run() {
        for(int i = 1; i < 5 ;i++){
            String url = "https://ggzy.qingdao.gov.cn/Tradeinfo-GGGSList/0-0-0?pageIndex="+i;
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
