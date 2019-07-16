package services_city;

import bean.LianyungangJson;
import bean.Tender;
import com.gargoylesoftware.htmlunit.javascript.host.TextDecoder;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import services.FujianggzyService;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 连云港
 */
public class LianyungangService {

    private static final Logger LOGGER = Logger.getLogger(LianyungangService.class); //使用log4j记录日志

    public List<Tender> getlist(String url, FormBody body, String category, int catid) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .url(url)
                    .post(body)
                    .build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = GetContentUtil.unicodeToString(responseWrap.body);
                String json = html.replace("\\", "").split("\"custom\":\"")[1].split("\",\"status\"")[0];
                Gson gson = new Gson();
                LianyungangJson lianyungangJson = gson.fromJson(json, LianyungangJson.class);
                List<LianyungangJson.TableBean> list = lianyungangJson.getTable();
                for (LianyungangJson.TableBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getPostdate();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://spzx.lyg.gov.cn/lygweb" +bean.getHref();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getTitle();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "con"); //获取正文
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setYeWuType(category);
                        tender.setFrom("连云港公共资源交易网");
                        tender.setFromurl(href);
                        tender.setAddress("连云港");
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

            } else {
                LOGGER.info("连云港 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("连云港 列表获取报错");
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
            Thread.sleep(1000);
            //WebDriverWait wait = new WebDriverWait(driver, 50);
            //wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));//开始打开网页，等待输入元素出现
            Document doc = Jsoup.parse(driver.getPageSource());
            Element elements = doc.getElementsByClass(classStr).get(0);
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
