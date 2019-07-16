package services;

import bean.SxJson;
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
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * 山西省
 */
public class SxggzyService {
    /**
     *  使用log4j记录日志
     */
    private static  final Logger LOGGER = Logger.getLogger(SxggzyService.class);


    public List<Tender>  getList(String url, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request);

            if (responseWrap.isSuccess()) {
                Gson gson = new Gson();
                String html = responseWrap.body;
                SxJson sxJson = gson.fromJson(html, SxJson.class);
                List<SxJson.ObjBean> objBeans = sxJson.getObj();
                for (SxJson.ObjBean objBean : objBeans) {
                    Tender tender = new Tender();
                    String daytime = objBean.getRECEIVETIME();
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://prec.sxzwfw.gov.cn/moreInfoController.do?getProjectNodeInfo&url=" + objBean.getURL()+"&id="+objBean.getID();

                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = objBean.getPROJECTNAME();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        //获取正文
                        GetContentUtil.getCont(href, tender, "table table-bordered","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setContent("<table>"+tender.getContent()+"</table>");
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setFrom("全国公共资源交易平台（山西省）");
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
                LOGGER.info("山西 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("山西：" + city + "列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
