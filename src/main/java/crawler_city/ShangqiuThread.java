package crawler_city;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.ShangqiuService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 商丘市
 */
public class ShangqiuThread implements Runnable {

    private ShangqiuService service = new ShangqiuService();

    String category = "[" +
            "{\"id\":\"1\",\"name\":\"建设工程\",\"catid\":1},{\"id\":\"2\",\"name\":\"交通工程\",\"catid\":8},{\"id\":\"3\",\"name\":\"水利工程\",\"catid\":2}]";
    
    JSONArray categoryJsonArr = JSONArray.parseArray(category);


    @Override
    public void run() {
        System.out.println("商丘市");
        String url = "http://www.sqggzy.com/spweb/HNSQ/TradeCenter/ColTableInfo.do?";
        for (int j = 0; j < categoryJsonArr.size(); j++) {
            JSONObject job = categoryJsonArr.getJSONObject(j);
            String category = job.get("name").toString();
            String type = "Deal_Type" + job.get("id");
            for (int i = 1; i < 20; i++) {
                String postBody = "projectName=&date=1month&begin_time=&end_time=&dealType=Deal_Type"+job.get("id")
                        + "&noticType=1+&area=&huanJie=NOTICE&pageIndex=" + i;
                List<Tender> list = service.getList(url, postBody,category, Integer.parseInt(job.get("catid").toString()),type);
                if (list.size() == 0) {
                    break;
                }
            }
        }
    }
}
