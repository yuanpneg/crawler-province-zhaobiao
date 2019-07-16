package services;

import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;

import bean.JsggzyBean;

import org.apache.log4j.Logger;
import utils.GetContentUtil;


import java.util.ArrayList;

import java.util.List;

/**
 * 江苏省
 */
public class JsggzyService {

    //使用log4j记录日志
    private static  final Logger LOGGER = Logger.getLogger(JsggzyService.class);

    public List<Tender> getList(String url, RequestBody requestBody) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).post(requestBody).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                JsggzyBean jsggzyBean = gson.fromJson(html, JsggzyBean.class);
                List<JsggzyBean.ResultBean.RecordsBean> recordsBean = jsggzyBean.getResult().getRecords();
                for (JsggzyBean.ResultBean.RecordsBean bean : recordsBean) {
                    Tender tender = new Tender();
                    String city = bean.getFieldvalue();
                    String daytime = bean.getInfodateformat();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String dayid = daytime.replace("-", "");
                    String href = "http://jsggzy.jszwfw.gov.cn/jyxx/003001/003001001/" + dayid + "/" + bean.getInfoid() + ".html";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getTitle();
                        if (new BaseDao().selectTitle(bean.getTitle()) != 0) continue;
                        GetContentUtil.getCont(href, tender, "ewb-trade-right","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom("全国公共资源交易平台（江苏地区）");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        //正文链接
                        tender.setFromurl(href);
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
            }
        } catch (Exception e) {
            LOGGER.info("江苏 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }


}
