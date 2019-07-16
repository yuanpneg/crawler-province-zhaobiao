package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import services.GansuService;

import java.util.List;

/**
 * 甘肃省
 */
public class GansuThread implements  Runnable{

    private GansuService service = new GansuService();


    String cityJson = "[" +
            "{\"id\":\"620100\",\"name\":\"兰州\"},{\"id\":\"620200\",\"name\":\"嘉峪关\"}," +
            "{\"id\":\"620300\",\"name\":\"金昌\"},{\"id\":\"620400\",\"name\":\"白银\"}," +
            "{\"id\":\"620500\",\"name\":\"天水\"},{\"id\":\"620600\",\"name\":\"武威\"}," +
            "{\"id\":\"620700\",\"name\":\"张掖\"},{\"id\":\"620800\",\"name\":\"平凉\"}," +
            "{\"id\":\"620900\",\"name\":\"酒泉\"},{\"id\":\"621000\",\"name\":\"庆阳\"}," +
            "{\"id\":\"621100\",\"name\":\"定西\"},{\"id\":\"621200\",\"name\":\"陇南\"}," +
            "{\"id\":\"622900\",\"name\":\"临夏\"},{\"id\":\"623000\",\"name\":\"甘南\"}" +
            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        for(int i=0; i < cityJsonArr.size(); i++){
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name"));
            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            formBodyBuilder.add("filterparam","%7B%22areaCode%22%3A%22"+
                    job.get("id") +
                    "%22%2C%22workNotice%22%3A%7B%22noticeNature%22%3A%221%22%2C%22bulletinType%22%3A%221%22%7D%7D");
            FormBody body = formBodyBuilder.build();
            for(int j=1; j < 20; j++){
                String url = "http://www.gsggfw.cn/w/bid/tenderAnnQuaInqueryAnn/pageList?pageNo="+j+"&pageSize=20";
                List<Tender> list = service.getList(url, body, job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
