package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import services.QinghaiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 青海省
 */
public class QinghaiThread implements Runnable {


    private QinghaiService service = new QinghaiService();

    String cityJson = "[" +
            "{\"id\":\"6301\",\"name\":\"西宁\",\"cnum\":\"009\"},{\"id\":\"6321\",\"name\":\"海东\",\"cnum\":\"005\"}," +
            "{\"id\":\"6328\",\"name\":\"海西\",\"cnum\":\"007\"},{\"id\":\"6322\",\"name\":\"海北\",\"cnum\":\"004\"}," +
            "{\"id\":\"6325\",\"name\":\"海南\",\"cnum\":\"006\"},{\"id\":\"6323\",\"name\":\"黄南\",\"cnum\":\"008\"}," +
            "{\"id\":\"6327\",\"name\":\"玉树\",\"cnum\":\"010\"},{\"id\":\"6326\",\"name\":\"果洛\",\"cnum\":\"003\"}," +
            "{\"id\":\"632801\",\"name\":\"格尔木市\",\"cnum\":\"001\"}" +
            "]";



    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        String url = "http://www.qhggzyjy.gov.cn/inteligentsearch/rest/inteligentSearch/getFullTextData";
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name"));
            for(int j = 0; j < 200; j += 10) {
                String postBody = "{\"token\":\"\",\"pn\":" +
                        j + ",\"rn\":10,\"sdt\":\"\",\"edt\":\"\",\"wd\":\"\",\"inc_wd\":\"\",\"exc_wd\":\"\",\"fields\":\"title\",\"cnum\":\""+job.get("cnum")+"\",\"sort\":\"{\\\"showdate\\\":\\\"0\\\"}\",\"ssort\":\"title\",\"cl\":200,\"terminal\":\"\",\"condition\":[{\"fieldName\":\"categorynum\",\"isLike\":true,\"likeType\":2,\"equal\":\"001001001\"},{\"fieldName\":\"xiaqucode\",\"isLike\":true,\"likeType\":2,\"equal\":\""
                        +job.get("id")+"\"}],\"time\":null,\"highlights\":\"title\",\"statistics\":null,\"unionCondition\":null,\"accuracy\":\"100\",\"noParticiple\":\"0\",\"searchRange\":null,\"isBusiness\":1}";
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                        , postBody);
                List<Tender>list =service.getList(url,job.get("name").toString(),requestBody);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
