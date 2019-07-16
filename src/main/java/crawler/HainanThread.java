package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import services.HainanService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 海南省
 */
public class HainanThread implements Runnable {


    private HainanService service = new HainanService();

    String cityJson = "[" +
            "{\"id\":\"HKS\",\"name\":\"海口\"},{\"id\":\"SYS\",\"name\":\"三亚\"}," +
            "{\"id\":\"SSS\",\"name\":\"三沙\"},{\"id\":\"DZS\",\"name\":\"儋州\"}," +
            "{\"id\":\"WZSS\",\"name\":\"五指山市\"},{\"id\":\"QHS\",\"name\":\"琼海\"}," +
            "{\"id\":\"WCS\",\"name\":\"文昌\"},{\"id\":\"WNS\",\"name\":\"万宁\"}," +
            "{\"id\":\"DFS\",\"name\":\"东方\"},{\"id\":\"DAS\",\"name\":\"定安\"}," +
            "{\"id\":\"TCS\",\"name\":\"屯昌\"},{\"id\":\"CMS\",\"name\":\"澄迈\"}," +
            "{\"id\":\"LGS\",\"name\":\"临高\"},{\"id\":\"BSS\",\"name\":\"白沙\"}," +
            "{\"id\":\"CJS\",\"name\":\"昌江\"},{\"id\":\"LDS\",\"name\":\"乐东\"}," +
            "{\"id\":\"LSS\",\"name\":\"陵水\"},{\"id\":\"BTS\",\"name\":\"保亭\"}," +
            "{\"id\":\"QZS\",\"name\":\"琼中\"}" +
            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name").toString());
            for (int j = 1; j < 20; j++) {
                String url = "http://zw.hainan.gov.cn/ggzy/ggzy/jgzbgg/index_" +
                        j +
                        ".jhtml?channelId=234&origin=" +
                        job.get("id") +
                        "&q=";
                List<Tender> list = service.getList(url, job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }


    }
}
