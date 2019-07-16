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
 * 枣庄市
 */
public class ZaozhuangService {

    private static final Logger LOGGER = Logger.getLogger(ZaozhuangService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select("tbody");
                if (elements.size() > 13) {
                    Element element = elements.get(13);
                    Elements trs = element.select("tr");
                    trs.remove(trs.size() - 1);
                    trs.remove(trs.size() - 1);
                    for (int i = 0; i < trs.size(); i += 2) {
                        Tender tender = new Tender();
                        String daytime = trs.get(i).getElementsByTag("td").get(3).text().replace("[", "").replace("]", "");
                        if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                        String href = "http://www.zzggzy.com" + trs.get(i).select("a").attr("href");
                        int tit = new BaseDao().selectUrl(href);
                        if (tit == 0) {
                            String title = trs.get(i).select("a").attr("title");
                            if (new BaseDao().selectTitle(title) != 0) continue;
                            getContById(href, tender, "TDContent");
                            if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                                continue;
                            tender.setFromurl(href);
                            tender.setTitle(title);
                            tender.setYeWuType("建设工程");
                            tender.setAddress("枣庄");
                            tender.setCatid(1);
                            tender.setStatus(1);
                            tender.setFrom("枣庄市公共资源交易网");
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
                }

            } else {
                LOGGER.info("枣庄市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("枣庄市 列表获取报错");
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
    public void getContById(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "gbk");
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
                String wUrl = li.select("a").attr("href");
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
