package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.ShanghaiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 上海
 */
public class ShanghaiThread implements Runnable {


    private ShanghaiService service = new ShanghaiService();

    @Override
    public void run() {
        System.out.println("上海");
        for (int i = 1; i < 20; i++) {
            String url = "http://222.66.64.149/publicity/constructionProject/page?page="+i+"&pageSize=10&timeZone=3";
            List<Tender> list = service.getList(url);
            if(list.size() == 0){
                break;
            }
        }
    }
}
