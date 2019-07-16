package crawler_city;

import bean.Tender;
import services_city.YangzhouService;

import java.util.List;

/**
 * 扬州市
 */
public class YangzhouThread implements Runnable{

    YangzhouService service = new YangzhouService();

    @Override
    public void run() {
        System.out.println("扬州市");
        for(int i = 1; i < 20; i++){
            String url = "http://ggzyjyzx.yangzhou.gov.cn/qtyy/ggzyjyzx/right_list/right_list_jsgc.jsp?currentPage="+i+"&categorynum=003007";
            List<Tender> list =service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
