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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 临沂市
 */
public class LinyiService {

    private static final Logger LOGGER = Logger.getLogger(LinyiService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select(".ewb-build-items");
                Elements lis = elements.select("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.select(".ewb-ndate").text();//时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    //获取正文
                    String urlday = daytime.replace("-", "");
                    String href = li.select("a").attr("href");
                    href = href.replace("/TPFront/infodetail/?InfoID=", "").replace("&CategoryNum=074001001001", "");
                    href = "http://ggzyjy.linyi.gov.cn/TPFront/InfoContent/" + urlday + "/074001001001_" + href + "_0.htm";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender);
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setFromurl(href);
                        tender.setTitle(title);
                        tender.setAddress("临沂");
                        tender.setYeWuType("建设工程");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("临沂市公共资源交易网");
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
                LOGGER.info("临沂市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("临沂市 列表获取报错");
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
    private void getCont(String url, Tender tender) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                if (doc != null) {
                    doc.select("link").remove();
                    doc.select("script").remove();
                    Elements elements = doc.select("body");
                    String content = elements.html().trim();
                    content = content.replace("\uFEFF", "");
                    if (content != null) {
                        tender.setContent(CompressUtils.compress(content)); //压缩
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("临沂市 正文获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }
}
