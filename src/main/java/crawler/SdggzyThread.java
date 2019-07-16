package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.SdggzyService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 山东省公共资源交易
 */
public class SdggzyThread implements Runnable {

    private SdggzyService service = new SdggzyService();

    String[] citys = {"济南", "青岛", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照", "莱芜", "临沂", "德州", "聊城", "滨州", "菏泽"};

    @Override
    public void run() {
        for (String city : citys) {
            for (int i = 1; i <= 20; i++) {
                String url = "http://www.sdggzyjy.gov.cn/queryContent_" + i + "-jyxxgg.jspx?title=&origin="+city+"&inDates=&channelId=78&ext=";
                List<Tender> list = service.getList(url, city);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
