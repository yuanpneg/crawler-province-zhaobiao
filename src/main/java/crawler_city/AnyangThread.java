package crawler_city;

import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.AnyangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 安阳市
 */
public class AnyangThread implements Runnable {

    private AnyangService service = new AnyangService();

    String categoryJson = "[" +
            "{\"id\":\"A01\",\"name\":\"建设工程\",\"catid\":\"1\"}," +
            "{\"id\":\"A02\",\"name\":\"市政工程\",\"catid\":\"4\"}," +
            "{\"id\":\"A03\",\"name\":\"交通工程\",\"catid\":\"8\"}," +
            "{\"id\":\"A07\",\"name\":\"水利工程\",\"catid\":\"2\"}" +
            "]";

    JSONArray categoryJsonArr = JSONArray.parseArray(categoryJson);

    @Override
    public void run() {
        String url = "http://www.ayggzy.cn/jyxx/jsgcZbgg";
        System.out.println("安阳市");
        for (int i = 0; i < categoryJsonArr.size(); i++) {
            JSONObject job = categoryJsonArr.getJSONObject(i);
            for (int j = 1; j < 20; j++) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("currentPage", String.valueOf(i))
                        .add("area", "004")
                        .add("secondArea", "000")
                        .add("city", "")
                        .add("industriesTypeCode", job.get("id").toString())
                        .add("tenderProjectCode", "")
                        .add("bulletinName", "");
                FormBody body = formBodyBuilder.build();
                List<Tender>list =service.getList(url, body, job.get("name").toString(), Integer.parseInt(job.get("catid").toString()));
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
