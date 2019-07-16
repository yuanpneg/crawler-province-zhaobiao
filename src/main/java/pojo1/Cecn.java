package pojo1;

public class Cecn {
    private int id;
    private String title;
    private int day;
    private String from ;
    private String fromurl;
    private String classify;
    private String fileinfo;


    public Cecn() {
    }

    public Cecn(int id, String title, int day, String from, String fromurl, String classify, String fileinfo) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.from = from;
        this.fromurl = fromurl;
        this.classify = classify;
        this.fileinfo = fileinfo;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromurl() {
        return fromurl;
    }

    public void setFromurl(String fromurl) {
        this.fromurl = fromurl;
    }

    public String getFileinfo() {
        return fileinfo;
    }

    public void setFileinfo(String fileinfo) {
        this.fileinfo = fileinfo;
    }
}
