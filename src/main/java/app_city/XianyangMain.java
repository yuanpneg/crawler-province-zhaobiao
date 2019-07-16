package app_city;

import crawler_city.XianyangThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * 咸阳
 */
public class XianyangMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        XianyangThread thread = new XianyangThread();
        thread.run();
    }
}
