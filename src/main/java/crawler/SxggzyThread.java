package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import services.SxggzyService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 山西省
 */
public class SxggzyThread implements Runnable {


    private SxggzyService sxggzyService = new SxggzyService();

    String cityJson = "[" +
            "{\"id\":\"140100\",\"name\":\"太原市\"},{\"id\":\"140200\",\"name\":\"大同市\"},{\"id\":\"140300\",\"name\":\"阳泉市\"}," +
            "{\"id\":\"140400\",\"name\":\"长治市\"},{\"id\":\"140500\",\"name\":\"晋城市\"},{\"id\":\"140600\",\"name\":\"朔州市\"}," +
            "{\"id\":\"140700\",\"name\":\"晋中市\"},{\"id\":\"140800\",\"name\":\"运城市\"},{\"id\":\"140900\",\"name\":\"忻州市\"}," +
            "{\"id\":\"141000\",\"name\":\"临汾市\"},{\"id\":\"141100\",\"name\":\"吕梁市\"}]";
    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {

        for (int i = 0; i < cityJsonArr.size(); i++) {
             JSONObject job = cityJsonArr.getJSONObject(i);
            //页数
            for (int j = 1; j < 15; j++) {
                String url = "http://prec.sxzwfw.gov.cn/moreInfoController.do?getMoreProjectInfo&page=1&rows=10&dateFlag=1month&tableName=gcjs&projectRegion=" +
                        job.get("id") + "&sjly=&projectName=&beginReceivetime=&endReceivetime=";
      //          FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                formBodyBuilder.add("date", "3month")
//                        .add("projectType", "gcjs")
//                        .add("area", job.get("id").toString())
//                        .add("huanJie", "NOTICE")
//                        .add("pageIndex", String.valueOf(j));
//                FormBody body = formBodyBuilder.build();
                 List<Tender> list =sxggzyService.getList(url, job.get("name").toString());
                if(list.size() == 0){
                    break;
                }
            }
        }

    }
}
