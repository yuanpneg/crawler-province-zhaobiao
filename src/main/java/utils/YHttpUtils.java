package utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YHttpUtils {

    public static Map<String, String> sendPostRequest(String reqURL, Map<String, String> reqParams, String reqCharset) {
        StringBuilder reqData = new StringBuilder();
        for (Map.Entry<String, String> entry : reqParams.entrySet()) {
            try {
                reqData.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), reqCharset)).append("&");
            } catch (UnsupportedEncodingException e) {
                System.out.println("编码字符串[" + entry.getValue() + "]时发生异常:系统不支持该字符集[" + reqCharset + "]");
                reqData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        if (reqData.length() > 0) {
            reqData.setLength(reqData.length() - 1); //删除最后一个&符号
        }
        return sendPostRequest(reqURL, reqData.toString(), reqCharset);
    }


    public static Map<String, String> sendPostRequest(String reqURL, String reqData, String reqCharset) {
        Map<String, String> respMap = new HashMap<String, String>();
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null; //写
        InputStream in = null;   //读
        String respBody = null;  //HTTP响应报文体
        String respCharset = "UTF-8";
        try {
            URL sendUrl = new URL(reqURL);
            httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
            httpURLConnection.setDoInput(true);    //true表示允许获得输入流,读取服务器响应的数据,该属性默认值为true
            httpURLConnection.setDoOutput(true);   //true表示允许获得输出流,向远程服务器发送数据,该属性默认值为false
            httpURLConnection.setUseCaches(false); //禁止缓存
            httpURLConnection.setReadTimeout(30000);    //30秒读取超时
            httpURLConnection.setConnectTimeout(30000); //30秒连接超时
            httpURLConnection.setRequestMethod("POST");

            out = httpURLConnection.getOutputStream();
            out.write(reqData.getBytes());
            out.flush(); //发送数据

            /**
             * 获取HTTP响应头
             * @see URLConnection类提供了读取远程服务器响应数据的一系列方法
             * @see getHeaderField(String name)可以返回响应头中参数name指定的属性的值
             * @see 注意:经过我的测试,此处获取到头属性的顺序与服务器响应的真实头属性顺序或可不一致
             * @see 注意:测试时,我让服务器返回的头属性中,Content-Type排在第一个,Content-Length排在第二个
             * @see 注意:结果在此处获取到的响应头属性中,Content-Length排在第一个,Content-Type排在第二个
             */
            StringBuilder respHeader = new StringBuilder();
            Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
            for(Map.Entry<String, List<String>> entry : headerFields.entrySet()){
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<entry.getValue().size(); i++){
                    sb.append(entry.getValue().get(i));
                }
                if(null == entry.getKey()){
                    respHeader.append(sb.toString());
                }else{
                    respHeader.append(entry.getKey()).append(": ").append(sb.toString());
                }
                respHeader.append("\r\n");
            }

            /**
             * 获取Content-Type中的charset值
             * @see 如Content-Type: text/html; charset=GBK
             */
            String contentType = httpURLConnection.getContentType();
            if(null!=contentType && contentType.toLowerCase().contains("charset")){
                respCharset = contentType.substring(contentType.lastIndexOf("=") + 1);
            }

            /**
             * 获取HTTP响应正文
             * @see ---------------------------------------------------------------------------------------------
             * @see SUN提供了基于HTTP协议的框架实现,不过,这些实现类并没有在JDK类库中公开,它们都位于sun.net.www包或者其子包中
             * @see 并且,URLConnection具体子类(HttpURLConnection类)的getInputStream()方法仅仅返回响应正文部分的输入流
             * @see HTTP响应结果包括HTTP响应码,响应头和响应正文3部分,获得输入流后,就能读取服务器发送的响应正文
             * @see ----------------------------------------------------------------------------------------------
             * @see 使用httpURLConnection.getContentLength()时,要保证服务器给返回Content-Length头属性
             * @see byte[] byteDatas = new byte[httpURLConnection.getContentLength()];
             * @see httpURLConnection.getInputStream().read(byteDatas);
             * @see respBody = new String(byteDatas, respCharset);
             * @see ----------------------------------------------------------------------------------------------
             * @see in = httpURLConnection.getInputStream();
             * @see byte[] byteDatas = new byte[in.available()];
             * @see 关于InputStream.available()说明如下,更详细说明见JDK API DOC
             * @see 有些InputStream的实现将返回流中的字节总数,但也有很多实现不会这样做
             * @see 试图使用in.available()方法的返回值分配缓冲区,以保存此流所有数据的做法是不正确的
             * @see ----------------------------------------------------------------------------------------------
             */
            in = httpURLConnection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while((len=in.read(buff)) != -1){
                buffer.write(buff, 0, len);
            }
            respBody = buffer.toString(respCharset);

            //使用httpURLConnection.getResponseMessage()可以获取到[HTTP/1.0 200 OK]中的[OK]
            respMap.put("respCode", String.valueOf(httpURLConnection.getResponseCode()));
            respMap.put("respBody", respBody);
            respMap.put("respMsg", respHeader.toString() + "\r\n" + respBody);
            return respMap;
        } catch (Exception e) {
            System.out.println("与[" + reqURL + "]通信异常,堆栈信息如下");
            e.printStackTrace();
            return respMap;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    System.out.println("关闭输出流时发生异常,堆栈信息如下");
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    System.out.println("关闭输入流时发生异常,堆栈信息如下");
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                httpURLConnection = null;
            }
        }
    }
}
