package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class JHttpUtils {

    public static String sendPost(String url,String postBody,String charset,String cookie) {
        OutputStream  out = null;
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
