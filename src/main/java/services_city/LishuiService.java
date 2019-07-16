package services_city;

import bean.Tender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo1.File;
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 丽水市
 */
public class LishuiService {

    private static final Logger LOGGER = Logger.getLogger(LishuiService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gb2312");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("contentall");
                Elements tables = elements.get(0).getElementsByTag("table");
                Elements trs;

                if(tables.size() <= 7){
                    trs = tables.get(5).getElementsByTag("tr");
                }else {
                    trs = tables.get(8).getElementsByTag("tr");
                }
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.getElementsByTag("td").get(2).text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.lssggzy.com" + tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href,tender,"TDContent","gb2312");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("丽水");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("丽水市公共资源交易网");
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
                LOGGER.info("丽水市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("丽水市 列表获取报错");
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
    public  void getContById(String url, Tender tender,String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Element elements = doc.getElementById(classStr);
                if (elements != null) {
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    List<File> fileList = fileinfo(doc);//获取附件
                    if (fileList.size() != 0) {
                        String fileinfo = gson.toJson(fileList);
                        tender.setFileinfo(fileinfo);//附件
                    }
                    elements.select("a").remove();
                    elements.select("img").remove();
                    String content = elements.html().trim();
                    tender.setContent(content); //压缩
                }
            }
        }catch (Exception e) {
            LOGGER.info("丽江：获取正文错误");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }

    /*
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Element flo = doc.getElementById("filedown");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (null != flo) {
            Elements as = flo.select("tr");
            for (Element li : as) {
                File file = new File();
                String wUrl = "http://www.lssggzy.com" + li.select("a").attr("href");
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
