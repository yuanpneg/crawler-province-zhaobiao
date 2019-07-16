package services_city;

import bean.LoudiJson;
import bean.Tender;
import com.gargoylesoftware.htmlunit.javascript.host.speech.SpeechSynthesisUtterance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo1.File;
import utils.GetContentUtil;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 娄底市
 */
public class LoudiService {

    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(LoudiService.class);

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
                Gson gson = new Gson();
                LoudiJson loudiJson = gson.fromJson(html,LoudiJson.class);
                List<LoudiJson.DataBean> list = loudiJson.getData();
                for (LoudiJson.DataBean li : list) {
                    Tender tender = new Tender();
                    String daytime = li.getPublishTime();
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String a = li.getNoticeTitle();
                    Document doc = Jsoup.parse(a);
                    String href = "http://ldggzy.hnloudi.gov.cn" + doc.getElementsByTag("a").attr("href");
                    String urls = "";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = doc.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0 && !"".equals(title)) continue;
                        getContByClass(href,tender,"td-2 td-article","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("娄底");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("娄底市公共资源交易网");
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                            continue;
                        }
                        //GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    }else {
                        break;
                    }
                }
            }else{
                LOGGER.info("娄底市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("娄底市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }



    /**
     * 获取正文
     *
     * @param href
     * @return
     */
    public  void getContByClass(String href, Tender tender,String classStr, String charset) {
        try {
            String url = href.replace("ViewsPage","zbggShow").split("type")[0]+"type=1";
            System.out.println(url);
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass(classStr);
                if (elements != null) {
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    //获取附件
                    List<File> fileList = fileinfo(doc);
                    if (fileList.size() != 0) {
                        String fileinfo = gson.toJson(fileList);
                        //附件
                        tender.setFileinfo(fileinfo);
                    }
                    elements.get(0).select("a").remove();
                    elements.get(0).select("img").remove();
                    String content = elements.html().trim();
                    content.replaceAll("附件：","");
                    //压缩
                    tender.setContent(content);
                }
            }
        }catch (Exception e) {
            LOGGER.info("娄底：获取正文错误");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }

    /*
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByClass("td-2");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo.size() > 0) {
            Elements as = flo.select("a");
            for (Element li : as) {
                File file = new File();
                String wUrl = li.select("a").attr("href");
                if(!wUrl.equals("")) {
                    String filetitle = li.select("a").text();
                    String[] hrs = filetitle.split(Pattern.quote("."));
                    String filetype = hrs[hrs.length - 1];
                    for (String s : str) {
                        if (s.equals(filetype)) {
                            file.setFiletitle(filetitle);//附件名
                            file.setFiletype(filetype);
                            file.setFileurl(wUrl);
                            fileList.add(file);
                            break;
                        }
                    }
                }
            }
        }
        return fileList;
    }
}
