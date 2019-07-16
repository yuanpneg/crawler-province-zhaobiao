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
 * 玉林市
 */
public class YulinService {

    private static final Logger LOGGER = Logger.getLogger(YulinService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder()
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
                    .header("Host","www.ycggfw.gov.cn")
                    .header("Referer","http://www.ycggfw.gov.cn/TPFront/jyxx/005001/005001001/")
                    .url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("vdot");
                Element tables = elements.get(0).getElementsByTag("tr").get(0).getElementsByTag("td").get(1).getElementsByTag("table").get(2);
                Elements trs = tables.getElementsByTag("tr");
                trs.remove(0);
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.getElementsByTag("td").get(2).text().replace("年", "-")
                            .replace("月", "-").replace("日", "");
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.ylctc.com/" + tr.getElementsByTag("td").get(1).select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.getElementsByTag("td").get(1).select("a").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "b_shet");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress("玉林");
                        tender.setFrom("玉林市建设工程交易中心");
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
                    }else {
                        break;
                    }
                }
            }else{
                LOGGER.info("玉林市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("玉林市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;

    }


    /**
     * 获取正文 class
     *
     * @param url
     * @return
     */
    public static void getCont(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByTag("table");
                if (elements != null) {
                    String content = elements.get(1).getElementsByTag("tbody").html().trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
