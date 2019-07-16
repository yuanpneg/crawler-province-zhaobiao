package app_city;

import crawler_city.AnyangThread;
import crawler_city.BaiseThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * 百色市
 */
public class BaiseMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        BaiseThread thread = new BaiseThread();
        thread.run();
    }
}
