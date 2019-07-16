package services_city;


import bean.JiuquanJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.apache.log4j.Logger;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

import static bean.JiuquanJson.*;

/**
 * 酒泉市
 */
public class JiuquanService {
    private static final Logger LOGGER = Logger.getLogger(LiaochengService.class); //使用log4j记录日志

    public List<Tender> getList(String url, FormBody body) {
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
                String htm = html.split("\"custom\":\"")[1].split("\",\"status\"")[0];
               String htmls = htm.replaceAll("\\\\\"","\"");
                Gson gson = new Gson();
                JiuquanJson jiuquanJson = gson.fromJson(htmls,JiuquanJson.class);
                List<JiuquanJson.TableBean> list = jiuquanJson.getTable();
                for (TableBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getInfodate();
                    if(GetContentUtil.DateDujge(tender,daytime)) break;
                    String href = "http://www.ggzyjypt.com.cn" + bean.getHref();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String categoryname = bean.getCategoryname();
                        if("中标候选人公示".equals(categoryname) || "结果公示".equals(categoryname) || "中标通知书".equals(categoryname)) continue;
                        String title = bean.getTitle();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getContById(href, tender, "bwp2","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("酒泉");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("酒泉市公共资源交易中心");
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
            } else {
                LOGGER.info("酒泉市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("酒泉市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
