package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo1.File;
import utils.CompressUtils;
import utils.GetContentUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 沈阳市
 */
public class ShenyangService {

    //使用log4j记录日志
    private static final Logger LOGGER = Logger.getLogger(ShenyangService.class);

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
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("article-list3-t");
                for (Element element : elements) {
                    Tender tender = new Tender();
                    String daytime = element.getElementsByClass("list-times").text();
                    //if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = element.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getCont(href, tender, "content-article");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("沈阳");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("沈阳市公共资源监督管理信息网");
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
            }else{
                LOGGER.info("沈阳市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("沈阳市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    /**
     * 获取正文 class
     *
     * @param url
     * @return
     */
    public static void getCont(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                String title = doc.getElementsByClass("content-title").get(0).text();
                if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
                    tender.setTitle(title);
                    Elements elements = doc.getElementsByClass(classStr);
                    if (elements != null) {
                        String content = elements.html().trim();
                        tender.setContent(CompressUtils.compress(content)); //压缩
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *获取附件的方法
     */
//    public List<File> fileinfo(String html) {
//        List<File> fileList = new ArrayList<>();
//        Document doc = Jsoup.parse(html);
//        Element flo = doc.getElementById("filedown");
//        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
//        if (flo != null) {
//            if (flo.select("tr").size() > 2) {
//                File file = new File();
//                String wUrl = flo.select("a").attr("href");
//                String filetitle = flo.select("tr").get(2).text();
//                wUrl = "http://www.xzggzy.com.cn" + wUrl;
//                String[] hrs = filetitle.split(Pattern.quote("."));
//                String filetype = hrs[hrs.length - 1];
//                for (String s : str) {
//                    if (s.equals(filetype)) {
//                        file.setFiletitle(filetitle);//附件名
//                        file.setFiletype(filetype);
//                        file.setFileurl(wUrl);
//                        fileList.add(file);
//                    }
//                }
//            }
//        }
//        return fileList;
//    }

}
