package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services.YunnanService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 云南省
 */
public class YunnanThread implements  Runnable{


    private YunnanService service = new YunnanService();

    String cityJson ="[" +
            "{\"id\":\"002\",\"name\":\"昆明\"},{\"id\":\"003\",\"name\":\"曲靖\"}," +
            "{\"id\":\"004\",\"name\":\"玉溪\"},{\"id\":\"005\",\"name\":\"保山\"}," +
            "{\"id\":\"006\",\"name\":\"昭通\"},{\"id\":\"007\",\"name\":\"丽江\"}," +
            "{\"id\":\"008\",\"name\":\"普洱\"},{\"id\":\"009\",\"name\":\"临沧\"}," +
            "{\"id\":\"014\",\"name\":\"楚雄\"},{\"id\":\"015\",\"name\":\"红河\"}," +
            "{\"id\":\"016\",\"name\":\"文山\"},{\"id\":\"017\",\"name\":\"西双版纳\"}," +
            "{\"id\":\"013\",\"name\":\"大理\"},{\"id\":\"010\",\"name\":\"德宏\"}," +
            "{\"id\":\"011\",\"name\":\"怒江\"},{\"id\":\"012\",\"name\":\"迪庆\"},"
            +"]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        String url = "https://www.ynggzyxx.gov.cn/jyxx/jsgcZbgg";
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name"));
            for(int j = 1; j < 20; j++) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("currentPage", String.valueOf(j))
                        .add("industriesTypeCode", "")
                        .add("area", job.get("id").toString())
                        .add("scrollValue", "0")
                        .add("tenderProjectCode", "")
                        .add("bulletinName","")
                        .add("secondArea","");
                FormBody body = formBodyBuilder.build();
                List<Tender> list =service.getList(url, body, job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
