package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ZhongshanService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 中山市
 */
public class ZhongshanThread implements Runnable{

    private ZhongshanService service = new ZhongshanService();

    @Override
    public void run() {
        System.out.println("中山市");
        for(int i = 1; i < 20; i++){
            String url = "http://www.zsjyzx.gov.cn/Application/NewPage/PageSubItem.jsp?page="+i+"&node=58";
            List<Tender>list =service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
