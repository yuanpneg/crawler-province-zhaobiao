package app;

import crawler.YunnanThread;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class YunnanMain {

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        YunnanThread thread = new YunnanThread();
        thread.run();
    }
}
