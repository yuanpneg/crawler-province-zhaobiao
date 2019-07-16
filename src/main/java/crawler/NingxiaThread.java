package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.NingxiaService;
import utils.GetContentUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 宁夏省
 */
public class NingxiaThread implements Runnable {


    private NingxiaService service = new NingxiaService();

    String[] citys = {"银川市", "石嘴山市", "吴忠市", "固原市", "中卫市"};

    @Override
    public void run() {
        try {
            for (String city : citys) {
                for (int i = 1; i < 20; i++) {
                    String url = "http://www.nxggzyjy.org/ningxiawebservice/services/BulletinWebServer/getInfoListInAbout?response=application/json&pageIndex=" +
                            +i + "&pageSize=18&siteguid=2e221293-d4a1-40ed-854b-dcfea12e61c5&categorynum=002001001&cityname=" +
                            URLEncoder.encode(city, "utf-8") +
                            "&title=&hy=&sdt=&edt=";
                    List<Tender> list = service.getList(url, city);
                    if (list.size() == 0) {
                        break;
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
