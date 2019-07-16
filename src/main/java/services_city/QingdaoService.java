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
 * 青岛市
 */
public class QingdaoService {
    private static final Logger LOGGER = Logger.getLogger(QingdaoService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("info_con");
                Elements trs = elements.get(0).getElementsByTag("tr");
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    System.out.println();
                    String daytime = tr.getElementsByTag("td").get(1).text();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "https://ggzy.qingdao.gov.cn" + tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        //获取正文
                        getContById(href, tender, "htmlTable","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("青岛");
                        tender.setFrom("青岛市公共资源交易中心");
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
                LOGGER.info("青岛市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("青岛市 列表获取报错");
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
                    //获取附件
                    List<File> fileList = fileinfo(doc);
                    if (fileList.size() != 0) {
                        String fileinfo = gson.toJson(fileList);
                        //附件
                        tender.setFileinfo(fileinfo);
                    }
                    elements.select("a").remove();
                    elements.select("img").remove();
                    String content = elements.html().trim();
                    content.replaceAll("附件：","");
                    //压缩
                    tender.setContent(content);
                }
            }
        }catch (Exception e) {
            LOGGER.info("青岛：获取正文错误");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByClass("people_li clearfix");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo.size() > 0) {
            Elements as = flo.select("a");
            for (Element li : as) {
                File file = new File();
                String wUrl = "https://ggzy.qingdao.gov.cn" + li.select("a").attr("href");
                if(!wUrl.equals("")) {
                    String filetitle =  li.select("a").text();
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
