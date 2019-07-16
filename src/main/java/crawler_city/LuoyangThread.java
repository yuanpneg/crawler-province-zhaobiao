package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.LuoyangService;

import java.util.List;

/**
 * 洛阳市
 */
public class LuoyangThread implements Runnable {

    private BaseDao baseDao = new BaseDao();

    private FormalDao formalDao = new FormalDao();

    private LuoyangService service = new LuoyangService();

    @Override
    public void run() {
        System.out.println("洛阳市");
        for (int i = 1001; i <= 1010; i++) {
            for(int j =1; j < 10; j++){
                String url = "http://www.lyggzyjy.cn/TPFront/jyxx/009001/009001001/00900100"+i+"/?Paging="+j;
                List<Tender>list = service.getList(url);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
