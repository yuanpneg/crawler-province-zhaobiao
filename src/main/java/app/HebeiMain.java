package app;

import crawler.HebeiThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/*
 *河北省
 */
public class HebeiMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        HebeiThread thread = new HebeiThread();
        thread.run();
    }
}
