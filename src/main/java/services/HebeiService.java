package services;

import bean.HebeiJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/*
 *河北省
 */
public class HebeiService {

    private static  final Logger LOGGER = Logger.getLogger(HebeiService.class); //使用log4j记录日志


    public List<Tender> getList(String url,String city, RequestBody requestBody) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().header("Accept","application/json, text/javascript, */*; q=0.01")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36")
                    .header("Referer","http://www.hebpr.cn/jyggiframe.html?GG_CITY=&GG_GOUNTY=&GG_CATETOP=").url(url).post(requestBody).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                HebeiJson hebeiJson = gson.fromJson(html,HebeiJson.class);
                List<HebeiJson.ResultBean.RecordsBean> list = hebeiJson.getResult().getRecords();
                for(HebeiJson.ResultBean.RecordsBean bean: list){
                    Tender tender = new Tender();
                    String daytime = bean.getInfodate().split(" ")[0];
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.hebpr.cn" + bean.getLinkurl();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title =bean.getTitle();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "hideDeil");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom("全国公共资源交易平台（河北省）");
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
                    } else {
                        break;
                    }

                }
            } else {
                LOGGER.info("河北 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("河北：" + city+"列表获取报错");
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
    public static void getCont(String url, Tender tender, String idStr) {
        Request request = new Request.Builder().url(url).build();
        HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
        if (responseWrap.isSuccess()) {
            String html = responseWrap.body;
            Document doc = Jsoup.parse(html);
            try {
                Element elements = doc.getElementById(idStr);
                if (elements != null) {
                    elements.select("a").remove();
                    elements.select("img").remove();
                    String content = elements.html().replace("display:none","").trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            } catch (Exception e) {
                LOGGER.info("连云港获取正文报错");
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
