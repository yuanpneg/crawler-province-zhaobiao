package pojo1;

/**
 * 招标城市实体类
 */
public class CityBean {

    private int id;
    private int pid;
    private String parentname;
    private int cityid;
    private String name;
    private int level;

    public CityBean() {
    }

    public CityBean(int id, int pid, String parentname, int cityid, String name, int level) {
        this.id = id;
        this.pid = pid;
        this.parentname = parentname;
        this.cityid = cityid;
        this.name = name;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
