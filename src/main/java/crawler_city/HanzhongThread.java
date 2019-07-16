package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.HanzhongService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 汉中市
 */
public class HanzhongThread implements Runnable{

    private HanzhongService service = new HanzhongService();

    @Override
    public void run() {
        System.out.println("汉中市");
        String category = "";
        int catid = 0;
        for(int i = 1; i < 4; i++){
            if(i == 1){
                category= "建设工程";
                catid = 1;
            }else if(i == 2){
                category = "交通工程";
                catid = 8;
            }else if(i == 3){
                category = "水利工程";
                catid = 2;
            }
            for(int j = 1; j < 20; j++){
                String url = "http://www.hzzbb.com/zhaobiao.asp?fd=&lb="+i+"&page="+j;
                List<Tender>list = service.getList(url,category,catid);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
