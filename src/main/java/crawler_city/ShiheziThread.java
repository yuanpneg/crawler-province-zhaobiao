package crawler_city;


import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import org.apache.xpath.SourceTree;
import services_city.ShiheziService;
import utils.GetContentUtil;

import java.util.List;

/**
 * 石河子（第八师）
 */
public class ShiheziThread implements Runnable {

    private ShiheziService service = new ShiheziService();

    @Override
    public void run() {

        String url = "";
        String city = "";
        String from = "";
        for (int j = 0; j < 10; j++) {
            for (int i = 1; i < 3; i++) {
                if (j == 0) {
                    System.out.println("石河子市");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt8/086003/086003001/086003001001/?Paging=" + i;
                    city = "石河子";
                    from = "兵团公共资源交易中心第八分中心";
                } else if (j == 1) {
                    System.out.println("五家渠市");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt6/084003/084003001/084003001001/?Paging=" + i;
                    city = "五家渠";
                    from = "兵团公共资源交易中心第六分中心";
                } else if (j == 2) {
                    System.out.println("哈密");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt13/090003/090003001/090003001001/?Paging=" + i;
                    city = "哈密";
                    from = "兵团公共资源交易中心第十三分中心";
                } else if (j == 3) {
                    System.out.println("克拉玛依");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt7/085003/085003001/085003001001/?Paging=" + i;
                    city = "克拉玛依";
                    from = "兵团公共资源交易中心第七分中心";
                } else if (j == 4) {
                    System.out.println("吐鲁番");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt12/089003/089003001/089003001001/?Paging=" + i;
                    city = "吐鲁番";
                    from = "兵团公共资源交易中心第十二分中心";
                } else if (j == 5) {
                    System.out.println("阿拉尔");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt1/079003/079003001/079003001001/?Paging=" + i;
                    city = "阿拉尔";
                    from = "兵团公共资源交易中心第一分中心";
                } else if (j == 6) {
                    System.out.println("阿克苏");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt1/079003/079003001/079003001001/?Paging=" + i;
                    city = "阿克苏";
                    from = "兵团公共资源交易中心第一分中心";
                } else if (j == 7) {
                    System.out.println("巴音郭楞");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt2/080003/080003001/080003001001/?Paging=" + i;
                    city = "巴音郭楞";
                    from = "兵团公共资源交易中心第二分中心";
                } else if (j == 8) {
                    System.out.println("博尔塔拉");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt5/083003/083003001/083003001001/?Paging=" + i;
                    city = "博尔塔拉";
                    from = "兵团公共资源交易中心第五分中心";
                } else if (j == 9) {
                    System.out.println("伊犁");
                    url = "http://ggzy.xjbt.gov.cn/TPFront/bt4/082003/082003001/082003001001/?Paging=" + i;
                    city = "伊犁";
                    from = "兵团公共资源交易中心第四分中心";
                }
                List<Tender> list = service.getList(url, city, from);
                if(list.size() == 0){
                    break;
                }
            }
        }
    }
}
