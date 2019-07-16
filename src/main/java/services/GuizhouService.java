package services;

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
 * 贵州省
 */
public class GuizhouService {


    private static final Logger LOGGER = Logger.getLogger(GuizhouService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {

                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Element element = doc.getElementById("news_news");
                Elements lis = element.getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String href = "http://www.gzjyfw.gov.cn/G2/project/" + li.select("a").attr("href");
                    String daytime = li.getElementsByClass("times").get(0).text().split(" ")[0];
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href, tender, "utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom("贵州公共资源交易公共服务平台");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFromurl(href);//正文链接
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
            } else {
                LOGGER.info("贵州 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("贵州：" + city);
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }


    /**
     * 获取正文 id
     *
     * @param url
     * @return
     */
    public static void getContById(String url, Tender tender, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);

                Element elements = doc.getElementById("s-0");
                Element element = doc.getElementById("s-1");
                if (elements != null) {
                    //elements.select("a").remove();
                    //elements.select("img").remove();
                    String content = elements.html().trim()+ element.html();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
