package services;

import bean.HnJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 河南省
 */
public class HnggzyService {

    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(HnggzyService.class);

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body.toString();
                Gson gson = new Gson();

                String json = html.substring(11).substring(0, html.substring(11).length() - 2).replace("\\", "");

                HnJson hnJson = gson.fromJson(json, HnJson.class);

                List<HnJson.TableBean> list = hnJson.getTable();
                for (HnJson.TableBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getInfodate();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.hnsggzyfwpt.gov.cn" + bean.getHref();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        if (new BaseDao().selectTitle(bean.getTitle()) != 0) continue;
                        tender.setTitle(bean.getTitle());
                        //获取正文
                        GetContentUtil.getCont(href, tender, "ewb-left-bd","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                        tender.setFrom("全国公共资源交易平台（河南省）");
                        tender.setAddress(bean.getInfoc());
                        tender.setCatid(1);
                        tender.setStatus(1);
                        //正文链接
                        tender.setFromurl(href);
                        //System.out.println(href+bean.getInfoc());
                        tender.setYeWuType("建设工程");
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
                LOGGER.info("河南 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("河南 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }


}
