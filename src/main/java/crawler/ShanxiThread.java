package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.ShanxiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 陕西省
 */
public class ShanxiThread implements Runnable {

    private ShanxiService service = new ShanxiService();

    String[] cities = {"榆林", "商洛", "铜川", "宝鸡"};

    @Override
    public void run() {
        for (String city : cities) {
            System.out.println(city);
            String url = "";
            String urlFirst = "";
            for (int i = 1; i < 3; i++) {
                if("榆林".equals(city)) {
                    url = "http://yl.sxggzyjy.cn/jydt/001001/001001001/001001001001/" + i + ".html";
                    urlFirst = "http://yl.sxggzyjy.cn";
                }else{
                    url = "http://wn.sxggzyjy.cn/jydt/001001/001001001/001001001001/" + i + ".html";
                    urlFirst = "http://wn.sxggzyjy.cn";
                }
                List<Tender> list =service.getList(url,city,urlFirst);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
