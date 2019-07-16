package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.JiangxiService;
import utils.GetContentUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 江西省
 */
public class JiangxiggzyThread implements Runnable {


    private JiangxiService service = new JiangxiService();

    //002002002
    //http://jxsggzy.cn/jxggzy/services/JyxxWebservice/getList?response=application/json&pageIndex=1&pageSize=22&area=d%E5%8D%97%E6%98%8C%E5%B8%82&prepostDate=&nxtpostDate=&xxTitle=&categorynum=002002002
    //http://jxsggzy.cn/jxggzy/services/JyxxWebservice/getList?response=application/json&pageIndex=1&pageSize=22&area=d%E5%8D%97%E6%98%8C%E5%B8%82&prepostDate=&nxtpostDate=&xxTitle=&categorynum=002003001

    String[] citys = {"南昌市", "景德镇市", "萍乡市", "九江市", "新余市", "鹰潭市", "吉安市", "宜春市", "抚州市", "上饶市", "赣州市", "赣江"};

    String[] categorys = {"建设工程", "交通工程", "水利工程"};

    @Override
    public void run() {
        for (String city : citys) {
            for (String category : categorys) {
                String categorynum = "";
                int catid = 0;
                if ("建设工程".equals(category)) {
                    categorynum = "002001001";
                    catid = 1;
                }else if ("交通工程".equals(category)){
                    categorynum = "002002002";
                    catid = 8;
                }else if("水利工程".equals(category)){
                    categorynum = "002003001";
                    catid = 2;
                }
                for (int i = 1; i <= 20; i++) {
                    try {
                        String url = "http://jxsggzy.cn/jxggzy/services/JyxxWebservice/getList?response=application/json&pageIndex=" +
                                String.valueOf(i) +
                                "&pageSize=22&area=d" +
                                URLEncoder.encode(city, "utf-8") +
                                "&prepostDate=&nxtpostDate=&xxTitle=&categorynum=" + categorynum;
                        List<Tender> list = service.getList(url, category, categorynum, catid, city);
                        if(list.size() == 0){
                            break;
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
    }
}
