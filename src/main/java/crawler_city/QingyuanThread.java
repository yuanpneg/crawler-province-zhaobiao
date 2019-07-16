package crawler_city;

import bean.Tender;
import okhttp3.FormBody;
import services_city.QingyuanService;

import java.util.List;

/**
 * 清远市
 */
public class QingyuanThread implements Runnable{

    private QingyuanService service = new QingyuanService();

    @Override
    public void run() {
        System.out.println("清远");
        String url = "https://www.qyggzy.cn/webIndex/newsLeftBoard/0102/010201";
        for(int i = 1; i < 20; i++){
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            formBodyBuilder.add("TokenCode", "9ec65e5-dce5-4a73-a3fe-ea7716a7e42a")
                    .add("ftitle","")
                    .add("pageNO",String.valueOf(i));
            FormBody body = formBodyBuilder.build();
            List<Tender> list = service.getList(url, body);
            if(list.size() == 0){
                break;
            }
        }
    }
}
