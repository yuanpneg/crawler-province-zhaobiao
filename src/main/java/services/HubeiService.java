package services;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

/**
 * 湖北省
 */
public class HubeiService {

    private static final Logger LOGGER = Logger.getLogger(HubeiService.class); //使用log4j记录日志

    public List<Tender> getList(String url, FormBody body, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .url(url)
                    .post(body)
                    .build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request);

            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements element = doc.getElementsByClass("newListwenzi");
                Elements elements = element.select("tr");
                for (Element el : elements) {
                    Tender tender = new Tender();
                    el.select("td").get(1).getElementsByTag("span").remove();
                    el.select("td").get(1).getElementsByTag("a").remove();
                    String daytime = el.select("td").get(1).text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "https://www.hbggzyfwpt.cn" + el.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = el.select("a").attr("title"); //标题
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        if (title.indexOf("采购") != -1 || title.indexOf("运行维护") != -1) {
                            continue;
                        }
                        getCont(href, tender, "detail"); //获取正文
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setFrom("湖北省公共资源交易电子服务平台");
                        tender.setFromurl(href);
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
                    } else {
                        break;
                    }
                }
            }else {
                LOGGER.info("湖北 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("湖北：" + city + "列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }


    /**
     * 获取正文内容
     *
     * @param url
     * @param tender
     * @param classStr
     */
    public static void getCont(String url, Tender tender, String classStr) {
        System.setProperty("phantomjs.binary.path", "C:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        WebDriver driver = new PhantomJSDriver();

        try {
            driver.get(url);
            Thread.sleep(3000);
            //WebDriverWait wait = new WebDriverWait(driver, 50);
            //wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));//开始打开网页，等待输入元素出现
            Document doc = Jsoup.parse(driver.getPageSource());
            Element elements = doc.getElementById(classStr);
            String content = elements.html();
            if (elements != null) {
                tender.setContent(CompressUtils.compress(content)); //压缩
            }
        } catch (Exception e) {
            LOGGER.info("湖北 正文获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }finally {
            driver.close();
            driver.quit();
        }
    }
}
