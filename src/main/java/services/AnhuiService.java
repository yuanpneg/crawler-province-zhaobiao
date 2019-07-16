package services;

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

import org.jsoup.select.Elements;
import pojo1.File;
import utils.CompressUtils;
import utils.GetContentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 安徽省
 */
public class AnhuiService {

    /**
     * 使用log4j记录日志
     */
    private static final Logger LOGGER = Logger.getLogger(AnhuiService.class);

    public List<Tender> getList(String url, FormBody body, String city) {
        List<Tender> listTender = new ArrayList<>();
        try {
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .headers(HttpUtils.getCommonHeaders())
                    .url(url)
                    .post(body)
                    .build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request);

            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                String htmllist = GetContentUtil.unicodeToString(html);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    String urlid = htmllist.split("s" + i + "\\['BULLETIN_ID'\\]=")[1].split(";s" + i)[0].replace("\"", "");
                    list.add(urlid);
                }
                for (String s : list) {
                    Tender tender = new Tender();
                    String href = "http://www.ahggzy.gov.cn/bulletin.do?method=showHomepage&bulletin_id=" + s;
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        Request requestCont = new Request.Builder().url(href).build();
                        HttpUtils.ResponseWrap responseWrapCon = HttpUtils.retryHttpNoProxy(requestCont, "gbk");
                        if (responseWrapCon.isSuccess()) {
                            String htmlCont = responseWrapCon.body;
                            Document doc = Jsoup.parse(htmlCont);
                            Elements elements = doc.getElementsByClass("w_content_main");
                            String daytime = elements.get(0).getElementsByClass("time").get(0)
                                    .getElementsByClass("td_name").get(0).text().replace("推送时间：", "").split(" ")[0];
                            if (GetContentUtil.DateDujge(tender, daytime)) break;
                            String title = elements.get(0).getElementsByClass("title").text();
                            if (new BaseDao().selectTitle(title) != 0) continue;
                            String content = elements.get(0).getElementsByClass("content886").html();
                            tender.setContent(CompressUtils.compress(content));
                            if ("".equals(content) || null == content || tender.getContent().length() < 50) continue;
                            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                            //List<File> fileList = fileinfo(doc);//获取附件
                            //if (fileList.size() != 0) {
                            //    String fileinfo = gson.toJson(fileList);
                            //    tender.setFileinfo(fileinfo);//附件
                            //}
                            //压缩
                            tender.setTitle(title);
                            tender.setYeWuType("建设工程");
                            tender.setFrom("全国公共资源交易平台（安徽省）");
                            tender.setFromurl(href);
                            tender.setAddress(city);
                            tender.setCatid(1);
                            tender.setStatus(1);
                            GetContentUtil.updateTenderRegionLngLat(tender);
                            System.out.println("【" + tender.getAddtime() + "】 " + tender.getTitle() + " , " + tender.getFromurl());
                            if(null == tender.getLongitude() || "".equals(tender.getLongitude())){
                                continue;
                            }
                            GetContentUtil.insertThreadData(tender);
                            listTender.add(tender);
                        }
                    } else {
                        break;
                    }
                }
            } else {
                LOGGER.info("安徽 列表请求错误");
            }
        } catch (Exception e) {
            LOGGER.info("安徽：" + city + "列表获取报错");
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
        return listTender;
    }

    /**
     *获取附件的方法
     */
    public List<File> fileinfo(Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByClass("preview_box");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo != null) {
            if (flo.select("a").size() > 0) {
                File file = new File();
                //附件地址
                String url = flo.select("a").attr("href");
                String filetitle = flo.select("a").attr("title");
                url = "http://ggzy.hefei.gov.cn" + url;
                String[] hrs = filetitle.split(Pattern.quote("."));
                String filetype = hrs[hrs.length - 1];
                for (String s : str) {
                    if (s.equals(filetype)) {
                        //附件名
                        file.setFiletitle(filetitle);
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
