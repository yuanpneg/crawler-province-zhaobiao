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
 * 石河子（第八师）
 */
public class ShiheziService {

    //使用log4j记录日志
    private static final Logger LOGGER = Logger.getLogger(ShiheziService.class);

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url,String city,String from) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("top10").get(1).getElementsByTag("tbody");
                Elements trs = elements.get(1).getElementsByTag("tr");
                trs.remove(trs.size()-1);
                for (int i = 0; i < trs.size(); i += 2) {
                    Tender tender = new Tender();
                    String daytime = trs.get(i).select("td").get(2).text().replace("[","").replace("]","");
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://ggzy.xjbt.gov.cn"+trs.get(i).getElementsByTag("a").attr("href");
                    //int tit = new BaseDao().selectUrl(href);
                    if (true) {
                        String title = trs.get(i).getElementsByTag("a").attr("title");
                        //if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href,tender,"TDContent","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)  continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom(from);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        //正文链接
                        tender.setFromurl(href);
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
            }else{
                LOGGER.info("石河子 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("石河子 获取正文错误");
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
//                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//                    List<File> fileList = fileinfo(doc);//获取附件
//                    if (fileList.size() != 0) {
//                        String fileinfo = gson.toJson(fileList);
//                        tender.setFileinfo(fileinfo);//附件
//                    }
                    elements.select("a").remove();
                    elements.select("img").remove();
                    String content = elements.html().trim();
                    tender.setContent(content); //压缩
                }
            }
        }catch (Exception e) {
            LOGGER.info("石河子：获取正文错误");
            LOGGER.info(e.getMessage());
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
