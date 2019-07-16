package app;

import bean.Area;
import services.QuanGuoGGZYService;

import java.util.ArrayList;
import java.util.List;

public class CrawlerTask implements Runnable {

//    private List<Area> areaList = new ArrayList<Area>();
//
//    public List<Area> getAreaList() {
//        return areaList;
//    }

    private Area area;

    public CrawlerTask(Area area) {
        this.area = area;
    }

    private QuanGuoGGZYService service = new QuanGuoGGZYService();

    @Override
    public void run() {
        // 循环任务下的省
        service.getList(area);
        // todo 如果是江苏省则把之前的一些网站适配进来
    }
}
