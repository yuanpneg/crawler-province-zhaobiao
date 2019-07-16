package utils;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TenderContent {
    public static String context(String content) {

        content = content.replaceAll("width=\".*\"", "width='100%'");
        content = content.replaceAll("width.*px;?", "width='100%'");
        content = content.replaceAll("margin-left:.*pt", "margin-left:5%");
        content = content.replaceAll("MARGIN-LEFT:\\s*\\d*.\\d*pt", "MARGIN-LEFT: 5%");

        content = "<div width='100%'>" + content + "</div>";




        if (content == null || content.trim().length() < 50) {
            return "";
        }
        String meta = "<meta name=\"viewport\" content=\"width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "\n" +
                "<meta name=\"format-detection\" content=\"telephone=no\">\n ";
        if (content == null || content.length() <= 0) {
            throw new RuntimeException("cq admin context 为空");
        }

        return meta + content;
    }

    public static String contextChange(String content,String reg) {

        // content = content.replaceAll("width=\".*\"", "width='100%'");
        // content = content.replaceAll("width.*px;?", "width='100%'");
        // content = content.replaceAll("margin-left:.*pt", "margin-left:5%");
        //content = content.replaceAll("MARGIN-LEFT:\\s*\\d*.\\d*pt", "MARGIN-LEFT: 5%");

        //content = "<div width='100%'>" + content + "</div>";

        if (content == null || content.trim().length() < 50) {
            return "";
        }
        String meta = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0,user-scalable=no,minimal-ui\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "\n" +
                "<meta name=\"format-detection\" content=\"telephone=no\">\n ";
        if (content == null || content.length() <= 0) {
            throw new RuntimeException("cq admin context 为空");
        }

        String regEx = "</?[^/?"+reg+"][^><]*>";
        Pattern p_html = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(content);
        content = m_html.replaceAll("");
        String contents = content.trim()
//                .replaceAll("</?tbody>","")
//                .replaceAll("</?table>","")
                .replaceAll("width=\"\\d*\"","")
                .replaceAll("width=\"\\d*px\"","")
                .replaceAll("（","( ")
                .replaceAll("）"," )")
                .replaceAll("style\\s*=\\s*\"([^\\\"]|\\\\\\\\\\\")+\"","")
                .replaceAll("&nbsp;","")
                .replaceAll("TEXT-INDENT:\\s*\\d*pt","")
                .replaceAll("MARGIN-LEFT:\\s*\\d*.\\d*pt","")
                .replaceAll("\uFEFF","");
        return "<div style = \"word-break:break-all;\">" + meta +contents + "</div>"; // 返回文本字符串
        //return meta + contents;
    }

    public static int addTime(String day) {
        if (day == null || day.length() <= 0) {
            return 0;
        }
        String date = null;
        Date time = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Pattern p = Pattern.compile("[0-9]{4}[年|\\-|/][0-9]{1,2}[月|\\-|/][0-9]{1,2}");
        Matcher m = p.matcher(day);
        while (m.find()) {
            if (!"".equals(m.group())) {
                date = m.group();
                date = date.replaceAll("年", "-");
                date = date.replaceAll("月", "-");
                date = date.replaceAll("/", "-");

            }
        }
        if (date == null || date.length() <= 0) {
            return -1;
        }
        try {
            time = simpleDateFormat.parse(date);
            Date excldue = simpleDateFormat.parse("2018-1-1");
            if (excldue.getTime() > time.getTime()) {
                return -2;
            }
            return (int) (time.getTime() / 1000);
        } catch (ParseException e) {
            throw new RuntimeException("cq admin" + e.getMessage());
        }
    }

    public static String fileInfo(Elements elements, String url, String RealUrl) {
        if (elements == null || elements.size() <= 0) {
            return null;
        }
        String attachment = null;
        String filetitle = "";
        String wordUrl = "";
        StringBuilder builderstr = new StringBuilder();
        for (Element ele : elements) {
            String href = ele.attr("href");
            filetitle = (href.substring(href.lastIndexOf("/") + 1, href.length()));
            builderstr.append("{\"filetitle\":").append("\"" + filetitle + "\"")
                    .append(",\"fileurl\":").append("\"" + RealUrl + href + "\"")
                    .append(",\"filetype\":").append("\"" + filetitle.substring(filetitle.lastIndexOf(".") + 1, filetitle.length()) + "\"},");

        }
        String str = builderstr.toString().substring(0, builderstr.toString().lastIndexOf(","));
        StringBuffer builder = new StringBuffer();
        return builder.append("[").append(str).append("]").toString();
    }
}
