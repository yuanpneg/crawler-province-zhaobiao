package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.LishuiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 丽水市
 */
public class LishuiThread implements  Runnable{

    private LishuiService service = new LishuiService();

    @Override
    public void run() {
        System.out.println("丽水");
        for(int i = 1001; i <= 1010; i++ ){
            for(int j = 1; j < 2; j++){
                String url = "http://www.lssggzy.com/lsweb/jyxx/071001/071001001/07100100"+i+"/"+"?Paging="+j;
                List<Tender> list = service.getList(url);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
