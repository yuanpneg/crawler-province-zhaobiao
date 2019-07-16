package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services_city.LinzhiService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 林芝市
 */
public class LinzhiThread implements Runnable{

    private LinzhiService service = new LinzhiService();

    @Override
    public void run() {
        System.out.println("林芝市");
        String url = "http://www.lzcs.gov.cn/Columns/MessageColumnMore.aspx?ColumnId=527";
        service.getList(url);

    }
}
