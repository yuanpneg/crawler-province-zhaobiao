package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 阳江市
 */
public class YangjiangService {
    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(YangzhouService.class);

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("list");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.select("span").text().replace("/","-");
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.yjggzy.cn" + li.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        GetContentUtil.getCont(href,tender,"acticlecontent","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50){
                            GetContentUtil.getContById(href,tender,"nr","utf-8");
                            if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        };
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("阳江");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setTitle(title);
                        tender.setFrom("阳江市公共资源交易网");
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    }else{
                        break;
                    }
                }
            }else{
                LOGGER.info("阳江市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("阳江市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }


}
