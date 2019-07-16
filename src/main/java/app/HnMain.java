package app;

import crawler.HnggzyThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * 河南
 */
public class HnMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        HnggzyThread hnggzyThread = new HnggzyThread();
        hnggzyThread.run();
    }
}
