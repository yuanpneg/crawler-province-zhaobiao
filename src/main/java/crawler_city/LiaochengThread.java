package crawler_city;
import bean.Tender;
import services_city.LiaochengService;


import java.util.List;

/**
 * 聊城市
 */
public class LiaochengThread implements Runnable {

    private LiaochengService service = new LiaochengService();

    String[] types = new String[]{"001", "002", "003", "004", "005"};

    String[] areas = new String[]{"001", "002", "003", "004", "005", "006", "007", "008", "009", "010", "011", "012", "013", "014"};

    @Override
    public void run() {
        System.out.println("聊城市");
        for (int i = 1; i < 5; i++) {
            for (int k = 0; k < types.length; k++) {
                for (int a = 0; a < areas.length; a++) {
                    String url = "http://www.lcsggzyjy.cn/lcweb/jyxx/079001/079001001/079001001" + types[k] + "/079001001" + types[k] + areas[a] + "/?Paging=" + i;
                    System.out.println(url);
                    List<Tender> list = service.getList(url);
                    if (list.size() == 0) {
                        continue;
                    }
                }
            }

        }
    }
}
