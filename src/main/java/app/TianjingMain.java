package app;

import crawler.TianjinThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * 天津
 */
public class TianjingMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        TianjinThread thread = new TianjinThread();
        thread.run();
    }
}
