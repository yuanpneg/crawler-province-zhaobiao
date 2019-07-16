package services_city;

import bean.Tender;
import bean.XuzhouJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import dao.BaseDao;
import http.HttpUtils;
import javafx.scene.effect.Light;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo1.File;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 徐州市
 */
public class XuzhouService {

    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(XuzhouService.class);

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                String detailHtml = html.split("\"return\":\"")[1];
                String detail = detailHtml.substring(0, detailHtml.length() - 2).replace("\\", "");
                XuzhouJson json = gson.fromJson(detail, XuzhouJson.class);
                List<XuzhouJson.TableBean> list = json.getTable();
                for (XuzhouJson.TableBean tableBean : list) {
                    Tender tender = new Tender();
                    String daytime = tableBean.getInfodate();
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.xzggzy.com.cn" + tableBean.getHref();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tableBean.getTitle();
                        if (new BaseDao().selectTitle(title) != 0 && !"".equals(title)) continue;
                        getCont(href, tender, "UTF-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setStatus(1);
                        tender.setCatid(1);
                        tender.setAddress("徐州");
                        tender.setFrom("徐州市公共资源交易平台");
                        GetContentUtil.updateTenderRegionLngLat(tender);
//                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
//                        System.out.println("城市id " +  tender.getRegionid() + " 城市名称 " + tender.getRegiontitle())  ;
                        if (null == tender.getLongitude() || "".equals(tender.getLongitude())) {
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("徐州市 列表获取报错");
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
    public void getCont(String url, Tender tender, String classStr) {
        try {
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, classStr);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Element element = doc.getElementsByClass("article-info").get(0);
                if (element != null) {
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    //获取附件
                    List<File> fileList = fileinfo(doc);
                    if (fileList.size() != 0) {
                        String fileinfo = gson.toJson(fileList);
                        //附件
                        tender.setFileinfo(fileinfo);
                    }
                    element.select(".info-sources").remove();
                    element.select("#attach").remove();
                    element.select("h1").remove();
                    element.select("img").remove();
                    String content = element.html().trim();
                    //压缩
                    tender.setContent(CompressUtils.compress(content));
                }
            }
        } catch (Exception e) {
            LOGGER.info("徐州市 正文获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Element flo = doc.getElementById("attach");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo != null) {
            if (flo.select("a").size() > 0) {
                File file = new File();
                String wUrl = flo.select("a").attr("href");
                String filetitle = flo.select("a").attr("title");
                String[] hrs = filetitle.split(Pattern.quote("."));
                String filetype = hrs[hrs.length - 1];
                for (String s : str) {
                    if (s.equals(filetype)) {
                        //附件名
                        file.setFiletitle(filetitle);
                        file.setFiletype(filetype);
                        file.setFileurl(wUrl);
                        fileList.add(file);
                    }
                }
            }
        }
        return fileList;
    }

}
