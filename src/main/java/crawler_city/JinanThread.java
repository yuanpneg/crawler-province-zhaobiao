package crawler_city;


import bean.Tender;
import okhttp3.FormBody;
import services_city.JinanService;

import java.util.List;

/**
 * 济南市
 */
public class JinanThread implements Runnable{

    JinanService service = new JinanService();

    @Override
    public void run() {
        for(int i  = 1; i < 20; i++){
            String url = "http://jnggzy.jinan.gov.cn/jnggzyztb/front/search.do";

            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("area","")
                    .add("pagenum",String.valueOf(i))
                    .add("subheading","")
                    .add("type","0")
                    .add("xuanxiang","招标公告");
            FormBody body = formBodyBuilder.build();
            List<Tender> list = service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
