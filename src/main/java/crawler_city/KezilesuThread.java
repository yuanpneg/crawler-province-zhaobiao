package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import org.apache.xpath.SourceTree;
import services_city.KezilesuService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 克孜勒苏市
 */
public class KezilesuThread implements Runnable{

    private KezilesuService service = new KezilesuService();

    @Override
    public void run() {
        System.out.println("克孜勒苏市");
        for(int i = 1; i <= 5; i++){
            String url = "http://www.kzggzyjy.com.cn/kzweb/jyxx/021001/021001001/02100100100"+i+"/MoreInfo.aspx?CategoryNum=02100100100"+i;
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
