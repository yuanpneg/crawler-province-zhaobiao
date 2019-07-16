package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.GanziService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 甘孜州
 */
public class GanziThread implements Runnable {

    private GanziService service = new GanziService();

    @Override
    public void run() {
        System.out.println("甘孜州");
        String url = "http://jypt.scgzzg.com:88/pub/showMcontent";
        for (int i = 1; i <= 2; i++) {
            String mode = "";
            if(i == 1){
                mode = "JYGCJS_ZBGG";
            }else{
                mode = "JYGCJS_BGGG";
            }
            for (int j = 1; j < 20; j++) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("mcode", mode)
                        .add("clicktype", "1")
                        .add("prjpropertycode", "A01")
                        .add("pageNum", String.valueOf(j))
                        .add("keyname", "")
                        .add("areacode", "");
                FormBody body = formBodyBuilder.build();
                List<Tender>list =service.getList(url, body);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
