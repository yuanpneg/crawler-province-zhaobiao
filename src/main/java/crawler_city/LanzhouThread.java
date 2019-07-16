package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.LanzhouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 兰州市
 */
public class LanzhouThread implements Runnable{

    private LanzhouService service = new LanzhouService();

    @Override
    public void run() {
        System.out.println("兰州市");
        String url = "http://lzggzyjy.lanzhou.gov.cn/EpointWebBuilder/xxlistSearchAction.action?cmd=initPageList";
        for(int i = 1; i < 20; i++){
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("siteGuid", "7eb5f7f1-9041-43ad-8e13-8fcb82ea831a")
                    .add("title", "")
                    .add("categorynum", "002001001")
                    .add("citycode", "all")
                    .add("pageIndex", String.valueOf(i))
                    .add("pageSize","15");
            FormBody body = formBodyBuilder.build();

            List<Tender> list = service.getList(url,body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
