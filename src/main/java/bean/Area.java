package bean;

import java.util.ArrayList;
import java.util.List;

public class Area {

    // 判断是否是直辖市
    private boolean isZhiXia = false;

    // id
    private String id;

    // 省市名称
    private String title;

    // 子市
    private List<Area> childAreas = new ArrayList<Area>();

    public boolean isZhiXia() {
        return isZhiXia;
    }

    public void setZhiXia(boolean zhiXia) {
        isZhiXia = zhiXia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Area> getChildAreas() {
        return childAreas;
    }

    public void setChildAreas(List<Area> childAreas) {
        this.childAreas = childAreas;
    }


    public Area(boolean isZhiXia, String id, String title) {
        this.isZhiXia = isZhiXia;
        this.id = id;
        this.title = title;
    }

    public Area() {
    }
}
