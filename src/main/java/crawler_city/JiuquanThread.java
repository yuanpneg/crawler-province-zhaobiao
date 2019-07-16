package crawler_city;

import bean.Tender;
import okhttp3.FormBody;
import services_city.JiuquanService;

import java.util.List;

/**
 * 酒泉市
 */
public class JiuquanThread implements Runnable{

    JiuquanService service = new JiuquanService();

    @Override
    public void run() {
        System.out.println("酒泉市");
        for(int i = 0; i < 20; i++){
            if(i ==1) continue; //第一页和第二页内容相同
            String url = "http://www.ggzyjypt.com.cn/EpointWebBuilder_jqggzy/getGgListAction.action?cmd=getZtbGgList";
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("pageIndex", String.valueOf(i))
                    .add("pageSize","20")
                    .add("siteGuid","7eb5f7f1-9041-43ad-8e13-8fcb82ea831a")
                    .add("title","")
                    .add("categorynum","008001")
                    .add("diqu","620901")
                    .add("xmlx","")
                    .add("cgfs","");
            FormBody body = formBodyBuilder.build();
            List<Tender> list = service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
