package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ShuozhouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 朔州市
 */
public class ShuozhouThread implements Runnable{

    private ShuozhouService  service = new ShuozhouService();

    @Override
    public void run() {
        System.out.println("朔州市");
        for(int i=1; i < 20; i++){
            String url = "http://szggzy.shuozhou.gov.cn/moreInfoController.do?getMoreNoticeInfo&page="+i
                    +"&rows=10&dateFlag=1month&tableName=gcjs&projectRegion=&projectName=&beginReceivetime=&endReceivetime=";
            List<Tender> list =service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
