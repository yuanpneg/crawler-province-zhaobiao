package app;

import bean.Tender;
import crawler.GuangxiThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * 广西省
 */
public class GuangxiMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        GuangxiThread thread = new GuangxiThread();
        thread.run();

    }
}
