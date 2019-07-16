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
 * 济源市
 */
public class JiyuanService {
    //使用log4j记录日志
    private static  final Logger LOGGER = Logger.getLogger(JiyuanService.class);

    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            HttpUtils.ResponseWrap responseWrap = GetContentUtil.getResponseWrap(url);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("ewb-txt");
                Elements trs = elements.get(0).getElementsByClass("trfont");
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String daytime = tr.select("td").get(2).text().replace("[","").replace("]","");
                    //判断时间
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.jyggjy.cn" + tr.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href,tender,"JszbggDetail1_content","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setYeWuType("建设工程");
                        tender.setAddress("济源");
                        tender.setFrom("济源市公共资源交易中心");
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
            } else {
                LOGGER.info("济源市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("济源市 列表获取报错");
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
    public  void getContById(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
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
        Element flo = doc.getElementById("JszbggDetail1_content");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (null != flo) {
            Elements as = flo.select("a");
            as.remove(as.size()-1);
            for (Element li : as) {
                File file = new File();
                String wUrl = "http://www.jyggjy.cn" + li.select("a").attr("href");
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
