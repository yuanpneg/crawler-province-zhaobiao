package crawler_city;

import bean.Tender;
import services_city.YangjiangService;

import java.util.List;


public class YangjiangThread implements Runnable{
    YangjiangService service = new YangjiangService();
    @Override
    public void run() {
        System.out.println("阳江市");
        for(int i = 1; i < 2; i++){
            String url = "http://www.yjggzy.cn/Query/JsgcBidAfficheQuery2/d4f193435ad04447a997719474139181?page="+i;
            List<Tender> list =service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
