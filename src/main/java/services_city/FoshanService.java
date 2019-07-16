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
 * 佛山市
 */
public class FoshanService {

    private static final Logger LOGGER = Logger.getLogger(FoshanService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("content_right");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByTag("span").text().replace("年", "-")
                            .replace("月", "-").replace("日", "");
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://zgb.fspc.gov.cn/ywbl/gcjygg" + li.getElementsByTag("a")
                            .attr("href").replaceFirst(".", "");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        //获取正文
                        getCont(href, tender, "article_con", "utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("佛山");
                        tender.setFrom("佛山市公共资源交易网");
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
            }else {
                LOGGER.info("佛山市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("佛山市 列表获取报错");
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
    public void getCont(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass(classStr);
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
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByClass("ewb-article-info").select("a");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf", "rar"};
        if (flo.size() > 0) {
            File file = new File();
            String wUrl = flo.attr("href");
            String filetitle = flo.text();
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
        return fileList;
    }
}
