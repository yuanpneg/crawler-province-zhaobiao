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
 * 衡阳市
 */
public class HengyangService {

    private static final Logger LOGGER = Logger.getLogger(HengyangService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("contentText");
                Elements lis = elements.get(0).getElementsByClass("nyLine");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("titleDate").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = li.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getCont(href,tender,"article");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                        tender.setYeWuType("建设工程");
                        tender.setAddress("衡阳");
                        tender.setFrom("衡阳市公共资源交易网");
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
                    }else{
                        break;
                    }
                }
            }else {
                LOGGER.info("衡阳市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("衡阳市 请求列表报错");
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
    public static void getCont(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass(classStr);
                String title = doc.getElementsByClass("finalTitle").select("h3").text();
                if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
                    tender.setTitle(title);
                    if (elements != null) {
                        elements.select("a").remove();
                        elements.select("script").remove();
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
