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
 * 益阳市
 */
public class YiyangService {

    private static final Logger LOGGER = Logger.getLogger(YiyangService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url,String category, int catid) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "UTF-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("ewb-r-items");
                Elements lis = elements.get(0).getElementsByTag("li");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByClass("r").text().replace("[","").replace("]","");
                    if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://jyzx.yiyang.gov.cn" + li.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.getElementsByTag("a").attr("title");
                        getCont(href, tender, "news-article");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        tender.setYeWuType(category);
                        tender.setTitle(title);
                        tender.setAddress("益阳");
                        tender.setFrom("益阳公共资源交易中心");
                        tender.setCatid(catid);
                        tender.setStatus(1);
                        tender.setFromurl(href);//正文链接
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
                LOGGER.info("益阳市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("益阳市 请求列表报错");
            LOGGER.info(e.getMessage());
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
//                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//                    List<File> fileList = fileinfo(doc);//获取附件
//                    if (fileList.size() != 0) {
//                        String fileinfo = gson.toJson(fileList);
//                        tender.setFileinfo(fileinfo);//附件
//                    }
                    elements.get(0).getElementsByClass("news-article-tt").remove();
                    elements.get(0).getElementsByClass("news-article-info").remove();
                    String content = elements.html().trim();
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
        Elements flo = doc.getElementsByClass("fj");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo.size() > 0) {
            Elements as = flo.select("a");
            for (Element li : as) {
                File file = new File();
                String wUrl = "http://jyzx.yiyang.gov.cn" + li.select("a").attr("href");
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
