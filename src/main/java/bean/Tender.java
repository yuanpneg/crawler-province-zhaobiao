package bean;

public class Tender {

    private int id;
    // 区域ID
    private String areaId;

    // 区域标题
    private String areaTitle;

    // 上级区域ID
    private String parentAreaId;

    // 上级区域标题
    private String parentAreaTitle;

    // 招标标题
    private String title;

    // 招标内容
    private String content = "";


    // 爬虫来源
    private String fromurl;

    // 爬虫来源网站名称
    private String from;

    // 信息真实来源
    private String realFrom;

    // 真实来源网站名称
    private String realFromName;

    // 业务类型
    private String yeWuType;

    // 信息类型
    private String infoType;

    // 行业
    private String hangYe;

    // 发表时间
    private String sendTime;

    private int sort;//排序

    private int status;//post状态，1已审核，0未审核

    private String ishandled;//是否处理过

    private String fileinfo;

    private long addtime;//post创建日期，永久不变，一般不显示给用户

    private long edittime;//post更新时间，可在前台修改，显示给用户

    //经度
    private String longitude;

    //维度
    private String latitude;

    //地址
    private String address;

    private int catid;

    //标识id
    private int formalId;

    private  int regionid;

    private String regiontitle;

    private int dsjaddtime;

    public int getDsjaddtime() {
        return dsjaddtime;
    }

    public void setDsjaddtime(int dsjaddtime) {
        this.dsjaddtime = dsjaddtime;
    }

    public int getRegionid() {
        return regionid;
    }

    public void setRegionid(int regionid) {
        this.regionid = regionid;
    }

    public String getRegiontitle() {
        return regiontitle;
    }

    public void setRegiontitle(String regiontitle) {
        this.regiontitle = regiontitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromurl() {
        return fromurl;
    }

    public void setFromurl(String fromurl) {
        this.fromurl = fromurl;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public long getEdittime() {
        return edittime;
    }

    public void setEdittime(long edittime) {
        this.edittime = edittime;
    }


    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIshandled() {
        return ishandled;
    }

    public void setIshandled(String ishandled) {
        this.ishandled = ishandled;
    }

    public String getFileinfo() {
        return fileinfo;
    }

    public void setFileinfo(String fileinfo) {
        this.fileinfo = fileinfo;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaTitle() {
        return areaTitle;
    }

    public void setAreaTitle(String areaTitle) {
        this.areaTitle = areaTitle;
    }

    public String getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getParentAreaTitle() {
        return parentAreaTitle;
    }

    public void setParentAreaTitle(String parentAreaTitle) {
        this.parentAreaTitle = parentAreaTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRealFrom() {
        return realFrom;
    }

    public void setRealFrom(String realFrom) {
        this.realFrom = realFrom;
    }

    public String getRealFromName() {
        return realFromName;
    }

    public void setRealFromName(String realFromName) {
        this.realFromName = realFromName;
    }

    public String getYeWuType() {
        return yeWuType;
    }

    public void setYeWuType(String yeWuType) {
        this.yeWuType = yeWuType;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getHangYe() {
        return hangYe;
    }

    public void setHangYe(String hangYe) {
        this.hangYe = hangYe;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getFormalId() {
        return formalId;
    }

    public void setFormalId(int formalId) {
        this.formalId = formalId;
    }
}
