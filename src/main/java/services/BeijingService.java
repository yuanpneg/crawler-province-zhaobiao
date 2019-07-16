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

public class BeijingService {

    private static final Logger LOGGER = Logger.getLogger(BeijingService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc= Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("article-list2");
                Elements lis = elements.get(0).select("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("list-times").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "https://www.bjggzyfw.gov.cn" + li.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href, tender, "base1","utf-8"); //获取正文
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setFrom("北京市公共资源交易电子服务平台");
                        tender.setFromurl(href);
                        tender.setAddress("北京");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    } else {
                        break;
                    }
                }

                System.out.println();
            }
        } catch (Exception e) {
            LOGGER.info("北京 列表获取报错");
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
    public static void getContById(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);

                Element elements = doc.getElementById(classStr);
                if (elements != null) {
                    elements.getElementsByClass("ml-list").remove();
                    String content = elements.html().trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
