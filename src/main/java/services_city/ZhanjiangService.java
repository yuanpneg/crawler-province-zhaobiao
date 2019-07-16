package services_city;

import bean.Tender;
import bean.ZhanjiangJson;
import bean.ZhanjiangcontentJson;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;

import org.apache.log4j.Logger;

import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 湛江市
 */
public class ZhanjiangService {

    private static final Logger LOGGER = Logger.getLogger(ZhanjiangService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();

        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                ZhanjiangJson zhanjiangJson = gson.fromJson(html,ZhanjiangJson.class);
                List<ZhanjiangJson.RowsBean> list = zhanjiangJson.getRows();
                for (ZhanjiangJson.RowsBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getStrFaBuQiShiShiJian();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://ggfw.zjprtc.com/jsgc_Message/getZhaoBiaoGongGaoByGuid.do?zhaoBiaoGongGaoGuid="+bean.getZhaoBiaoGongGaoGUID()
                            + "&xinXiType=1";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getZhaoBiaoGongGaoTitle();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href,tender);
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("湛江");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("湛江市公共资源交易网");
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
                LOGGER.info("湛江市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("湛江市 列表获取报错");
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
    public static void getCont(String url, Tender tender) {
        try {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders()).url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                ZhanjiangcontentJson json = gson.fromJson(html,ZhanjiangcontentJson.class);
                String content = json.getContentValue();
                if(!"".equals(content)){
                    tender.setContent(CompressUtils.compress(content));
                }
            }
        }catch (Exception e) {
            LOGGER.info("湛江市：正文获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }
}
