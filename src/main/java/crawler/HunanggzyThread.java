package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.HunanService;
import utils.GetContentUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 湖南省公共资源交易平台
 */
public class HunanggzyThread implements Runnable {

    private HunanService service = new HunanService();

    String[] citys = {"长沙", "株洲", "湘潭", "衡阳", "邵阳", "岳阳", "常德", "张家界", "益阳", "娄底", "郴州", "永州", "怀化", "湘西"};
    //848 房屋市政工程  849水利工程  850交通工程
    @Override
    public void run() {
        for (String city : citys) {
            for (int j = 848; j <= 850; j++) {
                String category = null;
                if (848 == j) {
                    category = "建设工程";
                } else if (849 == j) {
                    category = "水利工程";
                } else if (850 == j) {
                    category = "交通工程";
                }

                for (int i = 1; i <= 20; i++) {
                    String url = null;
                    try {
                        url = "http://www.hnsggzy.com/queryContent_" + i + "-jygk.jspx?title=&origin=" + URLEncoder.encode(city, "utf-8") +
                                "&inDates=&channelId=" + j + "&ext=%E6%8B%9B%E6%A0%87/%E8%B5%84%E5%AE%A1%E5%85%AC%E5%91%8A&beginTime=&endTime=";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    List<Tender> list = service.getList(url, category, city);
                    if(list.size() == 0){
                        break;
                    }
                }
            }
        }

    }
}
