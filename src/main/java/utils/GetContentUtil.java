package utils;

import app.AllApp;
import bean.Tender;
import dao.BaseDao;
import dao.FormalDao;
import http.HttpUtils;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo1.Cityregion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetContentUtil {

    /**
     * 获取正文 class
     *
     * @param url
     * @return
     */
    public static void getCont(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);

                Elements elements = doc.getElementsByClass(classStr);
                if (elements != null) {
                    //elements.select("a").remove();
                    //elements.select("img").remove();
                    String content = elements.html().trim();
                    //content.replaceAll("附件：","");
                    tender.setContent(CompressUtils.compress(content)); //压缩
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取正文 id
     *
     * @param url
     * @return
     */
    public static void getContById(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);

                Element elements = doc.getElementById(classStr);
                if (elements != null) {
                    //elements.select("a").remove();
                    //elements.select("img").remove();
                    String content = elements.html().trim();
                    //压缩
                    tender.setContent(CompressUtils.compress(content));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean DateDujge(Tender tender, String daytime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ("".equals(daytime)) return false;
            Date date = format.parse(daytime);
            //判断时间是否是30天内
            if (date.getTime() / 1000 <= AllApp.getfoct()) {
                return true;
            }
            tender.setAddtime(date.getTime() / 1000);
            tender.setEdittime(date.getTime() / 1000);
            int dsjaddtime = (int) (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000);
            tender.setDsjaddtime(dsjaddtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改经纬度
     */
    public static void updateTenderRegionLngLat(Tender tender) {
        FormalDao formalDao = new FormalDao();
        String city = tender.getAddress();
        try {
            String cityb = city.replace("省", "").replace("市", "")
                    .replace("区", "");
            if (!cityb.equals("") && null != cityb) {
                Cityregion cityregion = formalDao.selectLonLatByTitle(cityb);
                tender.setLongitude(cityregion.getLon());
                tender.setLatitude(cityregion.getLat());
                tender.setRegionid(cityregion.getId());
                tender.setRegiontitle(cityregion.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch + "");

        }

        return str;

    }

    public static HttpUtils.ResponseWrap getResponseWrap(String url) {
        Request request = new Request.Builder().headers(HttpUtils.getCommonHeaders()).url(url).build();
        return HttpUtils.retryHttpNoProxy(request, "utf-8");
    }

    public static HttpUtils.ResponseWrap getResponseWrapProxy(String url) {
        Request request = new Request.Builder().headers(HttpUtils.getCommonHeaders()).url(url).build();
        return HttpUtils.retryHttp(request, "utf-8");
    }

    //插入数据库
    public synchronized static void insertThreadData(Tender tender) {
        BaseDao baseDao = new BaseDao();
        FormalDao formalDao = new FormalDao();
        //打印更新内容
        //这个是你要转成后的时间的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("【" + sdf.format(new Date(Long.valueOf(tender.getAddtime() + "000"))) + "】 " + tender.getTitle() + " , " + tender.getFromurl());
        baseDao.insertTender(tender);
        int id = tender.getId();
        if (-1 != id) {
            formalDao.insertTender(tender);
            int flag = tender.getId();
            if (-1 != flag) {
                tender.setId(id);
                tender.setFormalId(flag);
                baseDao.updateFormalId(tender);
                tender.setId(flag);
                formalDao.insertTenderContent(tender);
            }
        }
    }


}
