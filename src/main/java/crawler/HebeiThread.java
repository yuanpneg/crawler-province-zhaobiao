package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import services.HebeiService;
import utils.GetContentUtil;

import java.util.List;

/*
 *河北省
 */
public class HebeiThread implements Runnable{



    private HebeiService service = new HebeiService();

    String cityJson = "[" +
            "{\"id\":\"1301\",\"name\":\"石家庄\"},{\"id\":\"1308\",\"name\":\"承德\"},{\"id\":\"1307\",\"name\":\"张家口\"}," +
            "{\"id\":\"1303\",\"name\":\"秦皇岛\"},{\"id\":\"1302\",\"name\":\"唐山\"},{\"id\":\"1310\",\"name\":\"廊坊\"}," +
            "{\"id\":\"1306\",\"name\":\"保定\"},{\"id\":\"1309\",\"name\":\"沧州\"},{\"id\":\"1311\",\"name\":\"衡水\"}," +
            "{\"id\":\"1305\",\"name\":\"邢台\"},{\"id\":\"1304\",\"name\":\"邯郸\"},{\"id\":\"139001\",\"name\":\"定州\"}" +
            ",{\"id\":\"139002\",\"name\":\"辛集\"}]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        String url = "http://www.hebpr.cn/inteligentsearch/rest/inteligentSearch/getFullTextDataNew";
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name").toString());
            for(int j = 0; j < 200; j += 10) {
                String postBody = "{\"token\":\"\",\"pn\":" +
                        j +
                        ",\"rn\":10,\"sdt\":\"\",\"edt\":\"\",\"wd\":\"\",\"inc_wd\":\"\",\"exc_wd\":\"\",\"fields\":\"title\",\"cnum\":\"001\",\"sort\":\"{\\\"showdate\\\":\\\"0\\\"}\",\"ssort\":\"title\",\"cl\":200,\"terminal\":\"\",\"condition\":[{\"fieldName\":\"categorynum\",\"isLike\":true,\"likeType\":2,\"equal\":\"003005002\"},{\"fieldName\":\"infoc\",\"isLike\":true,\"likeType\":2,\"equal\":\"" +
                        job.get("id") +
                        "\"}],\"time\":null,\"highlights\":\"title\",\"statistics\":null,\"unionCondition\":null,\"accuracy\":\"\",\"noParticiple\":\"0\",\"searchRange\":null,\"isBusiness\":1}";
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                        , postBody);
                List<Tender> list = service.getList(url,job.get("name").toString(),requestBody);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
