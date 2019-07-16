package services_city;

import bean.ShuozhouJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import org.apache.log4j.Logger;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 朔州市
 */
public class ShuozhouService {

    private static  final Logger LOGGER = Logger.getLogger(ShuozhouService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                ShuozhouJson shuozhouJson = gson.fromJson(html,ShuozhouJson.class);
                List<ShuozhouJson.ObjBean> list = shuozhouJson.getObj();
                for (ShuozhouJson.ObjBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getRECEIVETIME();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://szggzy.shuozhou.gov.cn/moreInfoController.do?getNoticeDetail&url="+bean.getURL()+"&id="+bean.getID();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getPROJECTNAME();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href, tender, "body_main","utf-8"); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("朔州");
                        tender.setFrom("朔州市公共资源交易中心");
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
            }else{
                LOGGER.info("朔州市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("朔州市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
