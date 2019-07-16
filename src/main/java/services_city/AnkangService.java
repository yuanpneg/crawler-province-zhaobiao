package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 安康市
 */
public class AnkangService {

    private static final Logger LOGGER = Logger.getLogger(AnkangService.class); //使用log4j记录日志

    public List<Tender> getList(String html, FormBody body) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByClass("newslist ico03");
            Elements lis = elements.get(0).getElementsByTag("li");
            for (Element li : lis) {
                Tender tender = new Tender();
                String daytime = li.getElementsByTag("span").text();
                if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                String href = "http://zbjy.ankang.cn/" + li.getElementsByTag("a").attr("href");
                int tit = new BaseDao().selectUrl(href);
                if (tit == 0) {
                    getCont(href,tender,"Contnt");
                    if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                    tender.setFromurl(href);
                    tender.setCatid(1);
                    tender.setStatus(1);
                    tender.setYeWuType("建设工程");
                    tender.setAddress("安康");
                    tender.setFrom("安康市建设工程招投标交易网");
                    GetContentUtil.updateTenderRegionLngLat(tender);
                    System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                    if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                        continue;
                    }
                    GetContentUtil.insertThreadData(tender);
                    tenderList.add(tender);
                }else {
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.info("安康市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    /**
     * 获取正文
     *
     * @param url
     * @return
     */
    public static void getCont(String url, Tender tender, String idStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Element elements = doc.getElementById(idStr);
                String title = doc.getElementById("Title").text();
                if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
                    tender.setTitle(title);
                    if (elements != null) {
                        String content = elements.html().trim();
                        tender.setContent(CompressUtils.compress(content)); //压缩
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
