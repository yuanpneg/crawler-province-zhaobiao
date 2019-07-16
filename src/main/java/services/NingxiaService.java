package services;

import bean.NingxiaJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 宁夏省
 */
public class NingxiaService {

    private static  final Logger LOGGER = Logger.getLogger(NingxiaService.class); //使用log4j记录日志

    public List<Tender> getList(String url, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                String json = html.substring(11).substring(0, html.substring(11).length() - 2).replace("\\", "");
                NingxiaJson ningxiaJson = gson.fromJson(json, NingxiaJson.class);
                List<NingxiaJson.TableBean> list = ningxiaJson.getTable();
                for (NingxiaJson.TableBean bean : list) {
                    Tender tender = new Tender();
                    //Calendar date = Calendar.getInstance();
                    //String year = String.valueOf(date.get(Calendar.YEAR));
                    String daytime =  bean.getInfodate();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String dayid = daytime.replace("-", "");
                    String href = "http://www.nxggzyjy.org/ningxiaweb/002/002001/002001001/" + dayid + "/" + bean.getInfoid() + ".html";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getCont(href, tender, "epoint-article-content"); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setYeWuType("建设工程");
                        tender.setFrom("全国公共资源交易平台（宁夏回族自治区）");
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
                LOGGER.info("宁夏 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("宁夏：" + city+"列表获取报错");
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
    public static void getCont(String url, Tender tender, String classStr) {
        Request request = new Request.Builder().url(url).build();
        HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
        if (responseWrap.isSuccess()) {
            String html = responseWrap.body;
            Document doc = Jsoup.parse(html);
            try {
                Elements elements = doc.getElementsByClass(classStr);
                if (elements != null) {
                    String title = doc.getElementsByClass("ewb-main-h2").get(0).text();
                    if (new BaseDao().selectTitle(title) == 0) {
                        tender.setTitle(title);
                        elements.select("a").remove();
                        elements.select("img").remove();
                        String content = elements.html().trim();
                        tender.setContent(CompressUtils.compress(content)); //压缩
                    }
                }
            } catch (Exception e) {
                LOGGER.info("宁夏：获取正文报错");
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
