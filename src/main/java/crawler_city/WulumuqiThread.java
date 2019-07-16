package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.WulumuqiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 乌鲁木齐
 */
public class WulumuqiThread implements Runnable{

    private WulumuqiService service = new WulumuqiService();

    @Override
    public void run() {
        System.out.println("乌鲁木齐市");
        String url = "http://ggzy.wlmq.gov.cn/dwr/call/plaincall/projectDWR.queryItemInfoByIndustryType2.dwr";

        for (int j = 1; j < 20; j++) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("callCount", "1")
                    .add("page", "/generalpage.do?method=showList&fileType=201605-043&faname=201605-038&num=4")
                    .add("httpSessionId", "")
                    .add("scriptSessionId", "230F1D20D179AC6B8BCFDE263F2840CE734")
                    .add("c0-scriptName", "projectDWR")
                    .add("c0-methodName", "queryItemInfoByIndustryType2")
                    .add("c0-id", "0")
                    .add("c0-e1", "string:packTable")
                    .add("c0-e2", "string:201605-043")
                    .add("c0-e3", "number:" + j)
                    .add("c0-e4", "string:15")
                    .add("c0-e5", "string:true")
                    .add("c0-e6", "string:packTable")
                    .add("c0-e7", "string:1824")
                    .add("c0-param0", "Object_Object:{flag:reference:c0-e1, name:reference:c0-e2, currentPage:reference:c0-e3, pageSize:reference:c0-e4, " +
                            "isPage:reference:c0-e5, tabId:reference:c0-e6, totalRows:reference:c0-e7}")
                    .add("batchId", String.valueOf(j));
            FormBody body = formBodyBuilder.build();
            List<Tender>list = service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
