package utils;



import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HtmlUtils {

    public static String stringFilter(String content)  {

        String contents = "<div>" + content.replaceAll("([a-zA-Z]*?)=\\\"(.*?)\\\"", "")
                .replaceAll("<style>[\\s\\S]*?<\\/style>", "").replaceAll("&nbsp;","").replaceAll("\\?","") + "<div>";
        String mate = " <div style = \"word-break:break-all;\">" + "<meta name=\"viewport\" content=\"width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "\n" +
                "<meta name=\"format-detection\" content=\"telephone=no\">\n " + contents;
        return mate;

//        String regEx = "</?[^/?"+reg+"][^><]*>";
//        Pattern p_html = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//        Matcher m_html = p_html.matcher(str);
//        str = m_html.replaceAll("");
//        String content = str.trim()
////                .replaceAll("</?tbody>","")
////                .replaceAll("</?table>","")
//                .replaceAll("width=\"\\d*\"","")
//                .replaceAll("width=\"\\d*px\"","")
//                .replaceAll("（","( ")
//                .replaceAll("）"," )")
//                .replaceAll("style\\s*=\\s*\"([^\\\"]|\\\\\\\\\\\")+\"","")
//                .replaceAll("&nbsp;","")
//                .replaceAll("TEXT-INDENT:\\s*\\d*pt","")
//                .replaceAll("MARGIN-LEFT:\\s*\\d*.\\d*pt","")
//                .replaceAll("\uFEFF","");
//        return "<div style = \"word-break:break-all;\">" +content + "</div>"; // 返回文本字符串
    }


//
//        public static String clean(String html,String reg){
//            StringBuffer buffer = new StringBuffer();
//            Perl5Util preg = new Perl5Util();
//            preg.substitute(buffer,"s/<style[^>]*?>.*?<\\/script>//gmi",html);
////过滤script标签
//            html =buffer.toString();
//            buffer.setLength(0);
//            preg.substitute(buffer,"s#<[/]*?(?!"+reg+")[^<>]*?>##gmi",html);
////( p/img/span ...标签之外,都删除)
//            html =buffer.toString();
//            buffer.setLength(0);
//            return html;
//        }

    public static String  stringUtil(String str,String reg){
        String regEx = "</?[^/?"+reg+"][^><]*>";
        Pattern p_html = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(str);
        str = m_html.replaceAll("");
        String content = str.trim()
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
        return "<div style = \"word-break:break-all;\">" +content + "</div>"; // 返回文本字符串
    }

    }

