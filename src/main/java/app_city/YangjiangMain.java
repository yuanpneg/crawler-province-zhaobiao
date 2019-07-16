package app_city;

import crawler_city.YanchengThread;
import crawler_city.YangjiangThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * 阳江市
 */
public class YangjiangMain {
    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        YangjiangThread thread = new YangjiangThread();
        thread.run();
    }
}
