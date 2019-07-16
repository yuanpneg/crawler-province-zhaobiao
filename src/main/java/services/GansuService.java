package services;

import bean.GansuContentJson;
import bean.Tender;
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
import sun.nio.cs.UnicodeEncoder;
import utils.CompressUtils;
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 甘肃省
 */
public class GansuService {

    private static final Logger LOGGER = Logger.getLogger(GansuService.class); //使用log4j记录日志

    public List<Tender> getList(String url, FormBody body, String city) {
        List<Tender> listTender = new ArrayList<>();
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
                Elements elements = doc.getElementsByClass("trad-sear-con").select("li");
                for (Element el : elements) {
                    Tender tender = new Tender();

                    String daytime = el.getElementsByTag("span").text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.gsggfw.cn" + el.getElementsByTag("a").attr("onclick")
                            .replace("location.href=", "").replace("'", "");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = el.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "arti-biao-cont"); //获取正文
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setFrom("全国公共资源交易平台（甘肃省）");
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
                        listTender.add(tender);
                    }else {
                        break;
                    }
                }

            } else {
                LOGGER.info("甘肃"+ city +" 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("甘肃：" + city + "列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return listTender;
    }


    /**
     * 获取正文
     *
     * @param url
     * @return
     */
    public static void getCont(String url, Tender tender, String classStr) {
        Request request = new Request.Builder().url(url).build();
        HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
        if (responseWrap.isSuccess()) {
            String html = responseWrap.body;
            Document doc = Jsoup.parse(html);
            try {
                Elements elements = doc.getElementsByClass(classStr);
                String content = "";
                if (!"".equals(elements.get(0).getElementsByTag("iframe").attr("src"))) {
                    String href = elements.get(0).getElementsByTag("iframe").attr("src")
                            .replace("webgonggao_Detail?", "webgonggao_DetailAction.action?cmd=page_Load&") + "&isCommondto=true";
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    okhttp3.Request requestCont = new okhttp3.Request.Builder()
                            .headers(HttpUtils.getCommonHeaders())
                            .url(href)
                            .build();
                    HttpUtils.ResponseWrap responseWrapCont = HttpUtils.retryHttpNoProxy(requestCont);
                    if (responseWrapCont.isSuccess()) {
                        String htmlCont = responseWrapCont.body;
                        String json = GetContentUtil.unicodeToString(htmlCont);
                        Gson gson = new Gson();
                        json.replace("\\", "");
                        GansuContentJson gansuContentJson = gson.fromJson(json, GansuContentJson.class);
                        content = gansuContentJson.getCustom().getGonggaocontent();
                    }
                } else {
                    content = elements.get(0).html();
                }

                tender.setContent(CompressUtils.compress(content)); //压缩

            } catch (Exception e) {
                LOGGER.info("甘肃 获取正文报错");
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

    }


}
