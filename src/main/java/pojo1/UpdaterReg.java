package pojo1;

public class UpdaterReg {
    private int regionid;
    private String newcity;
    private String oldcity;
    private String lng;
    private String lat;

    public UpdaterReg() {
    }

    public UpdaterReg(int regionid, String newcity, String oldcity) {
        this.regionid = regionid;
        this.newcity = newcity;
        this.oldcity = oldcity;
    }

    public UpdaterReg(int regionid, String newcity, String oldcity, String lng, String lat) {
        this.regionid = regionid;
        this.newcity = newcity;
        this.oldcity = oldcity;
        this.lng = lng;
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getRegionid() {
        return regionid;
    }

    public void setRegionid(int regionid) {
        this.regionid = regionid;
    }

    public String getNewcity() {
        return newcity;
    }

    public void setNewcity(String newcity) {
        this.newcity = newcity;
    }

    public String getOldcity() {
        return oldcity;
    }

    public void setOldcity(String oldcity) {
        this.oldcity = oldcity;
    }
}
