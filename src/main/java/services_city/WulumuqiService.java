package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 乌鲁木齐
 */
public class WulumuqiService {

    private static  final Logger LOGGER = Logger.getLogger(WulumuqiService.class); //使用log4j记录日志

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
                String htmllist = GetContentUtil.unicodeToString(html);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    String urlid = htmllist.split("s" + i + "\\['FILE_ID'\\]=")[1].split(";s" + i)[0].replace("\"", "");
                    list.add(urlid);
                }
                for (String s : list) {
                    Tender tender = new Tender();
                    String href = "http://ggzy.wlmq.gov.cn/infopublish.do?method=infoPublishView&infoid=" + s;
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        Request requestCont = new Request.Builder().url(href).build();
                        HttpUtils.ResponseWrap responseWrapCon = HttpUtils.retryHttpNoProxy(requestCont, "gbk");
                        if (responseWrapCon.isSuccess()) {
                            String htmlCont = responseWrapCon.body;
                            Document doc = Jsoup.parse(htmlCont);
                            Elements elements = doc.getElementsByClass("w_content_main");
                            String daytime = elements.get(0).getElementsByClass("time").get(0)
                                    .getElementsByClass("td_name").get(2).text().replace("发布日期：", "").split(" ")[0];
                            if (GetContentUtil.DateDujge(tender, daytime)) break;
                            String title = elements.get(0).getElementsByClass("title").text();
                            if(new BaseDao().selectTitle(title) != 0) continue;
                            //elements.select("a").remove();
                            String content = elements.get(0).getElementsByClass("content886").html().split("<br>")[0];
                            tender.setContent(CompressUtils.compress(content));
                            if ("".equals(content) || null == content || tender.getContent().length() < 50) continue;
                            //压缩
                            tender.setTitle(title);
                            tender.setYeWuType("建设工程");
                            tender.setFrom("乌鲁木齐公共资源交易网");
                            tender.setFromurl(href);
                            tender.setAddress("乌鲁木齐");
                            tender.setCatid(1);
                            tender.setStatus(1);
                            GetContentUtil.updateTenderRegionLngLat(tender);
                            System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                            if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                                continue;
                            }
                            GetContentUtil.insertThreadData(tender);
                            tenderList.add(tender);
                        }
                    }else {
                        break;
                    }
                }

            }else{
                LOGGER.info("乌鲁木齐 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("乌鲁木齐 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;

    }
}
