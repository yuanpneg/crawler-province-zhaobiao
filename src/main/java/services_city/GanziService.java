package services_city;

import bean.GanziJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import org.apache.log4j.Logger;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 甘孜州
 */
public class GanziService {

    private static final Logger LOGGER = Logger.getLogger(GanziService.class); //使用log4j记录日志

    public List<Tender> getList(String url, FormBody body) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .url(url)
                    .post(body)
                    .build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                GanziJson ganziJson = gson.fromJson(html,GanziJson.class);
                List<GanziJson.DataBean.ContentBean> list = ganziJson.getData().getContent();
                for (GanziJson.DataBean.ContentBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getMckeys();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://jypt.scgzzg.com:88/pub/GZ_indexContent_"+bean.getId()+".html";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getMctype();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href, tender, "text-wrap","utf-8"); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("甘孜");
                        tender.setFrom("甘孜州公共资源交易中心");
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
                LOGGER.info("甘孜州 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("甘孜州 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
