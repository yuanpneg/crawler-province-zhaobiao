package services;

import bean.Tender;;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import dao.BaseDao;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.CompressUtils;
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.List;

public class LiaoningService {

    private static  final Logger LOGGER = Logger.getLogger(LiaoningService.class); //使用log4j记录日志


    public List<Tender> getList(HtmlPage page,  String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            String html = page.asXml();
            Document doc = Jsoup.parse(html);
            Element element = doc.getElementById("MoreInfoListjyxx1_tdcontent");
            Elements elements = element.getElementsByClass("publicont");
            for(Element el : elements){
                Tender tender = new Tender();
                String daytime = el.getElementsByClass("span_o").text();
                if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                String href = "http://www.lnggzy.gov.cn" + el.select("a").attr("href");
                int tit = new BaseDao().selectUrl(href);
                if (tit == 0) {
                    String title = el.select("a").attr("title");
                    if (new BaseDao().selectTitle(title) != 0) continue;
                    getCont(href, tender);
                    if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                    tender.setYeWuType("建设工程");
                    tender.setFrom("全国公共资源交易平台（辽宁省）");
                    tender.setFromurl(href);
                    tender.setTitle(title);
                    tender.setAddress(city);
                    tender.setCatid(1);
                    tender.setStatus(1);
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

        }catch (Exception e) {
            LOGGER.info("辽宁：" + city+"列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }



    public void getCont(String url, Tender tender) {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setDownloadImages(false);

        try{
            HtmlPage page = webClient.getPage(url);
            String html = page.asXml();
            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByClass("xsxg");
            if (elements != null) {
                elements.select("a").remove();
                elements.select("img").remove();
                String content = elements.html().trim().replaceAll("暂时没有信息！","");
                tender.setContent(CompressUtils.compress(content)); //压缩
            }

        }catch (Exception e) {
            LOGGER.info("辽宁：获取正文报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }
}
