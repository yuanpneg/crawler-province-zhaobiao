package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import services.GuizhouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 贵州省
 */
public class GuizhouThread implements Runnable {

    private GuizhouService service = new GuizhouService();

    String cityJson = "[" +
            "{\"id\":\"520100\",\"name\":\"贵阳市\"},{\"id\":\"520200\",\"name\":\"六盘水市\"}," +
            "{\"id\":\"523000\",\"name\":\"遵义市\"},{\"id\":\"520400\",\"name\":\"安顺市\"}," +
            "{\"id\":\"522401\",\"name\":\"毕节市\"},{\"id\":\"522201\",\"name\":\"铜仁市\"}," +
            "{\"id\":\"522300\",\"name\":\"黔西南\"},{\"id\":\"522600\",\"name\":\"黔东南\"},"+
            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {

        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name").toString());
            for (int j = 1; j < 60; j++) {
                String url = "http://www.gzjyfw.gov.cn/G2/project/project-trade.do?pageSize=4&current="+j+"&projectName=&type=CE&regionCode=S"+job.get("id")+
                        "&publishTime=1month&transactionType=&transactionStage= ";
                List<Tender> list = service.getList(url,job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
