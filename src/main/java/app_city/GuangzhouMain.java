package app_city;

import crawler.GuizhouThread;
import crawler_city.GuangzhouThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class GuangzhouMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        GuangzhouThread thread = new GuangzhouThread();
        thread.run();
    }
}
