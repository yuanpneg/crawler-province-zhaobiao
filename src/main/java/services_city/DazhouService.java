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
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 达州市
 */
public class DazhouService {

    private static final Logger LOGGER = Logger.getLogger(DazhouService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements lis = doc.getElementsByClass("trfont");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByTag("td").get(2).text().replace("[","").replace("]","");
                    if(GetContentUtil.DateDujge(tender,daytime))  break;
                    String href = "http://www.dzggzy.cn" + li.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href, tender, "TDContent"); //获取正文
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("达州");
                        tender.setFrom("达州市公共资源交易网");
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
                LOGGER.info("达州市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("达州市 列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return tenderList;
    }

    /**
     * 获取正文 id
     *
     * @param url
     * @return
     */
    public  void getContById(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Element elements = doc.getElementById(classStr);
                if (elements != null) {
                    String content = elements.html().trim();
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                List<File> fileList = fileinfo(doc);//获取附件
                if (fileList.size() != 0) {
                    String fileinfo = gson.toJson(fileList);
                    tender.setFileinfo(fileinfo);//附件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
     *获取附件的方法
     */
    public  List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Element flo = doc.getElementById("filedown");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf", "rar"};
        if (flo != null) {
            Elements lis = flo.select("tr");
            if (lis.size() > 0) {
                for (Element li : lis) {
                    File file = new File();
                    String wUrl = "http://www.dzggzy.cn" + li.select("a").attr("href");
                    String filetitle = li.select("a").text();
                    String[] hrs = filetitle.split(Pattern.quote("."));
                    String filetype = hrs[hrs.length - 1];
                    for (String s : str) {
                        if (s.equals(filetype)) {
                            file.setFiletitle(filetitle);//附件名
                            file.setFiletype(filetype);
                            file.setFileurl(wUrl);
                            fileList.add(file);
                        }
                    }
                }
            }
        }
        return fileList;
    }
}
