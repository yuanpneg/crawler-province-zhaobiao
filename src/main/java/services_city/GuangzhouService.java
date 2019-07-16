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
 * 广州市
 */
public class GuangzhouService {

    private static final Logger LOGGER = Logger.getLogger(GuangzhouService.class); //使用log4j记录日志


    public List<Tender> getList(String url, FormBody body, String category, int catid) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .url(url)
                    .post(body)
                    .build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("infor_lb");
                Elements trs = elements.get(0).getElementsByTag("tr");
                trs.remove(0);
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.getElementsByTag("td").get(2).text().split(" ")[0];
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.gzggzy.cn" + tr.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.select("a").text().replace("...", "");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href, tender);
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType(category);
                        tender.setAddress("广州");
                        tender.setCatid(catid);
                        tender.setStatus(1);
                        tender.setFrom("广州公共资源交易中心");
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
                LOGGER.info("广州市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("广州市 列表获取报错");
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
    public void getCont(String url, Tender tender) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("Section1");
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
            LOGGER.info("广州市：正文获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }

    /*
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByTag("font").select("a");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf", "rar"};
        if (flo.size() > 0) {
            for (Element li : flo) {
                File file = new File();
                String wUrl = li.select("a").attr("href");
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
        return fileList;
    }
}
