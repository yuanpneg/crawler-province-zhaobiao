package app;

import crawler.ShanxiThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * 陕西省
 */
public class ShanxiMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        ShanxiThread thread = new ShanxiThread();
        thread.run();
    }
}
