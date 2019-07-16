package crawler;

import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import services.JiangxiService;
import services.JilinService;
import utils.GetContentUtil;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * 吉林省
 */
public class JilinThread implements Runnable {


    private JilinService service = new JilinService();

    String[] citys = {"长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城", "延边"}; // "长白山"

    @Override
    public void run() {
        for (String city : citys) {
            for (int i = 1; i < 20; i++) {
                Date now = new Date();
                String search = "";
                switch (city) {
                    case "长春":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%27112203007645929828%27%20or%20area=%27122030000105%27%20or%20area=%27112203000135292377%27%20or%20area=%2741270618-1%27%20or%20area=%27112203000135298353%27%20or%20area=%2711220300413126808N%27%20or%20%20area=%2712220300MB0125428T%27)%20and%20";
                        break;
                    case "吉林":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%2712220200782609514F%27)%20and%20";
                        break;
                    case "四平":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%27112203007645929828%27%20or%20area=%27122030000105%27%20or%20area=%27112203000135292377%27%20or%20area=%2741270618-1%27%20or%20area=%27112203000135298353%27%20or%20area=%2711220300413126808N%27%20or%20%20area=%2712220300MB0125428T%27)%20and%20";
                        break;
                    case "辽源":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%2734004150-3%27%20or%20%20area=%2712220400412763282Y%27)%20and%20";
                        break;
                    case "通化":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%2773256678-X%27%20or%20area=%2712220500MB1143476B%27%20)%20and%20";
                        break;
                    case "白山":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%2712220600737041237Q%27)%20and%20";
                        break;
                    case "松原":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%2766011618-0%27%20%20or%20%20area=%2712220700MB1837064Y%27)%20and%20";
                        break;
                    case "白城":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%2766429601-9%27%20or%20%20area=%2712220800MB11528661%27)%20and%20";
                        break;
                    case "延边自治州":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%2773256854-X%27%20or%20area=%2711222400MB14602364%27)%20and%20";
                        break;
                    case "长白山":
                        search = "searchword=gtitle%3C%3E%27%27%20and%20gtitle%3C%3E%27null%27%20and%20(area=%27112200007710693483%27)%20and%20";
                        break;
                }
                String url = "http://was.jl.gov.cn/was5/web/search?channelid=237687&page=" +
                        i + "&prepage=17&" + search +
                        "tType=%27%E5%B7%A5%E7%A8%8B%E5%BB%BA%E8%AE%BE%27%20%20%20and%20iType=%27%E6%8B%9B%E6%A0%87%E5%85%AC%E5%91%8A%27%20%20%20&callback&callback=result&_=" + now.getTime();
                List<Tender> list = service.getList(url, city);
                if(list.size() == 0){
                    break;
                }

            }

        }
    }
}
