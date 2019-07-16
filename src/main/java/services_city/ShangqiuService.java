package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.CompressUtils;
import utils.GetContentUtil;
import utils.JHttpUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 商丘市
 */
public class ShangqiuService {

    private static final Logger LOGGER = Logger.getLogger(ShangqiuService.class); //使用log4j记录日志

    public List<Tender> getList(String url, String postBody,String category, int catid,String type) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            String html = sendPost(url,postBody,"UTF-8","JSESSIONID=6623FC13B29387E66BDEF73E6A6747F4; JSESSIONID=3FCAF160C55F91A585E317CCB5FF0339",type);
            if (html != null) {
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByTag("a");
                for (Element tr : elements) {
                    Tender tender = new Tender();
                    //String daytime = tr.getElementsByTag("td").get(2).text();
                    //if (GetContentUtil.DateDujge(tender, daytime)) break; //判断时间
                    String href = "http://www.sqggzy.com" + tr.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        if(title.indexOf("测试") != -1) continue;
                        getCont(href, tender,"content-box","utf-8");
                        if ("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType(category);
                        tender.setAddress("商丘");
                        tender.setCatid(catid);
                        tender.setStatus(1);
                        tender.setFrom("商丘市公共资源交易网");
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
            }else{
                LOGGER.info("商丘市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("商丘市 列表获取报错");
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
    public static void getCont(String url, Tender tender, String classStr, String charset) {
        try {
            Request request = new Request.Builder().url(url).build();
            HttpUtils.ResponseWrap responseWrap = HttpUtils.retryHttpNoProxy(request, charset);
            if (responseWrap.isSuccess()) {
                String html = responseWrap.body;
                Document doc = Jsoup.parse(html);
                String daytime = doc.getElementsByClass("ttr").text().replace("发布日期：","").split(" ")[0];
                if(GetContentUtil.DateDujge(tender, daytime) == false) {
                    Elements elements = doc.getElementsByClass(classStr);
                    if (elements != null) {
                        String content = elements.html().trim();
                        tender.setContent(CompressUtils.compress(content)); //压缩
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String sendPost(String url,String postBody,String charset,String cookie, String type) {
        OutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", charset);
            conn.setRequestProperty("Host","www.sqggzy.com");
            conn.setRequestProperty("Origin","http://www.sqggzy.com");
            conn.setRequestProperty("Referer","http://www.sqggzy.com/spweb/HNSQ/TradeCenter/tradeList.do?Deal_Type=Deal_Type1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
            if (!(cookie.equals("")||cookie.equals(null))){
                conn.setRequestProperty("Cookie",cookie);
            }
            conn.connect();
            //建立输入流，向指向的URL传入参数
            // DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            out = conn.getOutputStream();
            out.write(postBody.getBytes());
            out.flush();

            //获得响应状态
            int resultCode = conn.getResponseCode();

            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer sb = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                return sb.toString();
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
        }
        return null;
    }

}
