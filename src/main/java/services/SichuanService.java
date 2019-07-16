package services;

import bean.SichuanJson;
import bean.Tender;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
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
 * 四川省
 */
public class SichuanService {

    private static final Logger LOGGER = Logger.getLogger(SichuanService.class); //使用log4j记录日志


    public List<Tender> getList(String url, String city, RequestBody requestBody) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().header("cookie", " JSESSIONID=AEA5A8E6F7669DCF810936DF8E72AD63; CNZZDATA1264557630=918057528-1540196075-http%253A%252F%252Fwww.ggzy.gov.cn%252F%7C1550727707; UM_distinctid=16acf565a5e9ab-056d62cbcb106f-5f1d3a17-1fa400-16acf565a5fb08; CNZZDATA1276636503=2045625494-1558256195-http%253A%252F%252Fwww.ggzy.gov.cn%252F%7C1558256195; userGuid=-254005787").url(url).post(requestBody).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                Tender tender = new Tender();
                SichuanJson sichuanJson = gson.fromJson(html, SichuanJson.class);
                List<SichuanJson.ResultBean.RecordsBean> recordList = sichuanJson.getResult().getRecords();
                for (SichuanJson.ResultBean.RecordsBean recordsBean : recordList) {
                    String daytime = recordsBean.getInfodate().split(" ")[0];
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://ggzyjy.sc.gov.cn" + recordsBean.getLinkurl();
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        if (new BaseDao().selectTitle(recordsBean.getTitle()) != 0) continue;
                        getContById(href, tender, "newsText", "utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        // tender.setTitle(title);
                        tender.setAddress(city);
                        tender.setFrom("全国公共资源交易平台（四川省）");
                        tender.setCatid(1);
                        tender.setTitle(recordsBean.getTitle());
                        tender.setStatus(1);
                        //正文链接
                        tender.setFromurl(href);
                        GetContentUtil.updateTenderRegionLngLat(tender);
                        System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                        if (null == tender.getLongitude() || "".equals(tender.getLongitude())) {
                            continue;
                        }
                        GetContentUtil.insertThreadData(tender);
                        tenderList.add(tender);
                    } else {
                        break;
                    }
                }

            } else {
                LOGGER.info("四川 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("四川：" + city + "列表获取报错");
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
    public void getContById(String url, Tender tender, String classStr, String charset) {
        Request request = new Request.Builder().url(url).build();
        HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
        if (responseWrap.isSuccess()) {
            String html = responseWrap.body;
            Document doc = Jsoup.parse(html);
            try {
                Element elements = doc.getElementById(classStr);
                if (elements != null) {
                    elements.select("a").remove();
                    elements.select("img").remove();
                    //String content = elements.attr("value").replace("&nbsp;", "").trim();
                    tender.setContent(CompressUtils.compress(elements.html())); //压缩
                }
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                List<File> fileList = fileinfo(doc);//获取附件
                if (fileList.size() != 0) {
                    String fileinfo = gson.toJson(fileList);
                    tender.setFileinfo(fileinfo);//附件
                }

            } catch (Exception e) {
                LOGGER.info("四川：获取正文报错");
                LOGGER.info(e.getMessage());
                e.printStackTrace();

            }
        }
    }

            /*
             *获取附件的方法
             */
            public List<File> fileinfo (Document doc){
                List<File> fileList = new ArrayList<>();
                Elements flo = doc.getElementsByClass("attach_content");
                String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
                if (flo != null) {
                    for (Element el : flo) {
                        File file = new File();
                        //附件地址
                        String url = "http://ggzyjy.sc.gov.cn" + el.select("a").attr("href");
                        String filetitle = el.select("a").attr("title");
                        String[] hrs = filetitle.split(Pattern.quote("."));
                        String filetype = hrs[hrs.length - 1];
                        for (String s : str) {
                            if (s.equals(filetype)) {
                                file.setFiletitle(filetitle);//附件名
                                file.setFiletype(filetype);
                                file.setFileurl(url);
                                fileList.add(file);
                            }
                        }
                    }
                }
                return fileList;
            }
        }
