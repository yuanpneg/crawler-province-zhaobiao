package crawler_city;


import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import services_city.LianyungangService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 连云港
 */
public class LianyungangThread implements Runnable {

    private LianyungangService service = new LianyungangService();

    String categoryJson = "[" +
            "{\"id\":\"001001001\",\"name\":\"建设工程\",\"catid\":1}," +
            "{\"id\":\"001003001\",\"name\":\"水利工程\",\"catid\":2}," +
            "{\"id\":\"001002001\",\"name\":\"交通工程\",\"catid\":8}]";

    JSONArray categoryArr = JSONArray.parseArray(categoryJson);

    @Override
    public void run() {

        String url = "http://spzx.lyg.gov.cn/EpointWebBuilder/jyxxAction.action?cmd=getList";
        for (int i = 0; i < categoryArr.size(); i++) {
            JSONObject job = categoryArr.getJSONObject(i);
            for (int j = 1; j <= 20; j++) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("siteGuid", "6c490a11-b8cc-4fd6-b80f-e5686167f5c8")
                        .add("city", "")
                        .add("categorynum", job.get("id").toString())
                        .add("xmmc", "")
                        .add("pageIndex", String.valueOf(j))
                        .add("pageSize", "10");
                FormBody body = formBodyBuilder.build();
                List<Tender> list = service.getlist(url, body, job.get("name").toString(), Integer.parseInt(job.get("catid").toString()));
                if (list.size() == 0) {
                    break;
                }
            }
        }
    }
}
