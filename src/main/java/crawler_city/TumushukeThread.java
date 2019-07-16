package crawler_city;


import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.TumushukeService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 图木舒克 （新疆生产建设兵团第三师）
 */
public class TumushukeThread implements Runnable{

    private TumushukeService service = new TumushukeService();

    @Override
    public void run() {
        System.out.println("图木舒克市");
        for(int i = 1; i < 20; i++){
            String url = "http://ggzy.xjbt.gov.cn/TPFront/bt3/081003/081003001/081003001001/?Paging=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
