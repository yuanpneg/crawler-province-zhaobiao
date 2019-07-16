package app;

import bean.Area;
import org.apache.commons.logging.LogFactory;
import services.QuanGuoGGZYService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) throws Exception {
        // 去掉烦人的日志
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        QuanGuoGGZYService service = new QuanGuoGGZYService();
//        service.getTender();

        int threadSize = 1;

        ExecutorService threadPool = Executors.newFixedThreadPool(threadSize);

        List<Area> areas = service.getAllArea();

        for (int i = 0; i < areas.size(); i++) {
            threadPool.submit(new CrawlerTask(areas.get(i)));
        }
        threadPool.shutdown();


        while (!threadPool.awaitTermination(5, TimeUnit.SECONDS));

        System.out.println("执行完成~");

    }


}
