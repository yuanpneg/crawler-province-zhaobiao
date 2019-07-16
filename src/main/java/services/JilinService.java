package services;

import bean.JilinJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.log4j.Logger;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 吉林省
 */
public class JilinService {

    private static  final Logger LOGGER = Logger.getLogger(JilinService.class); //使用log4j记录日志

    public List<Tender> getList(String url, String city) {

        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                String json = html.split("result")[1].replaceFirst("\\(", "").replace(");","");
                JilinJson jilinJson = gson.fromJson(json,JilinJson.class);
                List<JilinJson.DatasBean> list = jilinJson.getDatas();
                for (JilinJson.DatasBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getTimestamp().split(" ")[0].replace(".","-");
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = bean.getDocpuburl();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        if (new BaseDao().selectTitle(bean.getTitle()) != 0) continue;
                        GetContentUtil.getCont(href,tender,"ewb-article-info","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(bean.getTitle());
                        tender.setYeWuType("建设工程");
                        tender.setFrom("吉林省公共资源交易公共服务平台");
                        tender.setFromurl(href);
                        if("延边自治州".equals(city)){
                            city = "延边";
                        }
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
            }else {
                LOGGER.info("吉林 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("吉林：" + city + "列表获取报错");
            e.printStackTrace();
        }
        return tenderList;
    }
}
