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
 * 菏泽市
 */
public class HezeService {

    private static final Logger LOGGER = Logger.getLogger(HezeService.class); //使用log4j记录日志

    public List<Tender> getList(String html) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(html);
            Element element = doc.getElementById("contentL1");
            Elements lis = element.getElementsByTag("li");
            for (Element li : lis) {
                Tender tender = new Tender();
                String daytime = li.getElementsByClass("info_right").select("span").get(1).select("em").text();
                if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                String href = "http://www.hzsggzyjy.gov.cn/" + li.getElementsByTag("a").attr("href");
                int tit = new BaseDao().selectUrl(href);
                if (tit == 0) {
                    getCont(href,tender,"ContentPlaceHolder1_contents");
                    if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                    tender.setFromurl(href);
                    tender.setCatid(1);
                    tender.setStatus(1);
                    tender.setYeWuType("建设工程");
                    tender.setAddress("菏泽");
                    tender.setFrom("菏泽市公共服务与行政监督平台");
                    GetContentUtil.updateTenderRegionLngLat(tender);
                    System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                    if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                        continue;
                    }
                    GetContentUtil.insertThreadData(tender);
                    tenderList.add(tender);
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.info("菏泽市 列表获取报错");
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
    public  void getCont(String url, Tender tender, String idStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Element elements = doc.getElementById(idStr);
                String title = doc.getElementById("ContentPlaceHolder1_title").text();
                if (new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
                    tender.setTitle(title);
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
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Element flo = doc.getElementById("ContentPlaceHolder1_contents");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf", "rar","zip"};
        if (null != flo) {
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
