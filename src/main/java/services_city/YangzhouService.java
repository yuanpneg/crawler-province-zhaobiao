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
 * 扬州市
 */
public class YangzhouService {

    private static final Logger LOGGER = Logger.getLogger(YangzhouService.class); //使用log4j记录日志

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("item lh jt_dott f14");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.select("span").text();
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://ggzyjyzx.yangzhou.gov.cn" + li.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        getContById(href,tender,"content");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("扬州");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFrom("扬州市公共资源交易网");
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
                LOGGER.info("扬州市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("扬州市 列表获取报错");
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
    public  void getContById(String url, Tender tender, String classStr) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                String title = doc.getElementsByClass("h120 f20 bold").text().split(" 出自于：")[0];
                if(new BaseDao().selectTitle(title) == 0 && !"".equals(title)) {
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
        Elements flo = doc.getElementsByClass("content");
        //flo.get(0).getElementById("Table1").remove();
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo.size() > 0) {
            Elements as = flo.select("a");
            for (Element li : as) {
                File file = new File();
                String wUrl = "http://ggzyjyzx.yangzhou.gov.cn/qtyy/ggzyjyzx/" + li.select("a").attr("href");
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
