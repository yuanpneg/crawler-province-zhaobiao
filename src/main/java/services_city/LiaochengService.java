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
 * 聊城市
 */
public class LiaochengService {

    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(LiaochengService.class);

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gb2312");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("content");
                Elements trs = elements.get(0).getElementsByTag("table").select("tr");
                if (trs.size()>0) {
                    trs.remove(trs.size() - 1);
                }
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.getElementsByTag("td").get(2).text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.lcsggzyjy.cn" + tr.getElementsByTag("td").get(1).select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.getElementsByTag("td").get(1).select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender, "ZtbzbggDetail_jsgc1_content");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setFromurl(href);
                        tender.setTitle(title);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("聊城");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("聊城市公共资源交易中心");
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
            } else {
                LOGGER.info("聊城市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("聊城市 列表获取报错");
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
    public void getCont(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gb2312");
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
                    content.replaceAll("附件：", "");
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }
            }
        } catch (Exception e) {
            LOGGER.error("聊城 获取正文报错！");
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
            Elements as = flo.select("a");
            for (Element li : as) {
                File file = new File();
                String wUrl = "http://www.lcsggzyjy.cn:8001" + li.select("a").attr("href").replace("lcweb", "lcztb") + li.text();
                if (!wUrl.equals("")) {
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
