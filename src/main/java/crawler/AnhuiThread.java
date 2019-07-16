package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services.AnhuiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 安徽省
 */
public class AnhuiThread implements Runnable {

    private AnhuiService service = new AnhuiService();

    String cityJson = "[" +
            "{\"id\":\"340100\",\"name\":\"合肥市\"},{\"id\":\"340200\",\"name\":\"芜湖市\"}," +
            "{\"id\":\"340300\",\"name\":\"蚌埠市\"},{\"id\":\"340400\",\"name\":\"淮南市\"}," +
            "{\"id\":\"340500\",\"name\":\"马鞍山市\"},{\"id\":\"340600\",\"name\":\"淮北市\"}," +
            "{\"id\":\"340700\",\"name\":\"铜陵市\"},{\"id\":\"340800\",\"name\":\"安庆市\"}," +
            "{\"id\":\"341000\",\"name\":\"黄山市\"},{\"id\":\"341100\",\"name\":\"滁州市\"}," +
            "{\"id\":\"341200\",\"name\":\"阜阳市\"},{\"id\":\"341300\",\"name\":\"宿州市\"}," +
            "{\"id\":\"341500\",\"name\":\"六安市\"},{\"id\":\"341600\",\"name\":\"亳州市\"}," +
            "{\"id\":\"341700\",\"name\":\"池州市\"},{\"id\":\"341800\",\"name\":\"宣城市\"}" +
            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        String url = "http://www.ahggzy.gov.cn/dwr/call/plaincall/bulletinInfoDWR.getPackListForDwr1.dwr";

        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name"));
            for (int j = 1; j < 20; j++) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("callCount", "1")
                        .add("page", "/bulletininfo.do?method=showList&fileType=1&hySort=&bulletinclass=jy&num=1")
                        .add("httpSessionId", "27bc41cfa420b34cdcf205ce9d4b")
                        .add("scriptSessionId", "4A41531C332A1EF6F67AA4CB6058CC82885")
                        .add("c0-scriptName", "bulletinInfoDWR")
                        .add("c0-methodName","getPackListForDwr1")
                        .add("c0-id","0")
                        .add("c0-e1","string:201")
                        .add("c0-e2","string:")
                        .add("c0-e3","string:jy")
                        .add("c0-e4","string:201")
                        .add("c0-e5","string:01")
                        .add("c0-e6","string:")
                        .add("c0-e7","string:"+job.get("id"))
                        .add("c0-e8","number:"+j)
                        .add("c0-e9","string:10")
                        .add("c0-e10","string:true")
                        .add("c0-e11","string:packTable")
                        .add("c0-e12","string:500")
                        .add("c0-param0","Object_Object:{id:reference:c0-e1, hySort:reference:c0-e2, bulletinclass:reference:c0-e3, fileType:reference:c0-e4, bulletinType:reference:c0-e5, district:reference:c0-e6, srcdistrict:reference:c0-e7, currentPage:reference:c0-e8, pageSize:reference:c0-e9, isPage:reference:c0-e10, tabId:reference:c0-e11, totalRows:reference:c0-e12}")
                        .add("batchId", String.valueOf(j));
                FormBody body = formBodyBuilder.build();
                List<Tender> list = service.getList(url, body, job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }
    }


}
