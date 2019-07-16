package services_city;

import bean.Tender;
import dao.BaseDao;
import http.HttpUtils;
import okhttp3.Request;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.CompressUtils;
import utils.GetContentUtil;
import utils.JHttpUtils;

import javax.swing.text.html.HTML;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 长沙市
 */
public class ChangshaService {

    private static final Logger LOGGER = Logger.getLogger(ChangshaService.class); //使用log4j记录日志

    public List<Tender> getList(String url,String postBody, String category, int catid) {
        List<Tender> tenderList = new ArrayList<>();
        try {
            String responseWrap = sendPost(url,postBody,"UTF-8","JSESSIONID=CD23A2E79CA789AC21BFAD3FE7FA1AD8; UM_distinctid=1674f48d8543b-0d8fb9fc1706f9-3a3a5c0e-1fa400-1674f48d85564b; CNZZDATA1275214221=2068249357-1543223224-http%253A%252F%252Fggzy.changsha.gov.cn%252F%7C1548981008");

            if (responseWrap != null) {
                Document doc = Jsoup.parse(responseWrap);
                Elements trs = doc.getElementsByTag("a");
                for (Element tr : trs) {
                    Tender tender = new Tender();
                    String href = "http://fwpt.csggzy.cn" + tr.getElementsByTag("a").attr("href");
                    int tit = new BaseDao().selectUrl(href);
                    if (tit == 0) {
                        String title = tr.getElementsByTag("a").attr("title");
                        if (new BaseDao().selectTitle(title) != 0) continue;
                        if(title.indexOf("测试") != -1) continue;
                        getCont(href,tender,"content-box","utf-8");
                        if("".equals(tender.getContent()) || null == tender.getContent() || tender.getContent().length() < 50) continue;
                        tender.setTitle(title);
                        tender.setFromurl(href);
                        tender.setYeWuType(category);
                        tender.setAddress("长沙");
                        tender.setCatid(catid);
                        tender.setStatus(1);
                        tender.setFrom("长沙市公共资源交易网");
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
                LOGGER.info("长沙市 请求列表为空");
            }
        } catch (Exception e) {
            LOGGER.info("长沙市 列表获取报错");
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

    public static String sendPost(String url,String postBody,String charset,String cookie) {
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
            conn.setRequestProperty("Host","fwpt.csggzy.cn");
            conn.setRequestProperty("Origin","http://fwpt.csggzy.cn");
            conn.setRequestProperty("Referer"," http://fwpt.csggzy.cn/spweb/CS/TradeCenter/tradeList.do?Deal_Type=Deal_Type1");
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

            //dos.writeBytes(postBody);
            //dos.flush();
            //dos.close();

            //获得响应状态
            int resultCode = conn.getResponseCode();

//            System.out.println(resultCode);

            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer sb = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
//                System.out.println(sb.toString());
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
