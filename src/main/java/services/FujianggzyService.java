package services;

import bean.FujianContentJson;
import bean.FujianJson;
import bean.Tender;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;

import org.apache.log4j.Logger;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.*;

/**
 * 福建省
 */
public class FujianggzyService {

    private static  final Logger LOGGER = Logger.getLogger(FujianggzyService.class); //使用log4j记录日志

    public List<Tender> getList(String url, FormBody body,String cookie, String city) {

        List<Tender> listTender = new ArrayList<>();
        try {

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .header("Cookie", cookie)
                    .url(url)
                    .post(body)
                    .build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request);

            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                FujianJson fujianJson = gson.fromJson(html, FujianJson.class);
                List<FujianJson.DataBean> list = fujianJson.getData();
                for (FujianJson.DataBean bean : list) {
                    Tender tender = new Tender();
                    String title = bean.getNAME();
                    if (title.indexOf("采购") != -1) {
                        continue;
                    }
                    title = title.replace("-招标公告","");
                    String daytime = bean.getTM().split("T")[0];
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "https://www.fjggfw.gov.cn/Website/AjaxHandler/BuilderHandler.ashx?OPtype=GetGGInfoPC&ID=" + String.valueOf(bean.getM_ID()).replace(".0", "") + "&GGTYPE=1&url=AjaxHandler%2FBuilderHandler.ashx";

                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        if(new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender,cookie); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setFrom("福建省公共资源交易电子公共服务平台");
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
                    } else {
                        break;
                    }
                }
            } else {
                LOGGER.info("福建 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("福建：" + city+"列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return  listTender;

    }


    public void getCont(String url, Tender tender,String  cookie) {

        try {
            Request request = new Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .header("Cookie", cookie)
                    .url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                FujianContentJson fujianContentJson = gson.fromJson(html, FujianContentJson.class);
                List<String> elements = fujianContentJson.getData();
                if (elements.size() > 0) {
                    String content = elements.get(0);
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }

            }

        } catch (Exception e) {
            LOGGER.info("福建 获取正文报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }

}
