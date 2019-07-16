package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import services_city.XuzhouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 徐州市
 */
public class XuzhouThread implements Runnable {


    private XuzhouService service = new XuzhouService();


    //String[] categoryNum = {"046001001", "046001002", "046001003", "046001004", "046001005"};

    @Override
    public void run() {
        System.out.println("徐州市");
        for (int page = 1; page <=  10 ; page++) {
            String url = "http://www.xzggzy.com.cn/services/XzsZtmkWebservice/getList?response=application/json&pageIndex=" + page +
                    "&pageSize=15&categorynum=003001001&xmbh=&xmmc=";
            List<Tender> list =service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
