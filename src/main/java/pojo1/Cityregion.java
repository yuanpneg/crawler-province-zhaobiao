package pojo1;

public class Cityregion {

    private  int id;
    private  int parentid;
    private  String parentpath;
    private String title;
    private  int type;
    private String letter;
    private String lon;
    private String lat;
    private String address;

    public Cityregion() {
    }

    public Cityregion(int id, int parentid, String parentpath, String title, int type, String letter) {
        this.id = id;
        this.parentid = parentid;
        this.parentpath = parentpath;
        this.title = title;
        this.type = type;
        this.letter = letter;
    }

    public Cityregion(int id, int parentid, String parentpath, String title, int type, String letter, String lon, String lat) {
        this.id = id;
        this.parentid = parentid;
        this.parentpath = parentpath;
        this.title = title;
        this.type = type;
        this.letter = letter;
        this.lon = lon;
        this.lat = lat;
    }

    public Cityregion(int id, int parentid, String parentpath, String title, int type, String letter, String lon, String lat, String address) {
        this.id = id;
        this.parentid = parentid;
        this.parentpath = parentpath;
        this.title = title;
        this.type = type;
        this.letter = letter;
        this.lon = lon;
        this.lat = lat;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getParentpath() {
        return parentpath;
    }

    public void setParentpath(String parentpath) {
        this.parentpath = parentpath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
