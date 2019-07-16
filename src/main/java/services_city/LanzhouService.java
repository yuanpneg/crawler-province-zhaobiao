package services_city;

import bean.LanzhouJson;
import bean.Tender;
import com.google.gson.Gson;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import services.HubeiService;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 兰州市
 */
public class LanzhouService {

    private static final Logger LOGGER = Logger.getLogger(LanzhouService.class); //使用log4j记录日志

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
                String html = GetContentUtil.unicodeToString(responseWrap.body);
                Gson gson = new Gson();
                String json = html.replace("\\","").split("custom\":\"")[1].split("\",\"status\"")[0];
                LanzhouJson lanzhouJson = gson.fromJson(json,LanzhouJson.class);
                List<LanzhouJson.TableBean> list = lanzhouJson.getTable();
                for (LanzhouJson.TableBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getInfodate();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String id = daytime.replaceAll("-","");
                    String num = "";
                    if("014001001".equals(bean.getCategorynum())){
                        num = "xqfzx";
                    }else if("002001001".equals(bean.getCategorynum())){
                        num = "jygk";
                    }
                    String href = "http://lzggzyjy.lanzhou.gov.cn/"+num+"/"+bean.getCategorynum().substring(0,bean.getCategorynum().length()-3)
                            +"/"+bean.getCategorynum()+"/"+ id + "/" + bean.getInfoid()+".html";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = bean.getTitle2();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href, tender, "contents","utf-8"); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("兰州");
                        tender.setFrom("兰州市公共资源交易中心");
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
                LOGGER.info("兰州市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("兰州市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }
}
