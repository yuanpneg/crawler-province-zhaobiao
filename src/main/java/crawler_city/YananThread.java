package crawler_city;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import services_city.YananService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 延安市
 */
public class YananThread implements  Runnable{

    private YananService service = new YananService();

    String categoryJson = "[" +
            "{\"id\":\"004001001\",\"name\":\"建设工程\",\"catid\":\"1\"}," +
            "{\"id\":\"004001002\",\"name\":\"市政工程\",\"catid\":\"4\"}," +
            "{\"id\":\"004001003\",\"name\":\"交通工程\",\"catid\":\"8\"}," +
            "{\"id\":\"004001007\",\"name\":\"水利工程\",\"catid\":\"2\"}" +
            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(categoryJson);

    @Override
    public void run() {
        System.out.println("延安市");
        for(int i = 0; i < cityJsonArr.size(); i++){
            JSONObject job = cityJsonArr.getJSONObject(i);
            for(int j =1; j < 20; j++){
                String url = "http://www.yaggzyjy.cn/Front_YanAn/jyxx/004001/"+job.get("id")+"/"+job.get("id")+"001/"+"?pageing="+j;
                List<Tender>list = service.getList(url,job.get("name").toString(), Integer.parseInt(job.get("catid").toString()));
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
