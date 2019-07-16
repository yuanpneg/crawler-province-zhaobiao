package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.XianService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 西安市
 */
public class XianThread implements  Runnable{

    private XianService service = new XianService();

    @Override
    public void run() {
        System.out.println("西安市");
        for(int i = 1; i < 20; i++){
            String url = "http://www.xacin.com.cn/XianGcjy/web/tender/shed_gc.jsp?gc_type=5&page=" + i;
            List<Tender>list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
