package crawler_city;


import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.YiyangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 益阳市
 */
public class YiyangThread implements Runnable {

    private YiyangService service = new YiyangService();

    @Override
    public void run() {
        System.out.println("益阳市");
        String category = "";
        int catid = 0;
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                category = "建设工程";
                catid = 1;
            } else if (i == 2) {
                category = "水利工程";
                catid = 2;
            } else if (i == 3) {
                category = "交通工程";
                catid = 8;
            }
            for (int j  = 1;j < 20; j++) {
                String url = "http://jyzx.yiyang.gov.cn/jyxx/003001/00300100"+i+"/" + j + ".html";
                List<Tender> list = service.getList(url,category,catid);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
