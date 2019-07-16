package services;


import bean.HnJson;
import bean.JiangxiJson;
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
 * 江西省
 */
public class JiangxiService {

    /*
     *使用log4j记录日志
     */
    private static  final Logger LOGGER = Logger.getLogger(JiangxiService.class);

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url, String category, String categorynum, int catid, String city) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(url).build();

            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Gson gson = new Gson();
                String json = html.substring(11).substring(0, html.substring(11).length() - 2).replace("\\", "");
                JiangxiJson jiangxiJson = gson.fromJson(json, JiangxiJson.class);
                List<JiangxiJson.TableBean> list = jiangxiJson.getTable();
                for (JiangxiJson.TableBean bean : list) {
                    Tender tender = new Tender();
                    String daytime = bean.getPostdate();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String dayid = daytime.replace("-", "");
                    String href = "http://jxsggzy.cn/web/jyxx/" + categorynum.substring(0, categorynum.length() - 3) + "/" + categorynum + "/" + dayid + "/" + bean.getInfoid() + ".html";
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        if (new BaseDao().selectTitle(bean.getTitle()) != 0) continue;
                        tender.setTitle(bean.getTitle().replaceAll("<font color='#0066FF'>","").replaceAll("</font>","")
                                .replaceAll("<font color='red'>",""));
                        //获取正文
                        getCont(href, tender, "con");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setFromurl(href);
                        tender.setCatid(catid);
                        tender.setStatus(1);
                        tender.setYeWuType(category);
                        tender.setAddress(city);
                        tender.setFrom("江西省公共资源交易网");
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
            }else {
                LOGGER.info("江西 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("江西：" + city + "列表获取报错");
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
                    //获取附件
                    List<File> fileList = fileinfo(doc);
                    if (fileList.size() != 0) {
                        String fileinfo = gson.toJson(fileList);
                        //附件
                        tender.setFileinfo(fileinfo);
                    }
                    String content = elements.get(0).html().trim();
                    //压缩
                    tender.setContent(CompressUtils.compress(content));
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
        Elements flo = doc.getElementsByClass("con attach");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        if (flo.size() > 0) {
            Elements as = flo.select("a");
            for (Element li : as) {
                File file = new File();
                String wUrl = "http://jxsggzy.cn" + li.select("a").attr("href");
                if(!wUrl.equals("")) {
                    String filetitle =  li.select("a").attr("title");
                    String[] hrs = filetitle.split(Pattern.quote("."));
                    String filetype = hrs[hrs.length - 1];
                    for (String s : str) {
                        if (s.equals(filetype)) {
                            //附件名
                            file.setFiletitle(filetitle);
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
