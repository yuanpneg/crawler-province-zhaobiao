package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
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
 * 林芝市
 */
public class LinzhiService {

    private static final Logger LOGGER = Logger.getLogger(LinzhiService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);

                Elements elements = doc.getElementsByClass("col-full02");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String href = "http://www.lzcs.gov.cn" + li.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "content04");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("林芝");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("西藏自治区林芝市住房和城乡建设局");
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    } else {
                        break;
                    }
                }
            }else{
                LOGGER.info("林芝市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("林芝市 列表获取报错");
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
    public static void getCont(String url, Tender tender, String str) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass(str);
                String daytime = doc.getElementsByTag("h3").text().split(" ")[0].trim();
                if (GetContentUtil.DateDujge(tender, daytime) == false) {
                    //判断时间
                    String title = doc.getElementsByTag("h1").text();
                    if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
                        tender.setTitle(title);
                        if (elements != null) {
                            elements.select("h1").remove();
                            elements.select("h3").remove();
                            elements.select("h2").remove();
                            elements.get(0).getElementsByClass("related").remove();
                            String content = elements.html().trim();
                            tender.setContent(CompressUtils.compress(content)); //压缩
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
