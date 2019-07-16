package services;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 湖南省
 */
public class HunanService {

    private static  final Logger LOGGER = Logger.getLogger(HunanService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @param category
     * @return
     */
    public List<Tender> getList(String url, String category, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements element = doc.getElementsByClass("article-list2");
                Elements lis = element.select("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String href = li.getElementsByClass("article-list3-t").select("a").attr("href");
                    String daytime = li.getElementsByClass("list-times").text(); //获取时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getCont(href, tender);
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setYeWuType(category);
                        tender.setFrom("全国公共资源交易平台（湖南省）");
                        tender.setFromurl(href);
                        tender.setAddress(city);
                        int catid = 0;
                        if ("建设工程".equals(category)) {
                            catid = 1;
                        } else if ("水利工程".equals(category)) {
                            catid = 2;
                        } else if ("交通工程".equals(category)) {
                            catid = 8;
                        }
                        tender.setCatid(catid);
                        tender.setStatus(1);
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

            }else {
                LOGGER.info("湖南 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("湖南：" + city+"列表获取报错");
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
    public void getCont(String url, Tender tender) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("content");
                if (elements != null) {
                    String title = elements.get(0).getElementsByClass("content-title").text();  //标题
                    if (new BaseDao().selectTitle(title) == 0) {
                        tender.setTitle(title);
                        String strHtml = elements.get(0).getElementsByClass("div-article2").html();
                        String content = html_encode(strHtml);
                        tender.setContent(CompressUtils.compress(content)); //压缩
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("湖南：获取正文报错" );
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }

    public static String html_encode(String strHtml){
        String content = "";
        if(strHtml.length() == 0) return content;
        content = strHtml.replace("&gt;",">");
        content = content.replace("&lt;","<");
        return content;
    }
}
