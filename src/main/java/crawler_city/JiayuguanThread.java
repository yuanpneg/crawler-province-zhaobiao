package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.JiayuguanService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 嘉峪关
 */
public class JiayuguanThread implements Runnable{

    private JiayuguanService service = new JiayuguanService();

    @Override
    public void run() {
        System.out.println("嘉峪关");
        String url = "http://www.jygzyjy.gov.cn/f/newtrade/tenderannquainqueryanns/getListByProjectType";
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("projectType", "A");
        FormBody body = formBodyBuilder.build();
        service.getList(url,body);
    }
}
