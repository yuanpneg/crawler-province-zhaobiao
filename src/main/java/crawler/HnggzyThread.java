package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import services.HnggzyService;
import utils.GetContentUtil;

import java.util.List;


/**
 * 河南省公共资源交易网
 */
public class HnggzyThread implements Runnable {

    private HnggzyService service = new HnggzyService();

    String cityJson = "[" +
            "{\"id\":\"410100\",\"name\":\"郑州市\"},{\"id\":\"410200\",\"name\":\"开封市\"},{\"id\":\"410300\",\"name\":\"洛阳市\"}," +
            "{\"id\":\"410400\",\"name\":\"平顶山市\"},{\"id\":\"410500\",\"name\":\"安阳市\"},{\"id\":\"410600\",\"name\":\"鹤壁市\"}," +
            "{\"id\":\"410700\",\"name\":\"新乡市\"},{\"id\":\"410800\",\"name\":\"焦作市\"},{\"id\":\"410900\",\"name\":\"濮阳市\"}," +
            "{\"id\":\"411000\",\"name\":\"许昌市\"},{\"id\":\"411100\",\"name\":\"漯河市\"},{\"id\":\"411200\",\"name\":\"三门峡市\"}," +
            "{\"id\":\"411300\",\"name\":\"南阳市\"},{\"id\":\"411400\",\"name\":\"商丘市\"},{\"id\":\"411500\",\"name\":\"信阳市\"}," +
            "{\"id\":\"411600\",\"name\":\"周口市\"},{\"id\":\"411700\",\"name\":\"驻马店市\"},{\"id\":\"419001\",\"name\":\"济源市\"}" +
            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            //页数
            for(int j = 1; j < 15; j++){
                String url = "http://www.hnsggzyfwpt.gov.cn/services/hl/getSelect?response=application/json&pageIndex=" +
                        j +
                        "&pageSize=22&day=&sheng=&qu=" +
                        job.get("id") +
                        "&xian=&title=&timestart=&timeend=&categorynum=002001001&siteguid=9f5b36de-4e8f-4fd6-b3a1-a8e08b38ea38";
                //System.out.println(url);
                List<Tender> list = service.getList(url);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}

