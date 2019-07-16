package crawler_city;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import okhttp3.FormBody;
import services_city.GuangzhouService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 广州市
 */
public class GuangzhouThread implements  Runnable{

    private GuangzhouService service = new GuangzhouService();

    String []  category = {"建设工程","水利工程","园林绿化","交通工程"};

    @Override
    public void run() {
        String url = "";
        int catid = 0;
        System.out.println("广州市");
        for (String s : category) {
            switch (s){
                case "建设工程":
                    url ="http://www.gzggzy.cn/cms/wz/view/index/layout2/szlist.jsp?siteId=1&channelId=503&pchannelid=466&curgclb=01,02,14&curxmlb=01,02,03,04,05,14&curIndex=1&pcurIndex=1&cIndex=1";
                    catid = 1;
                    break;
                case "水利工程":
                    url = "http://www.gzggzy.cn/cms/wz/view/index/layout2/szlist.jsp?siteId=1&channelId=525&pchannelid=470&curgclb=04&curxmlb=01,02,03,04,05,14&curIndex=1&pcurIndex=5&cIndex=1";
                    catid = 2;
                    break;
                case "交通工程":
                    url = "http://www.gzggzy.cn/cms/wz/view/index/layout2/szlist.jsp?siteId=1&channelId=510&pchannelid=467&curgclb=03&curxmlb=01,02,03,04,05,14&curIndex=1&pcurIndex=2&cIndex=1";
                    catid = 8;
                    break;
                case "园林绿化":
                    url = "http://www.gzggzy.cn/cms/wz/view/index/layout2/szlist.jsp?siteId=1&channelId=543&pchannelid=472&curgclb=08&curxmlb=01,02,03,04,05,14&curIndex=1&pcurIndex=6";
                    catid = 6;
                    break;
            }
            for(int i =1; i < 20; i++){
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("page",String.valueOf(i));
                FormBody body = formBodyBuilder.build();
                List<Tender> list = service.getList(url, body, s, catid);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
