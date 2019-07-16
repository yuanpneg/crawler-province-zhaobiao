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
 * 汕头市
 */
public class ShantouService {

    private static final Logger LOGGER = Logger.getLogger(ShantouService.class); //使用log4j记录日志

    /**
     * 获取对像集合
     *
     * @param url
     * @return
     */
    public List<Tender> getList(String url) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            Request request = new Request.Builder()
                    .header("Cookie", "HA_STICKY_ucap1=ucap1.srv2; yfx_c_g_u_id_10000406=_ck18112810100711165803870431175; yfx_mr_10000406=%3A%3Amarket_type_free_search%3A%3A%3A%3Abaidu%3A%3A%3A%3A%3A%3A%3A%3Awww.baidu.com%3A%3A%3A%3Apmf_from_free_search; yfx_mr_f_10000406=%3A%3Amarket_type_free_search%3A%3A%3A%3Abaidu%3A%3A%3A%3A%3A%3A%3A%3Awww.baidu.com%3A%3A%3A%3Apmf_from_free_search; yfx_key_10000406=; yfx_f_l_v_t_10000406=f_t_1543371007101__r_t_1543371007101__v_t_1543383348971__r_c_0")
                    .header("Referer", "http://www.shantou.gov.cn/ggzyjy/jszbgs/list.shtml")
                    .header("Host", "www.shantou.gov.cn")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36").url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, "utf-8");
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByClass("con-right fr");
                Elements lis = elements.get(0).getElementsByClass("list_div mar-top2 ");
                for (Element li : lis) {
                    Tender tender = new Tender();
                    String daytime = li.getElementsByTag("tr").text().replace("发布时间： ", "").replace("招标公告", "").trim();
                    if (GetContentUtil.DateDujge(tender, daytime)) break;
                    String href = "http://www.shantou.gov.cn" + li.select("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = li.getElementsByTag("a").text();
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        getContById(href, tender, "zoomcon", "utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50)
                            continue;
                        tender.setYeWuType("建设工程");
                        tender.setTitle(title);
                        tender.setAddress("汕头");
                        tender.setFrom("汕头市公共资源交易网");
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
                LOGGER.info("汕头市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("汕头市 列表获取报错");
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
    public void getContById(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);

                Element elements = doc.getElementById(classStr);
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                List<File> fileList = fileinfo(url, doc);//获取附件
                if (fileList.size() != 0) {
                    String fileinfo = gson.toJson(fileList);
                    tender.setFileinfo(fileinfo);//附件
                }
                if (elements != null) {
                    elements.select("a").remove();
                    elements.select("img").remove();
                    elements.getElementById("xgfj").remove();
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
    public List<File> fileinfo(String url, Document doc) {
        List<File> fileList = new ArrayList<>();
        Elements flo = doc.getElementsByTag("ucapcontent");
        String[] str = {"doc", "docx", "xls", "xlsx", "ppt", "pdf"};
        Elements flos = flo.select("a");
        if (flos.size() > 0) {
            File file = new File();
            for (Element el : flos) {
                String wUrl = el.select("a").attr("href").replace("http:","");
                String filetitle = el.select("a").text();
                wUrl = url.substring(0,url.lastIndexOf("/")) +"/"+ wUrl;
                String filetype = wUrl.substring(wUrl.lastIndexOf(".")+1, wUrl.length());
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
