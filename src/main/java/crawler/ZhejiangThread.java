package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import services.ZhejiangService;

import java.util.List;

/**
 * 浙江 详情页面无法获取
 */
public class ZhejiangThread implements  Runnable{

    private BaseDao baseDao = new BaseDao();

    private FormalDao formalDao = new FormalDao();

    private ZhejiangService service = new ZhejiangService();

    String cityJson ="[" +
            "{\"id\":\"330100\",\"name\":\"杭州市\"},{\"id\":\"330200\",\"name\":\"宁波市\"}," +
            "{\"id\":\"330300\",\"name\":\"温州市\"},{\"id\":\"330400\",\"name\":\"嘉兴市\"}," +
            "{\"id\":\"330500\",\"name\":\"湖州市\"},{\"id\":\"330600\",\"name\":\"绍兴市\"}," +
            "{\"id\":\"330700\",\"name\":\"金华市\"},{\"id\":\"330800\",\"name\":\"衢州市\"}," +
            "{\"id\":\"330900\",\"name\":\"舟山市\"},{\"id\":\"331000\",\"name\":\"台州市\"}," +
            "{\"id\":\"331100\",\"name\":\"丽水市\"}]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name"));
            for(int j = 1; j < 20; j++){
                String url = "http://www.zjpubservice.com/fulltextsearch/rest/getfulltextdata?format=json&sort=0&rmk1=002001001&pn=" +
                        j +
                        "&rn=20&idx_cgy=web&rmk2=E47&rmk4=" +
                        job.get("id");
                service.getList(url,job.get("name").toString());

            }
        }
    }
}
