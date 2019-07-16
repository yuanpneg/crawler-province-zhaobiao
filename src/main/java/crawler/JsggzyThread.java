package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import services.JsggzyService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 江苏省
 */
public class JsggzyThread implements Runnable {


    private JsggzyService jsggzyService = new JsggzyService();
    @Override
    public void run() {
        String[] citys = {"南京市","无锡市","徐州市","常州市","苏州市","南通市","连云港市","淮安市","盐城市","扬州市","镇江市","泰州市","宿迁市"};
        for (String city : citys) {
            System.out.println(city);
            for (int i = 0; i <= 150; i += 15) {
                String url = "http://221.226.253.51:5057/inteligentsearch/rest/inteligentSearch/getFullTextData";
                String postBody = "{\"token\":\"\",\"pn\":"+i+",\"rn\":\"14\",\"sdt\":\"\",\"edt\":\"\",\"wd\":\"\",\"inc_wd\":\"\",\"exc_wd\":\"\",\"fields\":\"title\",\"cnum\":\"001\",\"sort\":\"{\\\"infodatepx\\\":\\\"0\\\"}\",\"ssort\":\"title\",\"cl\":200,\"terminal\":\"\",\"condition\":[{\"fieldName\":\"categorynum\",\"isLike\":true,\"likeType\":2,\"equal\":\"003001001\"},{\"fieldName\":\"fieldvalue\",\"isLike\":true,\"likeType\":2,\"equal\":\""
                        +city+
                        "\"},{\"fieldName\":\"author\",\"isLike\":true,\"likeType\":2,\"equal\":\"\"}],\"time\":null,\"highlights\":\"title\",\"statistics\":null,\"unionCondition\":null,\"accuracy\":\"\",\"noParticiple\":\"0\",\"searchRange\":null,\"isBusiness\":\"1\"}";
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                        , postBody);
                List<Tender> list = jsggzyService.getList(url,requestBody);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
