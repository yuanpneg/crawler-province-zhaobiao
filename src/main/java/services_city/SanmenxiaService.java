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
 * 三门峡市
 */
public class SanmenxiaService {

    private static final Logger LOGGER = Logger.getLogger(SanmenxiaService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("List1");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("Right").text();
                    if(GetContentUtil.DateDujge(tender,daytime));
                    String href = "http://www.smxgzjy.org"+li.getElementsByTag("a").get(1).attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.select("a").get(1).attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getCont(href,tender,"Padding10 F14 Content");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("三门峡");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("三门峡市公共资源交易中心");
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
                LOGGER.info("三门峡市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("三门峡市" + "列表获取报错");
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
    public  void getCont(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass(classStr);
                if (elements != null) {
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    List<File> fileList = fileinfo(doc);//获取附件
                    if (fileList.size() != 0) {
                        String fileinfo = gson.toJson(fileList);
                        tender.setFileinfo(fileinfo);//附件
                    }
                    elements.select("a").remove();
                    String content = elements.html().trim();
                  //  if(content.indexOf("附件下载：") != -1){
//                        content = elements.html().replace("附件下载：","");
//                    }
                    tender.setContent(CompressUtils.compress(content)); //压缩
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
        Elements flo = doc.getElementsByClass("Padding10 F14 Content");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo.size() > 0) {
            Elements as = flo.select("a");
            for (Element li : as) {
                File file = new File();
                String wUrl = "http://www.smxgzjy.org" + li.select("a").attr("href");
                if(!wUrl.equals("")) {
                    String [] sq = li.select("a").text().split("\\\\");
                    String filetitle = sq[sq.length-1];
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
