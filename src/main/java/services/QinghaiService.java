package services;

import bean.QinghaiJson;
import bean.Tender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;
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
 * 青海省
 */
public class QinghaiService {

    private static final Logger LOGGER = Logger.getLogger(QinghaiService.class); //使用log4j记录日志

    public List<Tender> getList(String url, String city, RequestBody requestBody) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().header("Accept", "application/json, text/javascript, */*; q=0.01")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36")
                    .header("Referer", "http://www.qhggzyjy.gov.cn/ggzy/jyxx/001001/001001001/secondPage.html").url(url).post(requestBody).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                QinghaiJson qinghaiJson = gson.fromJson(html, QinghaiJson.class);
                List<QinghaiJson.ResultBean.RecordsBean> list = qinghaiJson.getResult().getRecords();
                for (QinghaiJson.ResultBean.RecordsBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getInfodate().split(" ")[0];
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.qhggzyjy.gov.cn" + bean.getLinkurl();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        if (new BaseDao().selectTitle(bean.getTitle()) != 0) continue;
                        getCont(href, tender, "ewb-info-content", "utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(bean.getTitle());
                        tender.setAddress(city);
                        tender.setFrom("全国公共资源交易平台（青海省）");
                        tender.setCatid(1);
                        tender.setStatus(1);
                        tender.setFromurl(href);//正文链接
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
            } else {
                LOGGER.info("青海" + city + "请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("青海：" + city + "列表获取报错");
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
            LOGGER.info("青海：正文获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

    }

    /*
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByClass("ewb-info-end");
        flo.remove(0);
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf", "rar"};
        if (flo.size() > 0) {
            for (Element li : flo) {
                File file = new File();
                String wUrl = "http://111.44.251.34" + li.select("a").attr("href");
                String filetitle = li.select("a").attr("title");
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
