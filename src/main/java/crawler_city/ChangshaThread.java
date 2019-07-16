package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.ChangshaService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 长沙市
 */
public class ChangshaThread implements  Runnable{

    private ChangshaService service = new ChangshaService();

    @Override
    public void run() {
        System.out.println("长沙市");
        String category = "";
        int catid = 0;
        String url = "https://fwpt.csggzy.cn/spweb/CS/TradeCenter/ColTableInfo.do?";

        for(int i = 1; i <= 3; i++){
            if(i == 1){
                category = "建设工程";
                catid = 1;
            }else  if(i == 2){
                category = "交通工程";
                catid = 8;
            }else  if(i == 3){
                category = "水利工程";
                catid = 2;
            }
            for(int j = 1; j < 20; j++ ){
                String postBody = "projectName=&date=1month&begin_time=&end_time=&dealType=Deal_Type"+i
                        +"&noticType=1+&area=&huanJie=NOTICE&pageIndex="+j;

                List<Tender>list = service.getList(url,postBody,category,catid);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
