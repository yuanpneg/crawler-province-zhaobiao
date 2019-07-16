package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;

import dao.FormalDao;
import okhttp3.FormBody;
import services.HubeiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 湖北省
 */
public class HubeiThread implements Runnable{

    private HubeiService service = new HubeiService();

    String cityJson = "[" +
            "{\"id\":\"002\",\"name\":\"武汉\"},{\"id\":\"003\",\"name\":\"黄石\"},{\"id\":\"004\",\"name\":\"十堰\"}," +
            "{\"id\":\"005\",\"name\":\"荆州\"},{\"id\":\"006\",\"name\":\"宜昌\"},{\"id\":\"007\",\"name\":\"襄阳\"}," +
            "{\"id\":\"008\",\"name\":\"鄂州\"},{\"id\":\"009\",\"name\":\"荆门\"},{\"id\":\"010\",\"name\":\"黄冈\"}," +
            "{\"id\":\"011\",\"name\":\"孝感\"},{\"id\":\"012\",\"name\":\"咸宁\"},{\"id\":\"013\",\"name\":\"随州\"}" +
            ",{\"id\":\"014\",\"name\":\"恩施\"},{\"id\":\"015\",\"name\":\"仙桃\"},{\"id\":\"016\",\"name\":\"天门\"}" +
            ",{\"id\":\"017\",\"name\":\"潜江\"},{\"id\":\"018\",\"name\":\"神农架林区\"}]";


    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        String url= "https://www.hbggzyfwpt.cn/jyxx/jsgcZbgg";
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name").toString());
            for (int j = 1; j < 20; j++) {  //页数
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                formBodyBuilder.add("currentPage", String.valueOf(j))
//                        .add("industriesTypeCode", "1")
//                        .add("area", job.get("id").toString())
//                        .add("scrollValue", "0")
//                        .add("tenderProjectCode", "")
//                        .add("bulletinName","")
//                        .add("secondArea","");
                formBodyBuilder.add("currentPage", String.valueOf(j))
                        .add("industriesTypeCode", "0")
                        .add("area", job.get("id").toString())
                        .add("scrollValue", "0")
                        .add("publishTimeType", "2")
                        .add("bulletinName","")
                        .add("publishTimeStart","")
                        .add("publishTimeEnd","");
                FormBody body = formBodyBuilder.build();

                List<Tender> list = service.getList(url, body, job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
