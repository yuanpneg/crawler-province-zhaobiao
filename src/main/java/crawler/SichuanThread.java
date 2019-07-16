package crawler;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import services.SichuanService;
import utils.GetContentUtil;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 四川省
 */
public class SichuanThread implements  Runnable{


    private SichuanService service = new SichuanService();

    String cityJson ="[" +
            "{\"id\":\"S002\",\"name\":\"成都\"},{\"id\":\"S003\",\"name\":\"德阳\"}," +
            "{\"id\":\"S004\",\"name\":\"绵阳\"},{\"id\":\"S005\",\"name\":\"内江\"}," +
            "{\"id\":\"S006\",\"name\":\"乐山\"},{\"id\":\"S007\",\"name\":\"广元\"}," +
            "{\"id\":\"S008\",\"name\":\"眉山\"},{\"id\":\"S009\",\"name\":\"自贡\"}," +
            "{\"id\":\"S010\",\"name\":\"雅安\"},{\"id\":\"S011\",\"name\":\"宜宾\"}," +
            "{\"id\":\"S012\",\"name\":\"攀枝花\"},{\"id\":\"S013\",\"name\":\"泸州\"}," +
            "{\"id\":\"S014\",\"name\":\"遂宁\"},{\"id\":\"S015\",\"name\":\"广安\"}" +
            "{\"id\":\"S016\",\"name\":\"南充\"},{\"id\":\"S017\",\"name\":\"达州\"}" +
            "{\"id\":\"S018\",\"name\":\"资阳\"},{\"id\":\"S019\",\"name\":\"巴中\"}" +
            "{\"id\":\"S020\",\"name\":\"阿坝\"},{\"id\":\"S021\",\"name\":\"甘孜\"}" +
            "{\"id\":\"S022\",\"name\":\"凉山\"}"+
            "]";

//    String cityJson ="[" +
//
//            "{\"id\":\"S017\",\"name\":\"达州\"}" +
//            "{\"id\":\"S021\",\"name\":\"甘孜\"}" +
//            "]";

//    String cityJson = "[" +
//            "{\"id\":\"17\",\"name\":\"雅安\"},{\"id\":\"14\",\"name\":\"泸州\"},"+
//            "{\"id\":\"11\",\"name\":\"南充\"},{\"id\":\"10\",\"name\":\"达州\"}"+
//            "]";

    JSONArray cityJsonArr = JSONArray.parseArray(cityJson);

    @Override
    public void run() {
        String url = "http://ggzyjy.sc.gov.cn/inteligentsearch/rest/inteligentSearch/getFullTextData";
        LocalDate date = LocalDate.now();
        LocalDate yesterday =  LocalDate.now().minusMonths(1);
        for (int i = 0; i < cityJsonArr.size(); i++) {
            JSONObject job = cityJsonArr.getJSONObject(i);
            System.out.println(job.get("name"));
            for(int j = 0; j < 8; j++){
                String postBody = "{\"token\":\"\",\"pn\":"+j*12+",\"rn\":12,\"sdt\":\""+yesterday+" 00:00:00\",\"edt\":\""+date+" 23:59:59\",\"wd\":\"\",\"inc_wd\":\"\",\"exc_wd\":\"\",\"fields\":\"title\",\"cnum\":\"\",\"sort\":\"{'infodate':'0'}\",\"ssort\":\"title\",\"cl\":500,\"terminal\":\"\",\"condition\":[{\"fieldName\":\"tradingsourcevalue\",\"equal\":\""+job.get("id")+"\",\"notEqual\":null,\"equalList\":null,\"notEqualList\":null},{\"fieldName\":\"categorynum\",\"equal\":\"002001001\",\"notEqual\":null,\"equalList\":null,\"notEqualList\":null,\"isLike\":true,\"likeType\":2}],\"time\":null,\"highlights\":\"\",\"statistics\":null,\"unionCondition\":null,\"accuracy\":\"\",\"noParticiple\":\"0\",\"searchRange\":null,\"isBusiness\":\"1\"}";

                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                        , postBody);
                List<Tender> list = service.getList(url, job.get("name").toString(),requestBody);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
