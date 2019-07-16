package services_city;

import bean.Tender;
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
import utils.CompressUtils;
import utils.GetContentUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 清远市
 */
public class QingyuanService {

    private static final Logger LOGGER = Logger.getLogger(QingyuanService.class); //使用log4j记录日志

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
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("newtable");
                Elements trs = elements.get(0).getElementsByTag("tr");
                trs.remove(trs.size()-1);
                trs.remove(trs.size()-1);
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.select("td").get(1).text();
                    if(GetContentUtil.DateDujge(tender,daytime)) break;
                    String href = tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getCont(href,tender,"context_div","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("清远");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("清远市公共资源交易中心");
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
                LOGGER.info("清远市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("清远市 列表获取报错");
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
    public  void getCont(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                String title = doc.getElementsByClass("toptd1").text();
                if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
                    tender.setTitle(title);
                    Elements elements = doc.getElementsByClass(classStr);
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
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByTag("table");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo.size() > 0) {
            Elements trs = flo.get(flo.size()-1).select("tr");
            for (Element li : trs) {
                File file = new File();
                String wUrl = li.select("a").attr("href");
                if(!wUrl.equals("")) {
                    String filetitle =  li.select("a").text();
                    String[] hrs = filetitle.split(Pattern.quote("."));
                    String filetype = hrs[hrs.length - 1].replace("》","");
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
