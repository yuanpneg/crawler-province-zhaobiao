package services_city;

import bean.DatongJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import org.apache.log4j.Logger;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 大同市
 */
public class DatongService {

    /**
     *  使用log4j记录日志
     */
    private static  final Logger LOGGER = Logger.getLogger(DatongService.class);

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                DatongJson datongJson = gson.fromJson(html,DatongJson.class);
                List<DatongJson.RowsBean> list = datongJson.getRows();
                for (DatongJson.RowsBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getNoticeSendTime();
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.dtggzy.gov.cn/zyjyPortal/portal/tradeEdit?id=" + bean.getId();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getTitle();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        //获取正文
                        GetContentUtil.getContById(href, tender, "div_Descr","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("大同");
                        tender.setFrom("大同市公共资源交易中心");
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
                LOGGER.info("大同市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("大同市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
