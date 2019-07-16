package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.HeilongjiangService;
import utils.GetContentUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 黑龙江省
 */
public class HeilongjiangThread implements Runnable {


    private HeilongjiangService service = new HeilongjiangService();

    String[] cities = {"双鸭山", "黑河", "绥化", "哈尔滨", "佳木斯", "牡丹江", "鸡西", "大兴安岭", "七台河"};

    @Override
    public void run() {
        try {
            for (String city : cities) {
                System.out.println(city);
                //for (int i = 1; i < 20; i++) {
                //    String url = "http://hljggzyjyw.gov.cn/trade/tradezfcg?cid=16&pageNo=" + i + "&type=1";
                String url = null;

                url = "http://www.hljggzyjyw.gov.cn/web/search?key=" + URLEncoder.encode((URLEncoder.encode(city, "utf-8")),"utf-8") + "&cid=16";

                List<Tender> list = service.getList(url, city);

                if (list.size() == 0) {
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

